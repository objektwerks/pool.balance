create table if not exists pool (
  id long primary key auto_increment,
  name varchar not null,
  built date not null,
  volume double not null
);
create table if not exists measurement (
  id long primary key auto_increment,
  pool_id long references pool(id),
  typeof varchar not null,
  date_measured date not null,
  time_measured time not null,
  measurement double not null
);
create table if not exists chemical (
  id long primary key auto_increment,
  pool_id long references pool(id),
  typeof varchar not null,
  date_added date not null,
  time_added time not null,
  amount double not null,
  unit varchar not null
);