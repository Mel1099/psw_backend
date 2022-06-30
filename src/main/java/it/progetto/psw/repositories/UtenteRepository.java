package it.progetto.psw.repositories;

import it.progetto.psw.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository  extends JpaRepository<Utente, Integer> {

    //List<Utente> findAll();

    List<Utente> findByNome(String nome);

    List<Utente> findByCognome(String cognome);

    Utente findByEmail(String email);
    Utente findById(int id);

    boolean existsByEmail(String email);

}