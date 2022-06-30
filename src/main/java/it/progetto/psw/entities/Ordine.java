package it.progetto.psw.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "ordine", schema = "negozio")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_ordine")
    private Date dataOrdine;



    @Basic
    @Column(name = "indirizzo_consegna")
    private String indirizzoConsegna;

    @Basic
    @Column(name = "data_consegna")
    private String dataConsegna;

    @Basic
    @Column(name = "stato",nullable = false)
    private String stato;
    @Basic
    @Column(name = "metodo_pagamento",nullable = false)
    private String metodoPagamento;

    @ManyToOne
    @JoinColumn(name = "cliente",nullable = false)
    private Cliente cliente;



    @OneToMany(mappedBy = "ordine", cascade = CascadeType.MERGE)
    private List<ProdottoInOrdine> prodottoInOrdine;



    @Transient
    public float getTotale(){
        float sum = 0;
        List<ProdottoInOrdine> prodotti = getProdottoInOrdine();
        for (ProdottoInOrdine p : prodotti) {
            sum += p.getQuantita()*p.getProdotto().getPrezzo();
        }
        return sum;
    }
}