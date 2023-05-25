CREATE SEQUENCE naplo_sqe INCREMENT BY 1 START WITH 1;

SELECT * FROM (SELECT hivasId FROM HIVAS ORDER BY idopont desc) WHERE ROWNUM <= 1;

CREATE OR REPLACE TRIGGER feltoltnaplo AFTER insert ON HIVAS 
DECLARE
 hivas_rec   hivas%ROWTYPE;
 mentok number;
 hivasid hivas.hivasId%TYPE;
BEGIN
 hivasid := SELECT * FROM (SELECT hivasId FROM HIVAS ORDER BY idopont desc) WHERE ROWNUM <= 1;
 mentok := SELECT COUNT(*) FROM H_A WHERE hivasId = hivasid;
 if mentok == 0 then
  INSERT INTO NAPLO VAUES (naplo_sqe.nextval, SYSDATE + 300, SELECT USER FROM DUAL, TRUE, hivasid);
 else
  INSERT INTO NAPLO VAUES (naplo_sqe.nextval, SYSDATE, SELECT USER FROM DUAL, FALSE, hivasid);
 end if;
END;