create table employee ( employeid integer primary key, name varchar(120 ));

create table employee_log ( name varchar(120 ), changes integer);

create procedure track_emp_updates
(ename varchar(30)) as
begin
update employee_log set changes = changes + 1
where name = :ename;
end

create rule emp_updates after delete, insert,
update of employee
execute procedure track_emp_updates
(name = new.name)
