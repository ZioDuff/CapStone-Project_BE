package JacopoDeMaio.TattooStudio.payloads.roleDTO;

import jakarta.validation.constraints.NotEmpty;

public record NewRoleDTO(
        @NotEmpty(message = "Effective Role field is required")
        String name
) {
}
