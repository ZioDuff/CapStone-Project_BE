package JacopoDeMaio.TattooStudio.services;

import JacopoDeMaio.TattooStudio.entities.User;
import JacopoDeMaio.TattooStudio.exceptions.NotFoundException;
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
    private PasswordEncoder bCrypt;
    @Autowired
    private RoleService roleService;


    public Page<User> getAllUsers(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return userRepository.findAll(pageable);
    }

//    public User saveUser(UserDTO body) {
//        this.userRepository.findByEmail(body.email()).ifPresent(utente -> {
//            throw new BadRequestException("The user with email: " + body.email() + ", already exist.");
//        });
//
//
//        //TODO 1 - SISTEMARE RUOLO DEFAULT
//
////        User user = new User(body.username(), body.email(), bCrypt.encode(body.password()), body.name(), body.surname());
//////        Role foundRole = roleService.findByRoleName("User");
////
////        List<Role> roleList = new ArrayList<>();
////
//////        roleList.add(foundRole);
////
////        user.setRolesList(roleList);
//
////        return this.userRepository.save();
//    }

//    public User findByIdAndUpdate(UUID id, UserDTO payload) {
//        User found = this.findById(id);
//        found.setUsername(payload.username());
//        found.setName(payload.name());
//        found.setSurname(payload.surname());
//        found.setEmail(payload.email());
//        found.setPassword(payload.password());
//        return userRepository.save(found);
//    }

//    public void findByIdAndDelete(UUID id) {
//        User found = this.findById(id);
//        this.userRepository.delete(found);
//    }


    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

//    public User addRoles(UUID id, RoleAssignedDTO roleId) {
//        User found = this.findById(id);
//        Role role = this.roleService.findById(roleId.id());
//        found.getRolesList().add(role);
//
//        return this.userRepository.save(found);
//    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

}
