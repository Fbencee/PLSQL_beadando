create or replace procedure beteg_feltolt as
  F UTL_FILE.FILE_TYPE;
  sor varchar2(200);
  c1 varchar(30);
  c2 varchar(30);
  c3 varchar(30);
  c4 varchar(30);
begin
  F := UTL_FILE.FOPEN('DOP','betegek.txt','R');
    loop
       UTL_FILE.GET_LINE(F,sor,100);

       c1 := SUBSTR(sor, 1, INSTR(sor, ',') - 1);
       sor := SUBSTR(sor, INSTR(sor, ',') + 1);

       c2 := SUBSTR(sor, 1, INSTR(sor, ',') - 1);
       sor := SUBSTR(sor, INSTR(sor, ',') + 1);

       c3 := SUBSTR(sor, 1, INSTR(sor, ',') - 1);
       c4 := SUBSTR(sor, INSTR(sor, ',') + 1);

       INSERT INTO beteg VALUES(beteg_sqe.nextval,c1,c2,c3,c4);
    end loop;
    UTL_FILE.FCLOSE(F);
   exception
      when others then 
         DBMS_OUTPUT.put_line('Nem sikerült a feltöltés!');
  end;
