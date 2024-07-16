package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.payloads.userDTO.UserLoginDTO;
import JacopoDeMaio.TattooStudio.payloads.userDTO.UserLoginResponseDTO;
import JacopoDeMaio.TattooStudio.services.AuthService;
import JacopoDeMaio.TattooStudio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
