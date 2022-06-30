package it.progetto.psw.controllers;

import it.progetto.psw.entities.Carrello;
import it.progetto.psw.entities.Cliente;
import it.progetto.psw.services.CarrelloService;
import it.progetto.psw.supports.CarrelloAlreadyExistsException;
import it.progetto.psw.supports.CarrelloNotFoundException;
import it.progetto.psw.supports.ResponseMessage;
import it.progetto.psw.supports.UtenteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrello")
public class CarrelloController {
    @Autowired
    private CarrelloService carrelloService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity addCarrello(@RequestBody @Validated Cliente cliente) {
        try {
            return new ResponseEntity<>(carrelloService.addCarrello(cliente), HttpStatus.OK);
        } catch (CarrelloAlreadyExistsException e) {
            return new ResponseEntity<>(new ResponseMessage("ERROR_CARRELLO_ALREADY_EXISTS"), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/prodotti/{id}")
    public ResponseEntity getProdotti(@PathVariable int id) {
        try {
            return new ResponseEntity<>(carrelloService.getProdottiCliente(id), HttpStatus.OK);
        } catch (UtenteNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("UTENTE_NOT_FOUND!"), HttpStatus.BAD_REQUEST);


        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getCarrello(@PathVariable int id) {
        try {
            return new ResponseEntity<>(carrelloService.getCarrello(id), HttpStatus.OK);
        } catch (CarrelloNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("ERROR_CARRELLO_NOT_FOUND!"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ByCliente/{idCliente}")
    public ResponseEntity getCarrelloByUtente(@PathVariable int idCliente) {
        try {
            return new ResponseEntity<>(carrelloService.getCarrelloByCliente(idCliente), HttpStatus.OK);
        } catch (UtenteNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("UTENTE_NOT_FOUND!"), HttpStatus.BAD_REQUEST);
        } catch (CarrelloNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("ERROR_CARRELLO_NOT_FOUND!"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity svuota(@PathVariable int idCliente) {
        try {
            return new ResponseEntity(carrelloService.svuota(idCliente), HttpStatus.OK);
        } catch (UtenteNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("ERRORE_CLIENTE NON TROVATO!"), HttpStatus.BAD_REQUEST);
        }

    }
}