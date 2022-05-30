create table pool (
  id long primary key auto_increment,
  name varchar not null,
  built date not null,
  volume double not null
);
create table free_chlorine (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table combined_chlorine (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table total_chlorine (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table ph (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table calcium_hardness (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table total_alkalinity (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table cyanuric_acid (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table total_bromine (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table temperature (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);