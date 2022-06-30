package it.progetto.psw.services;

import it.progetto.psw.entities.Carrello;
import it.progetto.psw.entities.Cliente;
import it.progetto.psw.entities.ProdottoNelCarrello;
import it.progetto.psw.repositories.CarrelloRepository;
import it.progetto.psw.repositories.ClienteRepository;
import it.progetto.psw.repositories.ProdottoNelCarrelloRepository;
import it.progetto.psw.supports.CarrelloAlreadyExistsException;
import it.progetto.psw.supports.CarrelloNotFoundException;
import it.progetto.psw.supports.UtenteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class CarrelloService {
    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private ProdottoNelCarrelloRepository prodottoNelCarrelloRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = false)
    public Carrello addCarrello(Cliente cliente) throws CarrelloAlreadyExistsException {
        if(carrelloRepository.existsByCliente(cliente)) {
            throw new CarrelloAlreadyExistsException();
        }
        Carrello c= new Carrello();
        c.setCliente(cliente);
        return carrelloRepository.save(c);
    }


    @Transactional(readOnly = true)
    public List<ProdottoNelCarrello> getProdottiCliente(int id) throws  UtenteNotFoundException {


        if(!clienteRepository.existsById(id))
            throw new UtenteNotFoundException();

        Cliente cliente = clienteRepository.findById(id);
        return carrelloRepository.findByCliente(cliente).getProdottoNelCarrello();
    }


    @Transactional(readOnly = false)
    public Carrello svuota(int  idCliente) throws  UtenteNotFoundException {
        if(!clienteRepository.existsById(idCliente))
            throw new UtenteNotFoundException();

        Cliente cliente = clienteRepository.findById(idCliente);
        Carrello carrello =carrelloRepository.findByCliente(cliente);

        List<ProdottoNelCarrello> list= prodottoNelCarrelloRepository.findByCarrello(carrello);
        for (ProdottoNelCarrello p : list) {
            prodottoNelCarrelloRepository.delete(p);

        }

        entityManager.refresh(carrello);
        return carrello;
    }

    @Transactional(readOnly = true)
    public Carrello getCarrello(int id) throws CarrelloNotFoundException {

        if(!carrelloRepository.existsById(id)) {
            throw new CarrelloNotFoundException();
        }
        return carrelloRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Carrello getCarrelloByCliente(int id) throws CarrelloNotFoundException, UtenteNotFoundException {
        if(!clienteRepository.existsById(id))
            throw new UtenteNotFoundException();
        System.out.println("EEEEEEEEEE");
        Cliente cliente= clienteRepository.findById(id);
        if(!carrelloRepository.existsByCliente(cliente)) {
            throw new CarrelloNotFoundException();
        }
        return carrelloRepository.findByCliente(cliente);
    }
}
