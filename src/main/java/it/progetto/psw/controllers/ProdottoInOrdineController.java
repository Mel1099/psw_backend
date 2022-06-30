package it.progetto.psw.controllers;

import it.progetto.psw.entities.Ordine;
import it.progetto.psw.entities.ProdottoInOrdine;
import it.progetto.psw.services.ProdottoInOrdineService;
import it.progetto.psw.supports.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prodottiInOrdine")
public class ProdottoInOrdineController {
    @Autowired
    private ProdottoInOrdineService prodottoInOrdineService;

    @GetMapping("/{ordine}")
    public ResponseEntity getProduts(@PathVariable("ordine") Ordine ordine) {
        List<ProdottoInOrdine> result = prodottoInOrdineService.getAll(ordine);
        if (result.size() <= 0) {
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
