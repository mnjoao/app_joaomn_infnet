package br.edu.infnet.app_joaomn.repository;

import br.edu.infnet.app_joaomn.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
