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

create table if not exists t_cx_delivery (
  delivery_id number primary key,
  event_id varchar(200),
  delivery_period_year number,
  delivery_period_month number,
  territory nvarchar(200),
  region nvarchar(200),
  organ_id varchar(20),
  received_organ nvarchar(200),
  amd_name nvarchar(200),
  customer_level nvarchar(100),
  customer_id number,
  customer_name nvarchar(500),
  id_card_number varchar(100),
  phone_number varchar(30),
  address nvarchar(1000),
  gift_name nvarchar(300)
);

create table if not exists t_cx_deliver_attempt (
  attempt_id number primary key,
  delivery_id number,
  delivery_type_id number,
  delivery_status_id number,
  note text
);

create table if not exists t_cx_delivery_return (
  return_id number primary key,
  delivery_id number,
  attempt_id number,
  return_status_id number,
  note text
);




