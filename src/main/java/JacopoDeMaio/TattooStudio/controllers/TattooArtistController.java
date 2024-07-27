package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.entities.TattoArtist;
import JacopoDeMaio.TattooStudio.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tattooArtists")
public class TattooArtistController {

    @Autowired
    private GenericService genericService;


    @GetMapping
    public Page<TattoArtist> getUsersList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortedBy
    ) {
        return this.genericService.getAllTattooArtists(page, size, sortedBy);
    }

    @GetMapping("/{tattooArtistId}")
    public TattoArtist getTattooArtist(@PathVariable UUID tattooArtistId) {
        return this.genericService.findTattooArtistById(tattooArtistId);
    }


}


