package it.progetto.psw.controllers;


import it.progetto.psw.entities.Ordine;
import it.progetto.psw.services.OrdineService;
import it.progetto.psw.supports.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ordine")
public class OrdineController {
    @Autowired
    private OrdineService ordineService;


    @PostMapping
    public ResponseEntity create(@RequestBody @Validated Ordine ordine) {
        try {
            return new ResponseEntity<>(ordineService.addOrdine(ordine), HttpStatus.OK);
        } catch (ProductNotAvailableException e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasAuthority('admin')")
    @PutMapping("/{ordine}")
    public ResponseEntity modificaStato(@PathVariable Ordine ordine,@RequestParam(value = "stato") String stato) { // è buona prassi ritornare l'oggetto inserito
        try {
            return new ResponseEntity<>(ordineService.modificaStato(ordine,stato), HttpStatus.OK);
        } catch (OrdineNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Ordine non Trovato"), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<Ordine>result=ordineService.getAllOrdini();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/cliente")
    public ResponseEntity getOrdinibyUtente(@RequestParam(value="cliente")int c) {
        try {
            List<Ordine>result=ordineService.getOrdinibyCliente(c);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UtenteNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Utente non Trovato!"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity getOrdineInPeriodByCliente( @PathVariable int idCliente, @RequestParam(value = "start") @DateTimeFormat(pattern = "dd/MM/yyyy") Date start, @RequestParam(value = "end") @DateTimeFormat(pattern = "dd/MM/yyyy, HH:mm:ss") Date end) {
        try {
            System.out.println("D1"+start+ "D2"+end);
            List<Ordine> result = ordineService.getOrdineInPeriod(idCliente, start, end);

            if ( result.size() <= 0 ) {
                return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UtenteNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Utente non Trovato!"), HttpStatus.BAD_REQUEST);
        } catch (DateWrongRangeException e) {
            return new ResponseEntity<>(new ResponseMessage( "Start date must be previous end date XXX!"), HttpStatus.BAD_REQUEST);

        }
    }
    @DeleteMapping("/{ordine}")
    public ResponseEntity delete(@PathVariable("ordine") Ordine ordine){
        try {
            ordineService.delete(ordine);
            return new ResponseEntity<>(new ResponseMessage("Rimosso"),HttpStatus.OK);
        } catch (OrdineNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Ordine non Trovato!"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ordine/{id}")
    public ResponseEntity getOrdine(@PathVariable int id) {
        try {
            Ordine result=ordineService.getOrdineById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (OrdineNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Ordine non Trovato!"), HttpStatus.BAD_REQUEST);
        }
    }


}