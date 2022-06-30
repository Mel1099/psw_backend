package it.progetto.psw.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.progetto.psw.entities.Utente;
import it.progetto.psw.repositories.UtenteRepository;
import it.progetto.psw.supports.InvalidEmailFormatException;
import it.progetto.psw.supports.MailUtenteAlreadyExistsException;
import it.progetto.psw.supports.TokenErrorException;
import it.progetto.psw.supports.UtenteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Utente registerUtente(Utente utente) throws MailUtenteAlreadyExistsException, InvalidEmailFormatException {
        String regexEmail = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new MailUtenteAlreadyExistsException();
        }
        if (!Pattern.matches(regexEmail, utente.getEmail())) {
            throw new InvalidEmailFormatException();
        }

        Utente u= utenteRepository.save(utente);
        return u;
    }

    @Transactional(readOnly = true)
    public List<Utente> getAllUsers() {
        return utenteRepository.findAll();
    }
    @Transactional(readOnly = false)
    public Utente addIndirizzo(Utente utente,String indirizzo) throws UtenteNotFoundException {
        System.out.println(indirizzo);
        if(!utenteRepository.existsById(utente.getId())){
            throw new UtenteNotFoundException();
        }

        utente.setIndirizzo(indirizzo);
        return utenteRepository.save(utente);



    }
/*
    @Transactional(readOnly = true)
    public List<String> getRole(String email) throws UtenteNotFoundException {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username_admin)
                .password(password_admin)
                .build();


        // Get realm
        RealmResource realmResource = keycloak.realm(realm_client);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> response = usersResource.search(email);
        if(response.size()==0){
            throw new UtenteNotFoundException();
        }
        UserResource userResource = usersResource.get( response.get(0).getId());
        ClientRepresentation app1Client = realmResource.clients().findByClientId(nome_client).get(0);
        RoleMappingResource role=userResource.roles();

        List<RoleRepresentation> res=role.clientLevel(app1Client.getId()).listAll();
        List<String>result= new LinkedList<>();
        for(RoleRepresentation r:res){
            result.add(r.getName());

        }
        System.out.println(result);
        return result;


    }
*/
    public Utente getByAccessToken(String token) throws JsonProcessingException, UtenteNotFoundException, TokenErrorException, JsonProcessingException {
        String parts[] = token.split("\\.");
        if (parts.length != 3) {
            throw new TokenErrorException();
        }
        String payload= decodeBase64(parts[1]);
        ObjectMapper objectMapper= new ObjectMapper();
        HashMap payloadMap = objectMapper.readValue(payload,HashMap.class);
        if (payloadMap.size()==0) {
            throw new TokenErrorException();
        }
        String email=(String)payloadMap.get("email");
        if(!utenteRepository.existsByEmail(email)){
            throw new UtenteNotFoundException();
        }
        Utente utente = utenteRepository.findByEmail(email);
        return utente;
    }
    private String decodeBase64(String str) {
        String output = str.replaceAll("-", "+").replaceAll("_", "/");

        switch (output.length() % 4) {
            case 0:
                break;
            case 2:
                output += "==";
                break;
            case 3:
                output += "=";
                break;
            default:
                throw  new IllegalArgumentException();
        }
        return new String(Base64.getUrlDecoder().decode(output));


    }

    @Transactional
    public Utente putInformation(Utente utente, String nome, String cognome, String email, String numero) throws UtenteNotFoundException {
        if(!utenteRepository.existsById(utente.getId())){
            throw new UtenteNotFoundException();
        }
        System.out.println(nome);
        System.out.println(cognome);   System.out.println(email);   System.out.println(numero);

        if(nome!=null){
            utente.setNome(nome);
        }
        if(cognome!=null){
            utente.setCognome(cognome);
        }
        if(email!= null){
            utente.setEmail(email);
        }
        if(numero!=null){
            utente.setNumero(numero);
        }
        System.out.println(utente);
        utenteRepository.save(utente);
        return utente;
    }
}
