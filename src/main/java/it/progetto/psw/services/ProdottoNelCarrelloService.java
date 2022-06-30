package it.progetto.psw.services;

import it.progetto.psw.entities.Carrello;
import it.progetto.psw.entities.ProdottoNelCarrello;
import it.progetto.psw.repositories.CarrelloRepository;
import it.progetto.psw.repositories.ProdottoNelCarrelloRepository;
import it.progetto.psw.supports.CarrelloNotFoundException;
import it.progetto.psw.supports.ProdottoInListNotFound;
import it.progetto.psw.supports.ProdottoNelCarrelloNotFoundException;
import it.progetto.psw.supports.ProductNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdottoNelCarrelloService {
    @Autowired
    private ProdottoNelCarrelloRepository prodottoNelCarrelloRepository;
    @Autowired
    private CarrelloRepository carrelloRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ProdottoNelCarrello insert( int idCarrello, int quantita) throws CarrelloNotFoundException, ProdottoInListNotFound {
        if(!carrelloRepository.existsById(idCarrello))
            throw new CarrelloNotFoundException();
        Carrello carrello = carrelloRepository.findById(idCarrello);

            ProdottoNelCarrello prodottoNelCarrello = new ProdottoNelCarrello();
            prodottoNelCarrello.setCarrello(carrello);
            prodottoNelCarrello.setQuantita(quantita);

            return prodottoNelCarrelloRepository.save(prodottoNelCarrello);

    }

    @Transactional(readOnly = false)
    public ProdottoNelCarrello modificaQuantita(int id,int quantità) throws ProdottoNelCarrelloNotFoundException, ProductNotAvailableException {
        if (!prodottoNelCarrelloRepository.existsById(id))
            throw new ProdottoNelCarrelloNotFoundException();
        ProdottoNelCarrello pC = prodottoNelCarrelloRepository.findById(id);
        pC.setQuantita(quantità);

        return prodottoNelCarrelloRepository.save(pC);
    }


    @Transactional(rollbackFor = {Exception.class})
    public ProdottoNelCarrello aggiungiquantita(int id,int quantità) throws ProdottoNelCarrelloNotFoundException, ProductNotAvailableException {
        if(!prodottoNelCarrelloRepository.existsById(id))
            throw new ProdottoNelCarrelloNotFoundException();
        ProdottoNelCarrello pC= prodottoNelCarrelloRepository.findById(id);
        pC.setQuantita(pC.getQuantita()+quantità);
        if(pC.getQuantita()<0)
            throw new ProductNotAvailableException("Impossibile aggiungere quantità prodotto ");

        return prodottoNelCarrelloRepository.save(pC);




    }
    @Transactional(readOnly = false,  propagation = Propagation.REQUIRED)
    public ProdottoNelCarrello rimuoviquantita(int prodotto,int quantità) throws ProdottoInListNotFound {
        if(!prodottoNelCarrelloRepository.existsById(prodotto))
            throw new ProdottoInListNotFound();
        ProdottoNelCarrello pC= prodottoNelCarrelloRepository.findById(prodotto);
        pC.setQuantita(pC.getQuantita()-quantità);
        if(pC.getQuantita()<=0){
            prodottoNelCarrelloRepository.delete(pC);
            return pC;
        }


        return prodottoNelCarrelloRepository.save(pC);




    }



    @Transactional(readOnly = false,  propagation = Propagation.REQUIRED)
    public ProdottoNelCarrello delete(int  prodotto) throws ProdottoInListNotFound {
        if(!prodottoNelCarrelloRepository.existsById(prodotto))
            throw new ProdottoInListNotFound();
        ProdottoNelCarrello pC= prodottoNelCarrelloRepository.findById(prodotto);

        prodottoNelCarrelloRepository.delete(pC);
        return pC;
    }
}
