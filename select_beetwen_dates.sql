CREATE OR REPLACE PROCEDURE hivasok_datum(p_idopont1 varchar2, p_idopont2 varchar2, p_result OUT hivas_cursor) is
  BEGIN
    OPEN p_result FOR
      SELECT * FROM hivas WHERE idopont > TO_TIMESTAMP(p_idopont1,'YYYY/MM/DD') AND idopont < TO_TIMESTAMP(p_idopont2, 'YYYY/MM/DD');
  END;
END;