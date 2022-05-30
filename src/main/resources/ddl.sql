create table if not exists pool (
  id long primary key auto_increment,
  name varchar not null,
  build date not null,
  volume double not null
);
create table if not exists free_chlorine (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists combined_chlorine (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists total_chlorine (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists ph (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists calcium_hardness (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists total_alkalinity (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists cyanuric_acid (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists total_bromine (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists temperature (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exits chemical (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_added date not null,
  time_added time not null,
  chemical varchar not null,
  amount double not null,
  unit varchar not null
);
create table if not exists liquid_chlorine (
  id long primary key auto_increment,
  pool_id long references pool(id),
  date_added date not null,
  time_added time not null,
  amount double not null,
  unit varchar not null
);