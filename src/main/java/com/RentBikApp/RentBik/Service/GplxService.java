package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.GplxDto;
import com.RentBikApp.RentBik.DTO.GplxResponseDto;
import com.RentBikApp.RentBik.Model.Gplx;
import com.RentBikApp.RentBik.Repository.GplxRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GplxService {
    private final GplxRepository gplxRepository;

    public GplxService(GplxRepository gplxRepository) {
        this.gplxRepository = gplxRepository;
    }

    public GplxResponseDto saveGplx(GplxDto dto){
        var gplx = toGplx(dto);
        gplxRepository.save(gplx);
        return toGplxResponseDto(gplx);
    }
    private GplxResponseDto toGplxResponseDto(Gplx gplx){
        return new GplxResponseDto(
            gplx.getId(),
            gplx.getRank()
        );
    }
    private Gplx toGplx(GplxDto dto){
        var gplx = new Gplx();
        gplx.setRank(dto.rank());
        return gplx;
    }
    public List<Gplx> findAllGplx(){
        return gplxRepository.findAll();
    }

    public Gplx findGplxById(Integer id){
        return gplxRepository.findById(id)
                .orElse(null);
    }

    public List<Gplx> findGplxByRank(String rank){
        return gplxRepository.findAllByRankContaining(rank);
    }

}
