create table pool (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  volume double not null
);
create table free_chlorine (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  value double not null
);
create table combined_chlorine (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  value double not null
);
create table total_chlorine (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  value double not null
);
create table ph (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  value double not null
);
create table calcium_hardness (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  value double not null
);
create table total_alkalinity (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  value double not null
);
create table cyanuric_acid (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  value double not null
);
create table total_bromine (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  value double not null
);
create table temperature (
  id int primary key auto_increment,
  date_created date not null,
  time_created time not null,
  value double not null
);