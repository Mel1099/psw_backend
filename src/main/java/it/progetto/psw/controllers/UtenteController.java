package it.progetto.psw.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.progetto.psw.entities.Utente;
import it.progetto.psw.services.UtenteService;
import it.progetto.psw.supports.ResponseMessage;
import it.progetto.psw.supports.TokenErrorException;
import it.progetto.psw.supports.UtenteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;


    @PutMapping("/address/{utente}")
    public ResponseEntity aggiungi(@PathVariable Utente utente, @RequestParam(value = "indirizzo") String indirizzo){
        try{
            Utente u=utenteService.addIndirizzo(utente,indirizzo);
            return new ResponseEntity<>(u, HttpStatus.OK);
        }catch ( UtenteNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Cliente non trovato!"), HttpStatus.BAD_REQUEST);
        }

    }
    @PutMapping("/info/{utente}")
    public ResponseEntity putInformation(@PathVariable Utente utente,@RequestParam(value = "nome", required = false)String nome,@RequestParam(value = "cognome", required = false)String cognome,@RequestParam(value = "email", required = false)String email,@RequestParam(value = "numero", required = false)String numero){
        try{
            Utente c= utenteService.putInformation(utente,nome,cognome,email,numero);
            return new ResponseEntity<>(c, HttpStatus.OK);
        }catch ( UtenteNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Utente non trovato!"), HttpStatus.BAD_REQUEST);
        }
    }
   /* @GetMapping("/role")
    public ResponseEntity getRole(@RequestParam(value = "email") String email){
        try{
            return new ResponseEntity( utenteService.getRole(email), HttpStatus.OK);
        }catch ( UtenteNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Utente Non Trovato!"), HttpStatus.BAD_REQUEST);
        }

    }
    */


    @GetMapping("/keycloak")
    public ResponseEntity getByAccessToken(@RequestParam(value = "token") String token) {
        try{
            Utente c=utenteService.getByAccessToken(token);
            return new ResponseEntity<>(c, HttpStatus.OK);
        }catch ( UtenteNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Utente non trovato!"), HttpStatus.BAD_REQUEST);
        } catch (TokenErrorException e) {
            return new ResponseEntity<>(new ResponseMessage("Token non valido!"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ResponseMessage("Impossibile decodificare il token!"), HttpStatus.BAD_REQUEST);
        }
    }
}

