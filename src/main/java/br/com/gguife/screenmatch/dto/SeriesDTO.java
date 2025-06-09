package br.com.gguife.screenmatch.dto;

import br.com.gguife.screenmatch.model.Category;

public record SeriesDTO(Long id,
                        String title,
                        Integer totalSeasons,
                        Double rating,
                        Category genre,
                        String actors,
                        String poster,
                        String synopsis
                    ) {


}
