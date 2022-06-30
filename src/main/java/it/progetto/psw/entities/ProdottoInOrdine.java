package it.progetto.psw.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "prodotto_ordine", schema = "negozio")
public class ProdottoInOrdine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ordine")
    @JsonIgnore
    @ToString.Exclude
    private Ordine ordine;

    @Basic
    @Column(name = "quantita", nullable = false)
    private int quantita;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;




}