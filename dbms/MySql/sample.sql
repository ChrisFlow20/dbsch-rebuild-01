CREATE PROCEDURE AllCars() SELECT * FROM Payment;

DROP FUNCTION IF EXISTS CircleArea;

CREATE FUNCTION CircleArea(r DOUBLE) RETURNS DOUBLE
BEGIN
DECLARE area DOUBLE;
SET area = r * r * pi();
RETURN area;
END

SELECT CircleArea(5.5)

create table employee2 ( salary integer, perks integer )

CREATE TRIGGER ins_trig BEFORE INSERT ON Employee2
FOR EACH ROW
BEGIN
UPDATE Employee SET Salary=Salary-300 WHERE Perks>500;
END;;