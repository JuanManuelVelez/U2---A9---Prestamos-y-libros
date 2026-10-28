package mx.edu.utez.prestamos.controller.libro;

import jakarta.validation.Valid;
import mx.edu.utez.prestamos.controller.libro.dto.CrearLibroDTO;
import mx.edu.utez.prestamos.model.Libro;
import mx.edu.utez.prestamos.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libro")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody @Valid CrearLibroDTO payload) {
        return ResponseEntity.status(201).body(libroService.crear(payload));
    }

    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        return ResponseEntity.ok(libroService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.buscar(id));
    }
}
