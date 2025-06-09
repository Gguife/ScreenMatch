package br.com.gguife.screenmatch.controller;

import br.com.gguife.screenmatch.dto.SeriesDTO;
import br.com.gguife.screenmatch.service.SeriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeriesController {

    private SeriesService seriesService;

    @GetMapping("/series")
    public List<SeriesDTO> getSeries() {
        return seriesService.getAllSeries();
    }
}

