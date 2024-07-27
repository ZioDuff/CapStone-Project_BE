package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.entities.Tattoo;
import JacopoDeMaio.TattooStudio.services.TattooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tattoos")
public class TattooController {

    @Autowired
    private TattooService tattooService;

    @GetMapping
    public Page<Tattoo> getTattooList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortedBy
    ) {
        return this.tattooService.getAllTattoos(page, size, sortedBy);
    }
}
