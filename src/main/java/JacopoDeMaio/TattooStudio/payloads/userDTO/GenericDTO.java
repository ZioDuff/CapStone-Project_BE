package JacopoDeMaio.TattooStudio.payloads.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GenericDTO(
        @NotEmpty(message = "Username field is required")
        @Size(min = 4, max = 20, message = "The username must be between 4 and 20 characters")
        String username,
        @NotEmpty(message = "Email field is required")
        @Email
        String email,
        @NotEmpty(message = "Username field is required")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password,
        @NotEmpty(message = "name field is required")
        @Size(min = 2, max = 20, message = "name must be between 2 and 20 characters")
        String name,
        @NotEmpty(message = "surname field is required")
        @Size(min = 2, max = 20, message = "surname field is required")
        String surname,
        @NotNull(message = "age field is required")
        int age,
        @Size(min = 15, max = 200, message = "description must be between 15 and 200 characters")
        String description,
        int phoneNumber

) {
}
