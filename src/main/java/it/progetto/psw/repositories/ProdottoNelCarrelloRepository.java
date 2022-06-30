package it.progetto.psw.repositories;

import it.progetto.psw.entities.Carrello;
import it.progetto.psw.entities.ProdottoNelCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoNelCarrelloRepository  extends JpaRepository<ProdottoNelCarrello, Integer> {

    List<ProdottoNelCarrello> findAll();


    ProdottoNelCarrello findById(int id);

    List<ProdottoNelCarrello> findByCarrello(Carrello carrello);
}