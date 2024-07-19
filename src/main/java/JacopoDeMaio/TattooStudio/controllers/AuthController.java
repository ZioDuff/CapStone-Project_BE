package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.exceptions.BadRequestException;
import JacopoDeMaio.TattooStudio.payloads.userDTO.GenericDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.GenericLoginDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.GenericLoginResponseDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.NewGenericResponseDTO;
import JacopoDeMaio.TattooStudio.services.AuthService;
import JacopoDeMaio.TattooStudio.services.GenericService;
import JacopoDeMaio.TattooStudio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private GenericService genericService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public GenericLoginResponseDTO login(@RequestBody GenericLoginDTO payload) {
        return new GenericLoginResponseDTO(authService.authenticateUtenteAndGenerateToken(payload));
    }

    @PostMapping("/register/users")
    @ResponseStatus(HttpStatus.CREATED)
    public NewGenericResponseDTO saveUsers(@RequestBody @Validated GenericDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewGenericResponseDTO(this.userService.saveUser(payload).getId());
    }


}
