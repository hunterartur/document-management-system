insert into ORGANIZATION (ID, FULL_NAME, SHORT_NAME, CONTACT_NUMBER) values ('bf1cd7bc-2674-4718-a824-7f87057a6f60', 'RussianZD', 'rzd', '{
  "1" : "+7 987 000 22 55",
  "2" : "+7 987 540 19 19"
}');
insert into DEPARTMENT (ID, FULL_NAME, SHORT_NAME, ORGANIZATION_ID, CONTACT_NUMBER)
values ('1a0f7c3c-3641-45b4-abc8-960f70f368ad', 'Information technologic', 'IT', 'bf1cd7bc-2674-4718-a824-7f87057a6f60', '{
  "1" : "+7 987 477 11 90",
  "2" : "+7 967 540 19 55"
}');
insert into DEPARTMENT (ID, FULL_NAME, SHORT_NAME, ORGANIZATION_ID, CONTACT_NUMBER)
values ('59a1a5d5-c028-48c7-ba21-252384f6435f', 'Human hunter', 'HR', 'bf1cd7bc-2674-4718-a824-7f87057a6f60',  '{
  "1" : "+7 917 725 14 63"
}');

insert into POST (ID, NAME) values ('15f717ee-a312-412f-ad97-40e0e89934db', 'java junior developer');
insert into POST (ID, NAME) values ('6dae6981-4768-4b0d-aaf4-0b5f96a2ecbb', 'java middle developer');
insert into POST (ID, NAME) values ('68c894e1-9878-4a88-ad25-8a517171e88d', 'java senior developer');

insert into PERSON (ID, FIRST_NAME, LAST_NAME, PATRONYMIC, PHOTO, BIRTHDAY, PHONE_NUMBER, POST_ID, DEPARTMENT_ID)
VALUES ('89e7db31-aace-4b32-b570-db5e716ffaec', 'Ishmaev', 'Artur', 'Ilgizovich', 'c:\Photo\IAI.jpeg', '1994-01-04', '+7 987 040 00 72', '15f717ee-a312-412f-ad97-40e0e89934db', '1a0f7c3c-3641-45b4-abc8-960f70f368ad');
insert into PERSON (ID, FIRST_NAME, LAST_NAME, PATRONYMIC, PHOTO, BIRTHDAY, PHONE_NUMBER, POST_ID, DEPARTMENT_ID)
VALUES ('371a9d18-8124-4691-a01f-f1b65e2b7dff', 'Nasibullina', 'Yulduz', 'Ferdinantovna', 'c:\Photo\NYF.jpeg', '1993-11-01', '+7 996 582 96 37', '6dae6981-4768-4b0d-aaf4-0b5f96a2ecbb', '59a1a5d5-c028-48c7-ba21-252384f6435f');
insert into PERSON (ID, FIRST_NAME, LAST_NAME, PATRONYMIC, PHOTO, BIRTHDAY, PHONE_NUMBER, POST_ID, DEPARTMENT_ID)
VALUES ('1a8b1565-4802-4301-8564-2f1fdb3d134c', 'Ivanov', 'Igor', 'Ilgizovich', 'c:\Photo\III.jpeg', '1991-05-12', '+7 967 550 40 32', '68c894e1-9878-4a88-ad25-8a517171e88d', '59a1a5d5-c028-48c7-ba21-252384f6435f');

update ORGANIZATION SET DIRECTOR_ID='371a9d18-8124-4691-a01f-f1b65e2b7dff' where ID='bf1cd7bc-2674-4718-a824-7f87057a6f60';
update DEPARTMENT set DIRECTOR_ID='89e7db31-aace-4b32-b570-db5e716ffaec' where ID='59a1a5d5-c028-48c7-ba21-252384f6435f';
update DEPARTMENT set DIRECTOR_ID='1a8b1565-4802-4301-8564-2f1fdb3d134c' where ID='1a0f7c3c-3641-45b4-abc8-960f70f368ad';