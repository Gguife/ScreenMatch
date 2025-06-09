package br.com.gguife.screenmatch.service;

import br.com.gguife.screenmatch.dto.SeriesDTO;
import br.com.gguife.screenmatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository repository;

    public List<SeriesDTO> getAllSeries() {
        return repository.findAll()
                .stream()
                .map( s -> new SeriesDTO(
                        s.getId(),
                        s.getTitle(),
                        s.getTotalSeasons(),
                        s.getRating(),
                        s.getGenre(),
                        s.getActors(),
                        s.getPoster(),
                        s.getSynopsis()
                ))
                .collect(Collectors.toList());
    }
}
