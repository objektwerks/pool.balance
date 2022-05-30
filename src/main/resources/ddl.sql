create table if not exists pool (
  id int primary key auto_increment not null,
  name varchar not null,
  build date not null,
  volume double not null
);
create table if not exists free_chlorine (
  id int primary key auto_increment not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists combined_chlorine (
  id int primary key auto_increment not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists total_chlorine (
  id int primary key auto_increment not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists ph (
  id int primary key auto_increment not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists calcium_hardness (
  id int primary key auto_increment not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists total_alkalinity (
  id int primary key auto_increment not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists cyanuric_acid (
  id int primary key auto_increment not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists total_bromine (
  id int primary key auto_increment not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists temperature (
  id int primary key auto_increment not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);