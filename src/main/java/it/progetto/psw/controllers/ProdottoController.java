package it.progetto.psw.controllers;

import it.progetto.psw.entities.Prodotto;
import it.progetto.psw.services.ProdottoService;
import it.progetto.psw.supports.BarCodeAlreadyExistException;
import it.progetto.psw.supports.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
    @Autowired
    private ProdottoService prodottoService;

   // @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Validated Prodotto prodotto) {
        try {
            Prodotto p = prodottoService.addProduct(prodotto);
            return new ResponseEntity<>(p, HttpStatus.OK);

        } catch (BarCodeAlreadyExistException e) {
            return new ResponseEntity<>(new ResponseMessage("Barcode already exist!"), HttpStatus.BAD_REQUEST);
        }
    }




    @GetMapping
    public ResponseEntity getAll() {
        List<Prodotto> result = prodottoService.showAllProducts();
        if ( result.size() <= 0 ) {
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }




    @GetMapping("/search/byName")
    public ResponseEntity getByName(@RequestParam(required = false) String name) {

    List<Prodotto> result = prodottoService.showProductsByName(name);
        if ( result.size() <= 0 ) {
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/search/byBarCode")
    public ResponseEntity getByBarCode(@RequestParam(required = false) String barCode) {
        Prodotto result = prodottoService.showProductByBarCode(barCode);
        if ( result==null ) {
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
