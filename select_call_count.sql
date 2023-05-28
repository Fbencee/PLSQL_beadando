CREATE OR REPLACE FUNCTION hivasokszama(p_datum varchar2) RETURN NUMBER is
 hivasok NUMBER;
BEGIN
 SELECT COUNT(*) INTO hivasok FROM HIVAS WHERE idopont > TO_TIMESTAMP(p_datum,'YYYY/MM/DD') AND idopont < (TO_TIMESTAMP(p_datum, 'YYYY/MM/DD') + interval '1' day);
 RETURN hivasok;
END;
