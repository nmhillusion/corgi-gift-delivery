
create table t_cx_customer (
  customer_id int primary key,
  full_name nvarchar(200),
  id_card_number nvarchar(30)
);

create table t_cx_commodity_type (
  type_id int primary key,
  type_name nvarchar(100)
);

create table t_cx_commodity (
  com_id int primary key,
  com_name nvarchar(200),
  com_type int
);

create table t_cx_transaction(
  trans_id int primary key
);
