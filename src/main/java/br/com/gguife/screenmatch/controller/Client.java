package br.com.gguife.screenmatch.controller;

import br.com.gguife.screenmatch.model.*;
import br.com.gguife.screenmatch.repository.SeriesRepository;
import br.com.gguife.screenmatch.service.ApiService;
import br.com.gguife.screenmatch.service.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Client {
    private Scanner scanner = new Scanner(System.in);
    ApiService apiService = new ApiService();
    ConvertData convertData = new ConvertData();
    private final String APIKEY = "&apikey=e4a9b4c1";
    private final String address = "https://www.omdbapi.com/?t=";
    private List<SeriesData> seriesData = new ArrayList<>();
    private SeriesRepository repository;
    private List<Series> series = new ArrayList<>();

    public Client(SeriesRepository repository) {
        this.repository = repository;
    }


    public void showMenu() {

        int option = -1;
        while (option != 0) {
            String menu = """
                    1 - Search Series
                    2 - Search episodes
                    3 - Series already researched
                    4 - Search series by title
                    5 - Search series by actor
                    6 - Search top 5 series
                    7 - Search series by category
                    8 - filter series by total seasons and your rating
                    
                    0 - Exit
                    """;

            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchWebSeries();
                    break;
                case 2:
                    searchEpisodeBySeries();
                    break;
                case 3:
                    listResearchedSeries();
                    break;
                case 4:
                    searchSeriesByTitle();
                    break;
                case 5:
                    searchSeriesByActor();
                    break;
                case 6:
                    searchTopFiveSeries();
                    break;
                case 7:
                    searchSeriesByCategory();
                    break;
                case 8:
                    filterSeriesBySeasonsAndRating();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
   }


    private void searchWebSeries() {
        SeriesData data = getDataSeries();
//        seriesData.add(data);
       Series series = new Series(data);
       repository.save(series);
       System.out.println(data);
   }

   private SeriesData getDataSeries() {
       System.out.println("Type series name for search: ");
       String name = scanner.nextLine();
       String json = apiService.data(address + name.replace(" ", "%20") + APIKEY);
       SeriesData data = convertData.getData(json, SeriesData.class);
       return data;
   }

   private void searchEpisodeBySeries() {
        listResearchedSeries();
        System.out.println("Type series name: ");
        String name = scanner.nextLine();

        Optional<Series> serie = repository.findByTitleContainingIgnoreCase(name);

        if(serie.isPresent()) {
            Series findSeries = serie.get();
            List<SeasonData> seasons = new ArrayList<>();

            for(int i = 1; i <= findSeries.getTotalSeasons(); i++) {
                String json = apiService.data(address + findSeries.getTitle().replace(" ", "%20") + "&Season=" + i + APIKEY);
                SeasonData seasonData = convertData.getData(json, SeasonData.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episodes> episodes = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episodes(d.number(), e)))
                    .collect(Collectors.toList());

            findSeries.setEpisodes(episodes);
            repository.save(findSeries);
        } else {
            System.out.println("Not found!");
        }

   }

   private void listResearchedSeries() {
        series = repository.findAll();
        series.stream().sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
   }

   private void searchSeriesByTitle() {
       System.out.println("Type name of the series: ");
       String name = scanner.nextLine();
       Optional<Series> searchedSeries = repository.findByTitleContainingIgnoreCase(name);

       if(searchedSeries.isPresent()) {
           System.out.println("Series data: " + searchedSeries.get());
       } else {
           System.out.println("Series not found");
       }

   }

    private void searchSeriesByActor() {
        System.out.println("Type name of the actor: ");
        String name = scanner.nextLine();
        List<Series> searchedSeries = repository.findByActorsContainingIgnoreCase(name);

        if(!searchedSeries.isEmpty()) {
            System.out.println("Series found: ");
            searchedSeries.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating()));
        } else {
            System.out.println("Series not found.");
        }
    }

    private void searchTopFiveSeries() {
        List<Series> topSeries = repository.findTop5ByOrderByRatingDesc();
        topSeries.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }

    private void searchSeriesByCategory() {
        System.out.println("Type the genre do you like for search: ");
        String name = scanner.nextLine();
        Category category = Category.fromString(name);
        List<Series> series = repository.findByGenre(category);
        System.out.println("The result of search: " + name);
        series.forEach(System.out::println);
    }

    private void filterSeriesBySeasonsAndRating() {
        System.out.println("Type the number of seasons: ");
        int numberSeasons = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Type the minimum rating value you want: ");
        double numberRating = scanner.nextDouble();
        scanner.nextLine();
        List<Series> filterSeries = repository.seriesBySeasonsAndRating(numberSeasons, numberRating);
        System.out.println("** SERIES FILTERED **");
        filterSeries.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating() + ", total seasons: " + s.getTotalSeasons()));
    }

    private void searchEpisodesByName() {
        System.out.println("Type part of the episode name: ");
        String episodeName = scanner.nextLine();
        List<Episodes> result = repository.searchEpisodesByName(episodeName);
        result.forEach( e -> System.out.printf(
                "Series: %s Seasons: %s - Episode %s - %s\n",
                e.getSeries().getTitle(), e.getSeasons(),
                e.getNumberOfEp(), e.getTitle()
        ));
    }
}
