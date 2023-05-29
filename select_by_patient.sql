CREATE OR REPLACE PROCEDURE hivasok_beteg(p_beteg varchar2,p_result OUT hivas_cursor) IS
  BEGIN
    OPEN p_result FOR
      SELECT h.hivasId, h.idopont, b.nev, b.tajszam, b.diagnozis
                 FROM hivas h
                 JOIN beteg b ON h.hivasId = b.hivasId
                 WHERE b.nev = p_beteg;
  END;
