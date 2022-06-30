
DROP SCHEMA gioielleria;
CREATE SCHEMA gioielleria;



USE gioielleria;



CREATE TABLE utente (
                        id INTEGER AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(50) NOT NULL,
                        cognome VARCHAR(50) NOT NULL,
                        numero VARCHAR(20) NOT NULL,
                        email VARCHAR(90) NOT NULL,
                        indirizzo VARCHAR(150)
);

CREATE TABLE cliente (
                         id INTEGER AUTO_INCREMENT PRIMARY KEY,
                         utente INTEGER UNIQUE,
                         FOREIGN KEY (utente) REFERENCES utente (id)
);




CREATE TABLE prodotto (
                          id INTEGER AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(50) NOT NULL,
                          bar_code VARCHAR(70) NOT NULL,
                          descrizione VARCHAR(500) NOT NULL,
                          prezzo FLOAT NOT NULL




);

CREATE TABLE ordine (
                        id INTEGER AUTO_INCREMENT PRIMARY KEY,
                        cliente INTEGER,
                        data_ordine DATETIME DEFAULT CURRENT_TIMESTAMP,
                        indirizzo_consegna VARCHAR(500) NOT NULL,
                        data_consegna VARCHAR(500) NOT NULL,
                        metodo_pagamento VARCHAR(20) NOT NULL,
                        stato VARCHAR(20) NOT NULL,
                        FOREIGN KEY (cliente) REFERENCES cliente (id)
);

CREATE TABLE prodotto_ordine (
                                 id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                 ordine INTEGER,
                                 quantita INTEGER NOT NULL,
                                 prodotto INTEGER,
                                 FOREIGN KEY (ordine) REFERENCES ordine (id),
                                 FOREIGN KEY (prodotto) REFERENCES prodotto (id)
);
CREATE TABLE carrello(
                         id INTEGER AUTO_INCREMENT PRIMARY KEY,
                         cliente INTEGER,
                         FOREIGN KEY (cliente) REFERENCES cliente (id)
);



CREATE TABLE prodotto_nel_carrello (
                                       id INTEGER AUTO_INCREMENT PRIMARY KEY,
                                       prodotto INTEGER,
                                       quantita INTEGER NOT NULL,
                                       carrello INTEGER,
                                       FOREIGN KEY (prodotto) REFERENCES prodotto (id),
                                       FOREIGN KEY (carrello) REFERENCES carrello (id)
);