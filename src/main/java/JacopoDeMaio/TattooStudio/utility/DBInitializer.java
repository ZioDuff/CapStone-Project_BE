package JacopoDeMaio.TattooStudio.utility;

import JacopoDeMaio.TattooStudio.entities.Admin;
import JacopoDeMaio.TattooStudio.entities.Generic;
import JacopoDeMaio.TattooStudio.repositories.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DBInitializer {

    @Autowired
    private GenericRepository genericRepository;

    @Autowired
    private PasswordEncoder bCrypt;


    @Bean
    public void initializeUser() {
        Generic firstGeneric = new Admin("AdminSupremo",
                "jajodemaio@gmail.com",
                bCrypt.encode("AldoBaglio"),
                "Jacopo",
                "De Maio",
                "20",
                "https://ui-avatars.com/api/");

        if (genericRepository.findByEmail(firstGeneric.getEmail()).isPresent()) {
            return;
        }


        genericRepository.save(firstGeneric);
    }
}