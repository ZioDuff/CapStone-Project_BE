package JacopoDeMaio.TattooStudio.controllers;

import JacopoDeMaio.TattooStudio.entities.User;
import JacopoDeMaio.TattooStudio.payloads.userDTO.UserDTO;
import JacopoDeMaio.TattooStudio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    // COSE CHE PUO FARE L'ADMIN
    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public Page<User> getUsersList(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortedBy) {
        return userService.getAllUsers(page, size, sortedBy);
    }

    @GetMapping("/artists")
    public List<User> getArtist(@RequestParam String roleName) {
        return userService.getArtist(roleName);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('Admin')")
    public User getUser(@PathVariable UUID userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize(("hasAuthority('Admin')"))
    public void deleteUserProfile(@PathVariable UUID userId) {
        this.userService.findByIdAndDelete(userId);
    }


//    QUELLO CHE L'UTENTE PUO FARE SUL PROPRIO PROFILO

    @GetMapping("/me")
    public User getOwnProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.userService.findById(currentAuthenticatedUser.getId());
    }

    @PutMapping("/me")
    public User updateOwnProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody UserDTO payload) {
        return this.userService.findByIdAndUpdate(currentAuthenticatedUser.getId(), payload);
    }

    @DeleteMapping("/me")
    public void deleteOwnProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.userService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }
}
