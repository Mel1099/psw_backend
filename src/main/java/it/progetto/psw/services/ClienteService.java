package it.progetto.psw.services;

import it.progetto.psw.entities.Cliente;
import it.progetto.psw.entities.Utente;
import it.progetto.psw.repositories.ClienteRepository;
import it.progetto.psw.repositories.UtenteRepository;
import it.progetto.psw.supports.UtenteNotFoundException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private UtenteRepository utenteRepository;


    @Transactional(readOnly = true)
    public List<Cliente> getAll(){
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente getCliente(String email) throws UtenteNotFoundException {
        if(!utenteRepository.existsByEmail(email)){
            throw new UtenteNotFoundException();
        }
        Utente utente = utenteRepository.findByEmail(email);
        return clienteRepository.findByUtente(utente);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Cliente registerCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }







}

