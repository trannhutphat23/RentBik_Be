package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.DTO.GplxDto;
import com.RentBikApp.RentBik.DTO.GplxResponseDto;
import com.RentBikApp.RentBik.Model.Gplx;
import com.RentBikApp.RentBik.Service.GplxService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class GplxController {
    private final GplxService gplxService;

    public GplxController(GplxService gplxService) {
        this.gplxService = gplxService;
    }
    @PostMapping("/gplxs/add")
    public GplxResponseDto addGplx(
        @RequestBody GplxDto dto
    ) {
        return gplxService.saveGplx(dto);
    }
    @GetMapping("/gplxs")
    public List<Gplx> findAllGplx(){
        return gplxService.findAllGplx();
    }

    @GetMapping("/gplx/{gplx_id}")
    public Gplx findGplxById(
            @PathVariable("gplx_id") Integer id
    ){
        return gplxService.findGplxById(id);
    }

    @GetMapping("/gplx/search/{gplx_rank}")
    public List<Gplx> findGplxByRank(
            @PathVariable("gplx_rank") String rank
    ){
        return gplxService.findGplxByRank(rank);
    }
}
