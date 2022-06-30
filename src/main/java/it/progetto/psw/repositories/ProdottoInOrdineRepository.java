package it.progetto.psw.repositories;

import it.progetto.psw.entities.Ordine;
import it.progetto.psw.entities.ProdottoInOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoInOrdineRepository extends JpaRepository<ProdottoInOrdine, Integer> {
    ProdottoInOrdine findById(int id);
    List<ProdottoInOrdine> findByOrdine(Ordine ordine);

}