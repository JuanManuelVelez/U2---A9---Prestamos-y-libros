package mx.edu.utez.prestamos.controller.lector.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class IdLectorDTO {
    @NotNull(message = "El ID del lector es obligatorio")
    @Positive(message = "El ID del lector debe ser mayor que cero")
    private Long lectorId;
}
