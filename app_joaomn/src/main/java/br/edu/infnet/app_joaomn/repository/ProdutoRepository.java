package br.edu.infnet.app_joaomn.repository;

import br.edu.infnet.app_joaomn.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
