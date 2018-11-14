drop table if exists userprofile;

create table  userprofile (
  id integer primary key,
  firstName varchar(50),
  lastName varchar(50),
  email    varchar(50),
  phone varchar(10),
  addr varchar(50),
  state varchar(50),
  username varchar(50)
);

INSERT INTO userprofile (id, firstName, lastName,email,phone,addr,state,username) VALUES (223455,'Chris L', 'username1','username1@gmail.com','6364858533','4 BLACK DEER ST, STONE','TX','username1');
INSERT INTO userprofile (id, firstName, lastName,email,phone,addr,state,username) VALUES (123456,'Simon C', 'username2','username2@gmail.com','7264947276','43 SLIVER EAGLE ST, RIVER','MA','username2');
INSERT INTO userprofile (id, firstName, lastName,email,phone,addr,state,username) VALUES (234567,'Amber K', 'username3','username3@gmail.com','4274558382','67 RED LION ST ROCK','NY','username3');
INSERT INTO userprofile (id, firstName, lastName,email,phone,addr,state,username) VALUES (345678,'Kelly J', 'username4','username4@gmail.com','3530880835','8 GREEN SHARK ST, MOUNTAIN','CA','username4');





	