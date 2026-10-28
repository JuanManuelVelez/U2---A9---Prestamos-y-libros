package mx.edu.utez.prestamos.controller.prestamo;

import jakarta.validation.Valid;
import mx.edu.utez.prestamos.controller.lector.dto.IdLectorDTO;
import mx.edu.utez.prestamos.controller.prestamo.dto.IdPrestamoDTO;
import mx.edu.utez.prestamos.controller.prestamo.dto.SolicitarPrestamoDTO;
import mx.edu.utez.prestamos.model.Lector;
import mx.edu.utez.prestamos.model.Prestamo;
import mx.edu.utez.prestamos.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<Prestamo> solicitar(@RequestBody @Valid SolicitarPrestamoDTO payload) {
        return ResponseEntity.status(201).body(prestamoService.solicitarPrestamo(payload));
    }

    @PutMapping("/devolver")
    public ResponseEntity<Prestamo> devolver(@RequestBody @Valid IdPrestamoDTO payload) {
        return ResponseEntity.ok(prestamoService.devolverLibro(payload.getPrestamoId()));
    }

    @PutMapping("/renovar")
    public ResponseEntity<Prestamo> renovar(@RequestBody @Valid IdPrestamoDTO payload) {
        return ResponseEntity.ok(prestamoService.renovarPrestamo(payload.getPrestamoId()));
    }

    @PutMapping("/rehabilitarLector")
    public ResponseEntity<Lector> rehabilitar(@RequestBody @Valid IdLectorDTO payload) {
        return ResponseEntity.ok(prestamoService.rehabilitarLector(payload.getLectorId()));
    }

    @GetMapping("/activos/lector/{lectorId}")
    public ResponseEntity<List<Prestamo>> activosLector(@PathVariable Long lectorId) {
        return ResponseEntity.ok(prestamoService.listarPrestamosActivosLector(lectorId));
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> listarTodos() {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }
}
