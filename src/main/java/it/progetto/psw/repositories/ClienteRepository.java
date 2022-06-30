package it.progetto.psw.repositories;

import it.progetto.psw.entities.Cliente;
import it.progetto.psw.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByUtente(Utente utente);
    Cliente findById(int id);
}