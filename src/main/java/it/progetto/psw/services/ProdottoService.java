package it.progetto.psw.services;

import it.progetto.psw.entities.Prodotto;
import it.progetto.psw.repositories.ProdottoRepository;
import it.progetto.psw.supports.BarCodeAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;



    @Transactional(readOnly = false)
    public Prodotto addProduct(Prodotto prodotto) throws BarCodeAlreadyExistException {
        if (prodotto.getBarCode() != null && prodottoRepository.existsByBarCode(prodotto.getBarCode())) {
            throw new BarCodeAlreadyExistException();
        }
        return prodottoRepository.save(prodotto);
    }

    @Transactional(readOnly = true)
    public List<Prodotto> showAllProducts() {
        return prodottoRepository.findAll();
    }



    @Transactional(readOnly = true)
    public List<Prodotto> showProductsByName(String name) {
        return prodottoRepository.findByNome(name);
    }


    @Transactional(readOnly = true)
    public Prodotto showProductByBarCode(String barCode) {
        return prodottoRepository.findByBarCode(barCode);
    }
}
