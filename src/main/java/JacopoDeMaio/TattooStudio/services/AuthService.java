package JacopoDeMaio.TattooStudio.services;


import JacopoDeMaio.TattooStudio.entities.Generic;
import JacopoDeMaio.TattooStudio.exceptions.UnauthorizedException;
import JacopoDeMaio.TattooStudio.payloads.userDTO.GenericLoginDTO;
import JacopoDeMaio.TattooStudio.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private GenericService genericService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bCrypt;

    public String authenticateUtenteAndGenerateToken(GenericLoginDTO payload) {
        Generic generic = this.genericService.findByEmail(payload.email());
        if (bCrypt.matches(payload.password(), generic.getPassword())) {
            return jwtTools.createToken(generic);
        } else {
            throw new UnauthorizedException("Credenziali non corrette");
        }
    }
}
