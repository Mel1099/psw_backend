package it.progetto.psw.services;

import it.progetto.psw.entities.Cliente;
import it.progetto.psw.entities.Ordine;
import it.progetto.psw.entities.ProdottoInOrdine;
import it.progetto.psw.repositories.ClienteRepository;
import it.progetto.psw.repositories.OrdineRepository;
import it.progetto.psw.repositories.ProdottoInOrdineRepository;
import it.progetto.psw.repositories.ProdottoRepository;
import it.progetto.psw.supports.DateWrongRangeException;
import it.progetto.psw.supports.OrdineNotFoundException;
import it.progetto.psw.supports.ProductNotAvailableException;
import it.progetto.psw.supports.UtenteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;

@Service
public class OrdineService {
    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdottoRepository prodottoRepository;
    private ProdottoInOrdineRepository prodottoInOrdineRepository;
    @Autowired
    private EntityManager entityManager;


    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Transactional(readOnly = false,rollbackFor = {Exception.class})
    public Ordine addOrdine(Ordine ordine) throws ProductNotAvailableException {
        ordine.setStato("In Elaborazione");
        Ordine result = ordineRepository.save(ordine);
        for ( ProdottoInOrdine c : result.getProdottoInOrdine() ) {
            c.setOrdine(result);
            ProdottoInOrdine justAdded= prodottoInOrdineRepository.save(c);
            entityManager.refresh(justAdded);



        }
        entityManager.refresh(result);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Ordine> getOrdinibyCliente(int c) throws UtenteNotFoundException {
        if ( !clienteRepository.existsById(c) ) {
            throw new UtenteNotFoundException();
        }
        Cliente cliente= clienteRepository.findById(c);
        return ordineRepository.findByCliente(cliente);
    }
    @Transactional(readOnly = true)
    public List<Ordine> getOrdineInPeriod(Date startDate, Date endDate) throws DateWrongRangeException {
        if ( startDate.compareTo(endDate) >= 0 ) {
            throw new DateWrongRangeException();
        }
        return ordineRepository.findInPeriod(startDate, endDate);
    }
    @Transactional(readOnly = true)
    public List<Ordine> getOrdineInPeriod(int c, Date startDate, Date endDate) throws UtenteNotFoundException, DateWrongRangeException {
        if ( !clienteRepository.existsById(c) ) {
            throw new UtenteNotFoundException();
        }
        Cliente cliente= clienteRepository.findById(c);
        if ( startDate.compareTo(endDate) >= 0 ) {
            throw new DateWrongRangeException();
        }
        return ordineRepository.findByClienteInPeriod(startDate, endDate,cliente);
    }



    @Transactional(readOnly = true)
    public List<Ordine> getAllOrdini() {
        return ordineRepository.findAll();
    }

    @Transactional(readOnly = false,  propagation = Propagation.REQUIRED)
    public void delete(Ordine ordine) throws OrdineNotFoundException {
        if(!ordineRepository.existsById(ordine.getId()))
            throw new OrdineNotFoundException();
        System.out.println("ORDINE"+ordine.getId());
        List<ProdottoInOrdine> prodotti =ordine.getProdottoInOrdine();
        for(ProdottoInOrdine c: prodotti){
            prodottoInOrdineRepository.delete(c);
        }
        ordineRepository.delete(ordine);
    }

    public Ordine modificaStato(Ordine ordine,String stato) throws OrdineNotFoundException {
        if(!ordineRepository.existsById(ordine.getId())){
            throw new OrdineNotFoundException();
        }

        ordine.setStato(stato);
        if(stato.equals("Consegnato")){
            long date=System.currentTimeMillis();
            Date d= new Date(date);
            System.out.println(d);
            ordine.setDataConsegna(String.valueOf(date));
        }
        return ordineRepository.save(ordine);
    }

    public Ordine getOrdineById(int id) throws OrdineNotFoundException {
        if(!ordineRepository.existsById(id)){
            throw new OrdineNotFoundException();
        }
        Ordine res=ordineRepository.findById(id);
        return res;
    }
}
