package JacopoDeMaio.TattooStudio.utility;

import JacopoDeMaio.TattooStudio.entities.Role;
import JacopoDeMaio.TattooStudio.entities.User;
import JacopoDeMaio.TattooStudio.repositories.RoleRepository;
import JacopoDeMaio.TattooStudio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCrypt;


    @Bean
    public void initializeUser() {
        User firstUser = new User("AdminSupremo",
                "jajodemaio@gmail.com",
                bCrypt.encode("AldoBaglio"),
                "Jacopo",
                "De Maio",
                "https://ui-avatars.com/api/");

        if (userRepository.findByEmail(firstUser.getEmail()).isPresent()) {
            return;
        }

        Role admin = new Role("Admin");
        Role user = new Role("User");
        Role tattoArtist = new Role("TattoArtist");

        if (roleRepository.findByName("Admin").isPresent()) {
            return;
        }
        if (roleRepository.findByName("User").isPresent()) {
            return;
        }
        if (roleRepository.findByName("TattoArtist").isPresent()) {
            return;
        }


        roleRepository.save(admin);
        roleRepository.save(user);
        roleRepository.save(tattoArtist);

        List<Role> roleList = new ArrayList<>();
        roleList.add(admin);
        firstUser.setRolesList(roleList);

        userRepository.save(firstUser);
    }
}