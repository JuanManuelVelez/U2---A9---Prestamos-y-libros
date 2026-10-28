package mx.edu.utez.prestamos.repository;

import mx.edu.utez.prestamos.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectorRepository extends JpaRepository<Lector, Long> {
}
