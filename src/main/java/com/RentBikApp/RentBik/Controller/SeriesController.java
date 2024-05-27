package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.DTO.SeriesDto;
import com.RentBikApp.RentBik.Model.Series;
import com.RentBikApp.RentBik.Service.SeriesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping("/series")
    public List<Series> findAllSeries(){
        return seriesService.findAllBrands();
    }

    @PostMapping("/series/add")
    public Object addSeries(
            @RequestBody SeriesDto dto
    ){
        return seriesService.addSeries(dto);
    }
}
