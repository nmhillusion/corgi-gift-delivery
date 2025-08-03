create table if not exists t_cx_sequence (
    seq_name varchar(200) primary key,
    seq_value numeric
);

----

create table if not exists t_cx_delivery_type (
  type_id int primary key,
  type_name nvarchar(100) not null
);

create table if not exists t_cx_delivery_status (
  status_id int primary key,
  status_name nvarchar(100) not null,
  status_order numeric
);

create table if not exists t_cx_delivery_return_status (
  status_id int primary key,
  status_name nvarchar(100) not null
);

---------

create table if not exists t_cx_delivery (
  delivery_id bigint primary key,
  event_id varchar(200),
  delivery_period_year int,
  delivery_period_month int,
  territory nvarchar(200),
  region nvarchar(200),
  organ_id varchar(20),
  received_organ nvarchar(200),
  amd_name nvarchar(200),
  customer_level nvarchar(100),
  customer_id varchar(20),
  customer_name nvarchar(500),
  id_card_number varchar(100),
  phone_number varchar(30),
  address nvarchar(1000),
  gift_name nvarchar(300),
  note text,
  insert_date timestamp with time zone default CURRENT_TIMESTAMP,
  update_date timestamp with time zone
);

alter table t_cx_delivery
add constraint if not exists uniq_cx_delivery__event_id__customer_id
unique (event_id, customer_id);

create table if not exists t_cx_delivery_attempt (
  attempt_id bigint primary key,
  delivery_id bigint,
  delivery_type_id int,
  delivery_status_id int,
  note text,
  insert_date timestamp with time zone default CURRENT_TIMESTAMP,
  update_date timestamp with time zone
);

alter table t_cx_delivery_attempt
add constraint if not exists fk_cx_delivery_attempt__delivery_id
foreign key (delivery_id) references t_cx_delivery(delivery_id);

alter table t_cx_delivery_attempt
add constraint if not exists fk_cx_delivery_attempt__delivery_type_id
foreign key (delivery_type_id) references t_cx_delivery_type(type_id);

alter table t_cx_delivery_attempt
add constraint if not exists fk_cx_delivery_attempt__delivery_status_id
foreign key (delivery_status_id) references t_cx_delivery_status(status_id);


create table if not exists t_cx_delivery_return (
  return_id bigint primary key,
  delivery_id bigint,
  return_status_id int,
  note text,
  insert_date timestamp with time zone default CURRENT_TIMESTAMP,
  update_date timestamp with time zone
);

alter table t_cx_delivery_return
add constraint if not exists fk_cx_delivery_return__delivery_id
foreign key (delivery_id) references t_cx_delivery(delivery_id);

alter table t_cx_delivery_return
add constraint if not exists fk_cx_delivery_return__return_status_id
foreign key (return_status_id) references t_cx_delivery_return_status(status_id);



