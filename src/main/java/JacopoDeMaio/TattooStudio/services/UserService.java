package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.entities.User;
import JacopoDeMaio.TattooStudio.exceptions.BadRequestException;
import JacopoDeMaio.TattooStudio.exceptions.NotFoundException;
import JacopoDeMaio.TattooStudio.payloads.userDTO.GenericDTO;
import JacopoDeMaio.TattooStudio.repositories.GenericRepository;
import JacopoDeMaio.TattooStudio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenericRepository genericRepository;
    @Autowired
    private PasswordEncoder bCrypt;


    public User saveUser(GenericDTO payload) {
        this.genericRepository.findByEmail(payload.email()).ifPresent(generic -> {
            throw new BadRequestException("L'email: " + payload.email() + " è gia in uso");
        });
        this.genericRepository.findByUsername(payload.username()).ifPresent(generic -> {
            throw new BadRequestException("L'username: " + payload.username() + " è gia in uso");
        });
        User newUser = new User(
                payload.username(),
                payload.email(),
                bCrypt.encode(payload.password()),
                payload.name(),
                payload.surname(),
                payload.age(),
                "https://ui-avatars.com/api/" + payload.name() + payload.surname()
        );
        return userRepository.save(newUser);
    }

    public Page<User> getAllUsers(int page, int size, String sortedBy) {
        if (size > 10) size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        return userRepository.findAll(pageable);
    }

    public User findById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("l'utente con id: " + userId + " non è stato torvato")
        );
    }

    //
//    public User findByIdAndUpdate(UUID id, UserDTO payload) {
//
//        User found = this.findById(id);
//        found.setUsername(payload.username());
//        found.setName(payload.name());
//        found.setSurname(payload.surname());
//        return userRepository.save(found);
//    }
//
    public void findByIdAndDelete(UUID userid) {
        User found = this.findById(userid);
        this.userRepository.delete(found);
    }


}
