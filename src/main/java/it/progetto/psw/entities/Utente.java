package it.progetto.psw.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    @Entity
    @Table(name = "utente", schema = "negozio")
    public class Utente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private int id;

        @Basic
        @Column(name = "nome", nullable = false, length = 50)
        private String nome;

        @Basic
        @Column(name = "cognome", nullable = false, length = 50)
        private String cognome;

        @Basic
        @Column(name = "numero", nullable = false, length = 20)
        private String numero;

        @Basic
        @Column(name = "email", nullable = false, length = 90)
        private String email;

        @Basic
        @Column(name = "indirizzo", nullable = true, length = 150)
        private String indirizzo;



    }
