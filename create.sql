CREATE TABLE HIVAS(hivasId INT PRIMARY KEY, idopont TIMESTAMP, hivo varchar2(15), mentotkuld int, helyszin varchar2(30), leiras varchar2(50));

CREATE TABLE NAPLO(naploId INT PRIMARY KEY, kierkezes TIMESTAMP, felhasznalo varchar2(20), sikeres int, hivasId int, FOREIGN KEY (hivasId) REFERENCES HIVAS(hivasId));

CREATE TABLE BETEG(betegId INT PRIMARY KEY,nev varchar2(15), tajszam varchar2(15), diagnozis varchar2(20), hivasId int, FOREIGN KEY (hivasId) REFERENCES HIVAS(hivasId));

CREATE TABLE AUTO(autoId INT PRIMARY KEY, tipus varchar2(15), rendszam varchar2(8));

CREATE TABLE ALKALMAZOTT(aId INT PRIMARY KEY, nev varchar2(15), beosztas varchar2(10), autoId int, FOREIGN KEY (autoId) REFERENCES AUTO(autoId));

CREATE TABLE H_A(hivasId int, FOREIGN KEY (hivasId) REFERENCES HIVAS(hivasId), autoId int, FOREIGN KEY (autoId) REFERENCES AUTO(autoId));
