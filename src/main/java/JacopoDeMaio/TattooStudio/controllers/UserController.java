package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.entities.User;
import JacopoDeMaio.TattooStudio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

//    TODO quello che fa l'admin (sicuramente da ultimare)

    //    GET DI USER SOTTOFORMA DI PAGE
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getUsersList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortedBy
    ) {
        return this.userService.getAllUsers(page, size, sortedBy);
    }


    //    GET SUL SINGOLO UTENTE
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUser(@PathVariable UUID userId) {
        return this.userService.findById(userId);
    }

    //    ELIMINAZIONE DEL SINGOLO UTENTE
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable UUID userId) {
        this.userService.findByIdAndDelete(userId);
    }


}
