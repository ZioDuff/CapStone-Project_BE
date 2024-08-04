package JacopoDeMaio.TattooStudio.payloads.userDTO;

import jakarta.validation.constraints.NotEmpty;

public record UpdatePasswordDTO(
        @NotEmpty(message = "Il campo password è obbligatorio")
        String password
) {
}
