package JacopoDeMaio.TattooStudio.payloads.roleDTO;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record RoleAssignedDTO(
        @NotEmpty
        UUID id
) {
}
