package mx.edu.utez.prestamos.controller.prestamo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SolicitarPrestamoDTO {
    @NotNull(message = "El ID del lector es obligatorio")
    @Positive(message = "El ID del lector debe ser mayor que cero")
    private Long lectorId;

    @NotNull(message = "El ID del libro es obligatorio")
    @Positive(message = "El ID del libro debe ser mayor que cero")
    private Long libroId;
}
