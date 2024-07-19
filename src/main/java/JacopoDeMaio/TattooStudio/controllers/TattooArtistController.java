package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.exceptions.BadRequestException;
import JacopoDeMaio.TattooStudio.payloads.userDTO.GenericDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.NewGenericResponseDTO;
import JacopoDeMaio.TattooStudio.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tattooArtists")
public class TattooArtistController {

    @Autowired
    private GenericService genericService;

    @PostMapping("enroll/tattooArtist")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewGenericResponseDTO saveTattooArtist(@RequestBody @Validated GenericDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewGenericResponseDTO(this.genericService.saveTattooArtist(payload).getId());
    }
}
