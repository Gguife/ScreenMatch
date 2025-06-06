package br.com.gguife.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(@JsonAlias("Title") String title,
                         @JsonAlias("totalSeasons") Integer totalSeasons,
                         @JsonAlias("imdbRating") String rating,
                         @JsonAlias("Actors") String actors,
                         @JsonAlias("Plot") String synopsis,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Genre") String genre
                        ) {
}
