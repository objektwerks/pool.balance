create table if not exists pool (
  id long primary key auto_increment,
  name varchar not null,
  volume double not null,
  unit varchar not null
);
create index if not exists pool_name_idx ON pool(name);
create table if not exists cleaning (
  id long primary key auto_increment,
  pool_id long references pool(id),
  brush bool not null,
  net bool not null,
  skimmer_basket bool not null,
  pump_basket bool not null,
  pump_filter bool not null,
  vacuum bool not null,
  cleaned timestamp not null
);
create index if not exists cleaning_cleaned_idx ON cleaning(cleaned);
create table if not exists measurement (
  id long primary key auto_increment,
  pool_id long references pool(id),
  total_chlorine int not null,
  free_chlorine int not null,
  combined_chlorine double not null,
  ph double not null,
  calcium_hardness int not null,
  total_alkalinity int not null,
  cyanuric_acid int not null,
  total_bromine int not null,
  salt int not null,
  temperature int not null,
  measured timestamp not null
);
create index if not exists measurement_measured_idx ON measurement(measured);
create table if not exists chemical (
  id long primary key auto_increment,
  pool_id long references pool(id),
  typeof varchar not null,
  amount double not null,
  unit varchar not null,
  added timestamp not null
);
create index if not exists chemical_added_idx ON chemical(added);