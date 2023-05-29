 PROCEDURE hivasok_datum(p_idopont1 varchar2, p_idopont2 varchar2) is
  BEGIN
   FOR hivasok IN (SELECT * FROM hivas WHERE idopont > TO_TIMESTAMP(p_idopont1,'YYYY/MM/DD') AND idopont < TO_TIMESTAMP(p_idopont2, 'YYYY/MM/DD')) LOOP
    DBMS_OUTPUT.PUT_LINE('Hívás ID: ' || hivasok.hivasId);
    DBMS_OUTPUT.PUT_LINE('Hívás dátuma: ' || hivasok.idopont);
    DBMS_OUTPUT.PUT_LINE('Hívó neve: ' || hivasok.hivo);
    DBMS_OUTPUT.PUT_LINE('Hívás helyszine: ' || hivasok.helyszin);
    DBMS_OUTPUT.PUT_LINE('Hívás leírása: ' || hivasok.leiras);
    IF hivasok.mentotkuld = 1 THEN
     DBMS_OUTPUT.PUT_LINE('Mentő kiküldve.');
    ELSE
     DBMS_OUTPUT.PUT_LINE('Nincs mentő küldve.');
    END IF;
    DBMS_OUTPUT.PUT_LINE('-------------------');
   END LOOP;
  END;

  PROCEDURE hivasok_beteg(p_beteg varchar2) IS
  BEGIN
   FOR adatok IN (SELECT h.hivasId, h.idopont, b.nev, b.tajszam, b.diagnozis
                 FROM hivas h
                 JOIN beteg b ON h.hivasId = b.hivasId
                 WHERE b.nev = p_beteg) LOOP
    DBMS_OUTPUT.PUT_LINE('Hívás ID: ' || adatok.hivasId);
    DBMS_OUTPUT.PUT_LINE('Hívás dátuma: ' || adatok.idopont);
    DBMS_OUTPUT.PUT_LINE('Beteg neve: ' || adatok.nev);
    DBMS_OUTPUT.PUT_LINE('Beteg tajszáma: ' || adatok.tajszam);
    DBMS_OUTPUT.PUT_LINE('Beteg diagnózis: ' || adatok.diagnozis);
   END LOOP;
  END;
