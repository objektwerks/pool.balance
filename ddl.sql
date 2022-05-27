drop table pool if exists;
create table pool (
  id int primary key auto_increment,
  volume double not null
);