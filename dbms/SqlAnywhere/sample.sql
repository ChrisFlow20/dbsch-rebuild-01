create table t1(
key_1 integer not null primary key,
non_key_1 varchar(100) not null) ;


create trigger triud_t1 before insert, update on t1
for each row
begin
message string('Trigger fired.') to console;
end;

insert t1 values ( 1, 'first row ');
insert t1 values ( 2, 'second row ');


CREATE FUNCTION ODBCinsert(
  IN ProductName CHAR(30),
  IN ProductDescription CHAR(50)
)
RETURNS INT
EXTERNAL NAME 'ODBCexternalInsert@extodbc.dll'
LANGUAGE C_ODBC32;