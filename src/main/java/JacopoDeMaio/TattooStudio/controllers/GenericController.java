package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.entities.Generic;
import JacopoDeMaio.TattooStudio.exceptions.BadRequestException;
import JacopoDeMaio.TattooStudio.payloads.TattooDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.GenericDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.NewGenericResponseDTO;
import JacopoDeMaio.TattooStudio.services.GenericService;
import JacopoDeMaio.TattooStudio.services.TattooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/generics")
public class GenericController {

    @Autowired
    private GenericService genericService;
    @Autowired
    private TattooService tattooService;

    @PostMapping("enroll/tattooArtist")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewGenericResponseDTO saveTattooArtist(@RequestBody @Validated GenericDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewGenericResponseDTO(this.genericService.saveTattooArtist(payload).getId());
    }


    @GetMapping("/me")
    public Generic getOwnProfile(@AuthenticationPrincipal Generic currentAuthenticatedUser) {
        return this.genericService.findById(currentAuthenticatedUser.getId());
    }

    @PatchMapping("/me/avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile image, @AuthenticationPrincipal Generic currentAuthenticatedUser) throws IOException {
        return genericService.uploadImage(image, currentAuthenticatedUser.getId());
    }

    @PostMapping("me/newTattoo")
    public String uploadTattoo(@RequestParam("newTattoo") MultipartFile image, @AuthenticationPrincipal Generic currentAuthenticatedUser, TattooDTO payload) throws IOException {
        return tattooService.uploadTattooImage(image, payload, currentAuthenticatedUser.getId());
    }

    @PutMapping("/me")
    public Generic updateOwnProfile(@AuthenticationPrincipal Generic currentAuthenticatedUser, @RequestBody GenericDTO payload) {
        return this.genericService.findByIdAndUpdate(currentAuthenticatedUser.getId(), payload);
    }

    @DeleteMapping("/me")
    public void deleteOwnProfile(@AuthenticationPrincipal Generic currentAuthenticatedUser) {
        this.genericService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }


}
