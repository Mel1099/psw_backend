package it.progetto.psw.controllers;

import it.progetto.psw.entities.ProdottoNelCarrello;
import it.progetto.psw.services.ProdottoNelCarrelloService;
import it.progetto.psw.supports.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prodottoNelCarrello")
public class ProdottoNelCarrelloController {
    @Autowired
    private ProdottoNelCarrelloService prodottoNelCarrelloService;


    @PostMapping
    public ResponseEntity aggiungi( @RequestParam(value="idCarrello") int idCarrello, @RequestParam(value="quantita") int quantita) {
        try {
            return new ResponseEntity(prodottoNelCarrelloService.insert(idCarrello,quantita), HttpStatus.OK);
        } catch (CarrelloNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Carrello not found"), HttpStatus.BAD_REQUEST);

        } catch (ProdottoInListNotFound prodottoInListNotFound) {
            return new ResponseEntity<>(new ResponseMessage("Prodotto not available"), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity rimuoviProdotto(@PathVariable int id){
        try{
            return new ResponseEntity(prodottoNelCarrelloService.delete(id),HttpStatus.OK);
        }catch (ProdottoInListNotFound e){
            return new ResponseEntity<>(new ResponseMessage("Prodotto not found"), HttpStatus.BAD_REQUEST);


        }
    }
    @PutMapping("/modifica")
    public ResponseEntity modifica(@RequestParam(value="id") int id, @RequestParam(value="quantita") int quantita){
        try{
            return new ResponseEntity(prodottoNelCarrelloService.modificaQuantita(id,quantita),HttpStatus.OK);
        }catch ( ProdottoNelCarrelloNotFoundException e){
            return new ResponseEntity<>(new ResponseMessage("Prodotto not found"), HttpStatus.BAD_REQUEST);

        } catch (ProductNotAvailableException e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/aggiungi")
    public ResponseEntity aggiungiQuantita(@RequestParam(value="id") int id, @RequestParam(value="quantita") int quantita){
        try{
            return new ResponseEntity(prodottoNelCarrelloService.aggiungiquantita(id,quantita),HttpStatus.OK);
        }catch ( ProdottoNelCarrelloNotFoundException e){
            return new ResponseEntity<>(new ResponseMessage("Prodotto not found"), HttpStatus.BAD_REQUEST);

        } catch (ProductNotAvailableException e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/rimuovi")
    public ResponseEntity rimuoviQuantita(@RequestParam(value="id") int id, @RequestParam(value="quantita") int quantita){
        try{
            ProdottoNelCarrello rem=prodottoNelCarrelloService.rimuoviquantita(id,quantita);
            if(rem.getQuantita()==0) {
                String rim = "Articolo rimosso";
                return new ResponseEntity(rim,HttpStatus.OK);
            }

            return new ResponseEntity(rem,HttpStatus.OK);
        }catch (ProdottoInListNotFound e){
            return new ResponseEntity<>(new ResponseMessage("Prodotto not fount"), HttpStatus.BAD_REQUEST);

        }
    }
}
