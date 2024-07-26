package JacopoDeMaio.TattooStudio.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TattooDTO(
        @NotEmpty(message = "Il campo nome è obbligatorio")
        @Size(min = 3, max = 30, message = "il nome del tatuaggio deve essere compreso tra i 3 e i 30 caratteri")
        String name,
        @Size(min = 10, max = 100, message = "la descrizone deve essere compresa tra i 10 e i 100 caretteri")
        String description
) {
}
