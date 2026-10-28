package mx.edu.utez.prestamos.controller.lector;

import jakarta.validation.Valid;
import mx.edu.utez.prestamos.controller.lector.dto.CrearLectorDTO;
import mx.edu.utez.prestamos.model.Lector;
import mx.edu.utez.prestamos.service.LectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lector")
public class LectorController {
    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @PostMapping
    public ResponseEntity<Lector> crear(@RequestBody @Valid CrearLectorDTO payload) {
        return ResponseEntity.status(201).body(lectorService.crear(payload));
    }

    @GetMapping
    public ResponseEntity<List<Lector>> listar() {
        return ResponseEntity.ok(lectorService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lector> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(lectorService.buscar(id));
    }
}
