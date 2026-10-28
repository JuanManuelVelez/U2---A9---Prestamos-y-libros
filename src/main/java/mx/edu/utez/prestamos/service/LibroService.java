package mx.edu.utez.prestamos.service;

import mx.edu.utez.prestamos.controller.libro.dto.CrearLibroDTO;
import mx.edu.utez.prestamos.exception.RecursoNoEncontradoException;
import mx.edu.utez.prestamos.model.Libro;
import mx.edu.utez.prestamos.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {
    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Transactional
    public Libro crear(CrearLibroDTO payload) {
        Libro libro = new Libro();
        libro.setTitulo(payload.getTitulo());
        libro.setEjemplaresDisponibles(payload.getEjemplaresDisponibles());
        return libroRepository.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Libro buscar(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el libro con ID " + id));
    }
}
