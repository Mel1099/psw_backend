package it.progetto.psw.repositories;

import it.progetto.psw.entities.Carrello;
import it.progetto.psw.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {

    boolean existsById(int id);
    Carrello findById(int id);

    boolean existsByCliente(Cliente cliente);

    Carrello findByCliente(Cliente cliente);
}
