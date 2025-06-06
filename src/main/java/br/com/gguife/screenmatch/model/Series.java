package br.com.gguife.screenmatch.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    private Integer totalSeasons;
    private Double rating;
    private String actors;
    private String synopsis;
    private String poster;

    @Enumerated(EnumType.STRING)
    private Category genre;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodes> episodes = new ArrayList<>();

    public Series() {}

    public Series(SeriesData seriesData) {
        this.title = seriesData.title();
        this.totalSeasons = seriesData.totalSeasons();
        this.actors = seriesData.actors();
        this.synopsis = seriesData.synopsis();
        this.poster = seriesData.poster();
        this.genre = Category.fromString(seriesData.genre().split(",")[0].trim());
        this.rating = OptionalDouble.of(Double.valueOf(seriesData.rating())).orElse(0);
    }

    public Long getId() {
        return id;
    }

    public void setEpisodes(List<Episodes> episodes) {
        episodes.forEach(e -> e.setSeries(this));
        this.episodes = episodes;
    }

    public List<Episodes> getEpisodes() {
        return episodes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public String getActors() {
        return actors;
    }

    public String getPoster() {
        return poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Double getRating() {
        return rating;
    }

    public Category getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "genre='" + genre + '\'' +
                "title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", rating=" + rating +
                ", actors='" + actors + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", poster='" + poster + '\'' +
                ", episodes='" + episodes + '\'';
    }
}
