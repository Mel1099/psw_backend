package it.progetto.psw.services;

import it.progetto.psw.entities.Ordine;
import it.progetto.psw.entities.ProdottoInOrdine;
import it.progetto.psw.repositories.ProdottoInOrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdottoInOrdineService {
    @Autowired
    private ProdottoInOrdineRepository prodottoInOrdineRepository;

    @Transactional(readOnly = true)
    public List<ProdottoInOrdine> getAll(Ordine ordine){
        return prodottoInOrdineRepository.findByOrdine(ordine);
    }
}
