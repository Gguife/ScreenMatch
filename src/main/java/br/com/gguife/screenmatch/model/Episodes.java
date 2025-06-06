package br.com.gguife.screenmatch.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ManyToAny;

import java.time.DateTimeException;
import java.time.LocalDate;

@Entity
@Table(name = "episodes")
public class Episodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer seasons;
    private String title;
    private Integer numberOfEp;
    private Double rating;
    private LocalDate releaseDate;

    @ManyToOne
    private Series series;

    public Episodes() {}

    public Episodes(Integer numberOfSeason, EpData epData) {
        this.seasons = numberOfSeason;
        this.title = epData.title();
        this.numberOfEp = epData.epNumber();

        try {
            this.rating = Double.valueOf(epData.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }
        try {
            this.releaseDate = LocalDate.parse(epData.date());
        }catch (DateTimeException e) {
            this.releaseDate = LocalDate.of(0000, 1, 1);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Series getSeries() {
        return series;
    }

    public Integer getSeasons() {
        return seasons;
    }

    public Integer getNumberOfEp() {
        return numberOfEp;
    }

    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setSeasons(Integer seasons) {
        this.seasons = seasons;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumberOfEp(Integer numberOfEp) {
        this.numberOfEp = numberOfEp;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public String toString() {
        return "Season=" + seasons +
                ", title=" + title + '\'' +
                ", episode number=" + numberOfEp +
                ", rating=" + rating +
                ", release date=" + releaseDate;
    }
}
