

drop database if exists uni4;

create database uni4;

use uni4;

CREATE USER 'uniuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON uni4 . * TO 'uniuser'@'localhost';


/* 
DROP TABLE hören;
DROP TABLE voraussetzen;
DROP TABLE prüfen;
DROP TABLE Vorlesungen;
DROP TABLE Studenten;
DROP TABLE Assistenten;
DROP TABLE Professoren; 
*/

CREATE TABLE Studenten
       (MatrNr         INTEGER PRIMARY KEY,
        Name           VARCHAR(30) NOT NULL,
        Semester       INTEGER);


CREATE TABLE Professoren
       (PersNr         INTEGER PRIMARY KEY,
        Name           VARCHAR(30) NOT NULL,
        Rang           CHAR(2) CHECK (Rang in ('C2', 'C3', 'C4')),
        Raum           INTEGER UNIQUE);

CREATE TABLE Assistenten
       (PersNr         INTEGER PRIMARY KEY,
        Name           VARCHAR(30) NOT NULL,
        Fachgebiet     VARCHAR(30),
        Boss           INTEGER,
		FOREIGN KEY (Boss) REFERENCES Professoren (PersNr));

CREATE TABLE Vorlesungen
       (VorlNr         INTEGER PRIMARY KEY,
        Titel          VARCHAR(30),
        SWS            INTEGER,
        gelesenVon     INTEGER, 
		FOREIGN KEY (gelesenVon) REFERENCES Professoren (PersNr));

CREATE TABLE hören
       (MatrNr         INTEGER,
        VorlNr         INTEGER,
        PRIMARY KEY    (MatrNr, VorlNr),
		FOREIGN KEY (MatrNr) REFERENCES Studenten (MatrNr),
		FOREIGN KEY (VorlNr) REFERENCES Vorlesungen (VorlNr));

CREATE TABLE voraussetzen
       (Vorgänger     INTEGER,
        Nachfolger     INTEGER,
        PRIMARY KEY    (Vorgänger, Nachfolger),
		FOREIGN KEY (Vorgänger) REFERENCES Vorlesungen (VorlNr),
		FOREIGN KEY (Nachfolger) REFERENCES Vorlesungen (VorlNr));

CREATE TABLE prüfen
       (MatrNr         INTEGER,
        VorlNr         INTEGER,
        PersNr         INTEGER,
        Note           NUMERIC(2,1) CHECK (Note between 0.7 and 5.0),
        PRIMARY KEY    (MatrNr, VorlNr),
		FOREIGN KEY (MatrNr) REFERENCES Studenten (MatrNr),
		FOREIGN KEY (VorlNr) REFERENCES Vorlesungen (VorlNr),
		FOREIGN KEY (PersNr) REFERENCES Professoren (PersNr)
		);
