package JacopoDeMaio.TattooStudio.payloads.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UpdateEmailDTO(
        @NotEmpty(message = "il campo email è obbligatorio")
        @Email
        String email
) {
}
