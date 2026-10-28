package mx.edu.utez.prestamos.service;

import mx.edu.utez.prestamos.controller.prestamo.dto.SolicitarPrestamoDTO;
import mx.edu.utez.prestamos.exception.RecursoNoEncontradoException;
import mx.edu.utez.prestamos.exception.ReglaNegocioException;
import mx.edu.utez.prestamos.model.EstadoPrestamo;
import mx.edu.utez.prestamos.model.Lector;
import mx.edu.utez.prestamos.model.Libro;
import mx.edu.utez.prestamos.model.Prestamo;
import mx.edu.utez.prestamos.repository.LectorRepository;
import mx.edu.utez.prestamos.repository.LibroRepository;
import mx.edu.utez.prestamos.repository.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {
    private static final List<EstadoPrestamo> ESTADOS_ACTIVOS =
            List.of(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO);

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LectorRepository lectorRepository,
                           LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional
    public Prestamo solicitarPrestamo(SolicitarPrestamoDTO payload) {
        Lector lector = lectorRepository.findById(payload.getLectorId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el lector con ID " + payload.getLectorId()));

        if (!lector.isActivo()) {
            throw new ReglaNegocioException("El lector está bloqueado y no puede solicitar préstamos");
        }

        Libro libro = libroRepository.findById(payload.getLibroId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el libro con ID " + payload.getLibroId()));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new ReglaNegocioException("No hay ejemplares disponibles de ese libro");
        }

        long prestamosActivos = prestamoRepository.countByLectorIdAndEstadoIn(lector.getId(), ESTADOS_ACTIVOS);
        int limite = lector.isMembresiaPremium() ? 5 : 3;

        if (prestamosActivos >= limite) {
            throw new ReglaNegocioException("El lector alcanzó el límite de " + limite + " préstamos activos");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverLibro(Long prestamoId) {
        Prestamo prestamo = buscarPrestamo(prestamoId);

        if (prestamo.getEstado() != EstadoPrestamo.PRESTADO
                && prestamo.getEstado() != EstadoPrestamo.VENCIDO) {
            throw new ReglaNegocioException("El préstamo ya fue devuelto");
        }

        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
            prestamo.getLector().setActivo(false);
            lectorRepository.save(prestamo.getLector());
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = buscarPrestamo(prestamoId);

        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO
                || prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new ReglaNegocioException("No se puede renovar un préstamo vencido o devuelto");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Lector rehabilitarLector(Long lectorId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el lector con ID " + lectorId));

        lector.setActivo(true);
        return lectorRepository.save(lector);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamosActivosLector(Long lectorId) {
        if (!lectorRepository.existsById(lectorId)) {
            throw new RecursoNoEncontradoException("No existe el lector con ID " + lectorId);
        }
        return prestamoRepository.findByLectorIdAndEstadoIn(lectorId, ESTADOS_ACTIVOS);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    private Prestamo buscarPrestamo(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el préstamo con ID " + id));
    }
}
