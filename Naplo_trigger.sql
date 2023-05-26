CREATE SEQUENCE naplo_sqe INCREMENT BY 1 START WITH 1;

SELECT * FROM (SELECT hivasId FROM HIVAS ORDER BY idopont desc) WHERE ROWNUM <= 1;

CREATE OR REPLACE TRIGGER feltoltnaplo AFTER insert ON HIVAS 
DECLARE
sor hivas%ROWTYPE;
minutes int;
BEGIN
 SELECT * INTO sor FROM HIVAS WHERE hivasId = (SELECT * FROM (SELECT hivasId FROM HIVAS ORDER BY idopont desc) WHERE ROWNUM <= 1);
 minutes := MOD(ABS(DBMS_RANDOM.RANDOM()), 20) + 5;
 IF (sor.mentotkuld >= 1) then
  INSERT INTO NAPLO VALUES (naplo_sqe.nextval, sysdate + (1/1440)*minutes, (SELECT USER FROM DUAL), sor.hivasId);
 END IF;
END;

END;
