CREATE OR REPLACE TRIGGER feltolt_h_a AFTER insert ON HIVAS 
DECLARE
sor hivas%ROWTYPE;
autoszam int;
BEGIN
 SELECT * INTO sor FROM HIVAS WHERE hivasId = (SELECT * FROM (SELECT hivasId FROM HIVAS ORDER BY idopont desc) WHERE ROWNUM <= 1);
 autoszam := MOD(ABS(DBMS_RANDOM.RANDOM()), 3) + 1;
 IF sor.mentotkuld >= 1 then
  INSERT INTO H_A VALUES (sor.hivasId, autoszam);
 END IF;
END;
