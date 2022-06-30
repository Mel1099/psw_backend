package it.progetto.psw.repositories;

import it.progetto.psw.entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {

    List<Prodotto> findByNome(String nome);
    Prodotto findByBarCode(String barCode);
    boolean existsByBarCode(String barCode);

}
