package mx.edu.utez.prestamos.controller.prestamo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class IdPrestamoDTO {
    @NotNull(message = "El ID del préstamo es obligatorio")
    @Positive(message = "El ID del préstamo debe ser mayor que cero")
    private Long prestamoId;
}
