package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.SeriesDto;
import com.RentBikApp.RentBik.Model.ErrorResponse;
import com.RentBikApp.RentBik.Model.Series;
import com.RentBikApp.RentBik.Repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;
    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }
    public List<Series> findAllBrands(){
        return seriesRepository.findAll();
    }
    public Object addSeries(SeriesDto dto){
        var series = toInsurance(dto);

        if (seriesRepository.existsByName(series.getName())){
            return new ErrorResponse("Name must be unique");
        }

        return seriesRepository.save(series);
    }

    public Series toInsurance(SeriesDto dto){
        var series = new Series();
        series.setName(dto.name());
        return series;
    }
}
