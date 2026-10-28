package mx.edu.utez.prestamos.controller.libro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CrearLibroDTO {
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no debe superar 150 caracteres")
    private String titulo;

    @PositiveOrZero(message = "Los ejemplares no pueden ser negativos")
    private int ejemplaresDisponibles;
}
