create table if not exists t_cx_sequence (
    seq_name varchar(200) primary key,
    seq_value numeric
);

----

create table if not exists t_cx_delivery_type (
  type_id int primary key,
  type_name nvarchar(100)
);

create table if not exists t_cx_delivery_status (
  status_id int primary key,
  status_name nvarchar(100),
  status_order numeric
);
