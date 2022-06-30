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
    @Table(name = "cliente", schema = "negozio")
    public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private int id;

        @OneToOne(cascade = CascadeType.MERGE)
        @JoinColumn(name = "utente")
        private Utente utente;

        @OneToMany(mappedBy = "cliente", cascade = CascadeType.MERGE)
        @JsonIgnore
        private List<Ordine> acquisti;


        @OneToOne(mappedBy="cliente")
        @JsonIgnore
        private Carrello carrello;


    }
