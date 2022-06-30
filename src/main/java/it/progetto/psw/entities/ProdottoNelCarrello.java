package it.progetto.psw.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "prodotto_nel_carrello", schema = "negozio")
public class ProdottoNelCarrello {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "quantita", nullable = false)
    private int quantita;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "carrello")
    private Carrello carrello;


}

