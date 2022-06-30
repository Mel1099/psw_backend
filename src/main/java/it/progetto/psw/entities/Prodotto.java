package it.progetto.psw.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "prodotto", schema = "gioielleria")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Basic
    @Column(name = "bar_code", nullable = false, length = 70)
    private String barCode;

    @Basic
    @Column(name = "descrizione", nullable = true, length = 500)
    private String descrizione;

    @Basic
    @Column(name = "prezzo", nullable = false)
    private float prezzo;











}


