-- Active: 1738659250429@@172.80.237.53@3306@db_s2_ETU003184
CREATE DATABASE IF NOT EXISTS db_s2_ETU003184;
USE db_s2_ETU003184;
DROP DATABASE db_s2_ETU003184;

CREATE TABLE db_s2_ETU003184_prevision (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(50),
    montant DECIMAL(12, 2)
);

DROP TABLE db_s2_ETU003184_depense;
CREATE TABLE db_s2_ETU003184_depense(
    id INT PRIMARY KEY AUTO_INCREMENT,
    idPrevision INT,
    montant DECIMAL(12, 2),
    FOREIGN KEY (idPrevision) REFERENCES db_s2_ETU003184_prevision(id)
);


INSERT INTO prevision(id, libelle, montant) VALUES("Sakafo", 20);

