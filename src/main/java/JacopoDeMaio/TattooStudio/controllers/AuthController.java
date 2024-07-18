package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.exceptions.BadRequestException;
import JacopoDeMaio.TattooStudio.payloads.userDTO.NewUserResponseDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.UserDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.UserLoginDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.UserLoginResponseDTO;
import JacopoDeMaio.TattooStudio.services.AuthService;
import JacopoDeMaio.TattooStudio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO payload) {
        return new UserLoginResponseDTO(authService.authenticateUtenteAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO saveUsers(@RequestBody @Validated UserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewUserResponseDTO(this.userService.saveUser(body).getId());
    }

    @PostMapping("/register/artist")
    @PreAuthorize("hasAuthority('Admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO adminSaveTattoArtist(@RequestBody @Validated UserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewUserResponseDTO(this.userService.adminSaveArtist(body).getId());
    }
}
