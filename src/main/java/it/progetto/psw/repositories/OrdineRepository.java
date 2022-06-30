package it.progetto.psw.repositories;

import it.progetto.psw.entities.Cliente;
import it.progetto.psw.entities.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {


    List<Ordine> findAll();
    Ordine findById(int id);
    @Query("select o from Ordine o where o.dataOrdine >= ?1 and o.dataOrdine <= ?2")
    List<Ordine> findInPeriod(Date startDate, Date endDate);

    @Query("select o from Ordine o where o.dataOrdine >= ?1 and o.dataOrdine <= ?2 and o.cliente = ?3")
    List<Ordine> findByClienteInPeriod(Date startDate, Date endDate, Cliente cliente);

    List<Ordine> findByCliente(Cliente cliente);
}