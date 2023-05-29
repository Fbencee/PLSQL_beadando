CREATE OR REPLACE PROCEDURE hivasok(p_hivo VARCHAR2, p_result OUT hivas_cursor) IS
  BEGIN
    OPEN p_result FOR
      SELECT *
      FROM hivas
      WHERE hivo = p_hivo;
  END;