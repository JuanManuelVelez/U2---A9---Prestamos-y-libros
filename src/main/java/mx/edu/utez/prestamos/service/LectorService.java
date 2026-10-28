package mx.edu.utez.prestamos.service;

import mx.edu.utez.prestamos.controller.lector.dto.CrearLectorDTO;
import mx.edu.utez.prestamos.exception.RecursoNoEncontradoException;
import mx.edu.utez.prestamos.model.Lector;
import mx.edu.utez.prestamos.repository.LectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LectorService {
    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    @Transactional
    public Lector crear(CrearLectorDTO payload) {
        Lector lector = new Lector();
        lector.setNombre(payload.getNombre());
        lector.setActivo(payload.isActivo());
        lector.setMembresiaPremium(payload.isMembresiaPremium());
        return lectorRepository.save(lector);
    }

    @Transactional(readOnly = true)
    public List<Lector> listar() {
        return lectorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Lector buscar(Long id) {
        return lectorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el lector con ID " + id));
    }
}
