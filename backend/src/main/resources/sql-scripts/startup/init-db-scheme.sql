create table if not exists t_cx_sequence (
    seq_name varchar(200) primary key,
    seq_value numeric
);

create table if not exists t_cx_recipient_type (
    type_id int primary key,
    type_name nvarchar(100)
);

create table if not exists t_cx_recipient (
    recipient_id int primary key,
    full_name nvarchar(200),
    id_card_number nvarchar(30),
    recipient_type_id int
);

alter table t_cx_recipient
add constraint if not exists fk_cx_recipient__recipient_type_id
foreign key (recipient_type_id)
REFERENCES t_cx_recipient_type(type_id);

create table if not exists t_cx_commodity_type (
  type_id int primary key,
  type_name nvarchar(100)
);

----

create table if not exists t_cx_warehouse (
  warehouse_id int primary key,
  warehouse_name nvarchar(200),
  warehouse_address nvarchar(500)
);

create table if not exists t_cx_commodity_import (
  import_id int primary key,
  import_name nvarchar(200),
  warehouse_id int not null,
  import_time timestamp with time zone
);
alter table t_cx_commodity_import
add constraint if not exists fk_cx_commodity_import__warehouse_id
foreign key (warehouse_id)
references t_cx_warehouse (warehouse_id);

------

create table if not exists t_cx_commodity (
  com_id numeric primary key,
  com_name nvarchar(200),
  com_type_id int,
  create_time timestamp with time zone
);

alter table t_cx_commodity
add constraint if not exists fk_cx_commodity__com_type_id
foreign key (com_type_id)
REFERENCES t_cx_commodity_type(type_id);

----

create table if not exists t_cx_warehouse_item (
  item_id varchar(100) primary key,
  import_id int,
  warehouse_id int,
  com_id int,
  quantity numeric,
  create_time timestamp with time zone
);

alter table t_cx_warehouse_item
add constraint if not exists fk_cx_warehouse_item__import_id
foreign key (import_id)
references t_cx_commodity_import (import_id);

alter table t_cx_warehouse_item
add constraint if not exists fk_cx_warehouse_item__com_id
foreign key (com_id)
references t_cx_commodity (com_id);

alter table t_cx_warehouse_item
add constraint if not exists fk_cx_warehouse_item__warehouse_id
foreign key (warehouse_id)
references t_cx_warehouse (warehouse_id);

----

create table if not exists t_cx_delivery_type (
  type_id int primary key,
  type_name nvarchar(100)
);

create table if not exists t_cx_delivery_status (
  status_id int primary key,
  status_name nvarchar(100)
);


create table if not exists t_cx_delivery (
  delivery_id varchar(200) primary key,
  recipient_id int,
  commodity_id int,
  com_quantity NUMERIC,
  start_time TIMESTAMP with time zone,
  end_time TIMESTAMP with time zone,
  current_attempt_id varchar(100),
  delivery_status_id int
);

----

create table if not exists t_cx_shipper_type (
  type_id int primary key,
  type_name nvarchar(100)
);

create table if not exists t_cx_shipper (
  shipper_id int primary key,
  shipper_type_id int,
  shipper_code varchar(100),
  shipper_name nvarchar(100)
);

alter table t_cx_shipper
add constraint if not exists fk_cx_shipper
foreign key (shipper_type_id)
references t_cx_shipper_type (type_id);

----
create table if not exists t_cx_delivery_attempt (
  attempt_id varchar(100) primary key,
  delivery_id int,
  delivery_type_id int,
  shipper_id varchar(100),
  start_time timestamp with time zone,
  end_time timestamp with time zone,
  delivery_status_id int
);

alter table t_cx_delivery_attempt
add constraint if not exists fk_cx_delivery_attempt__delivery_type_id
foreign key (delivery_type_id)
REFERENCES t_cx_delivery_type (type_id);

alter table t_cx_delivery_attempt
add constraint if not exists fk_cx_delivery_attempt__delivery_status_id
foreign key (delivery_status_id)
REFERENCES t_cx_delivery_status (status_id);

alter table t_cx_delivery_attempt
add constraint if not exists fk_cx_delivery_attempt__delivery_id
foreign key (delivery_id)
REFERENCES t_cx_delivery (delivery_id);

alter table t_cx_delivery_attempt
add constraint if not exists fk_cx_delivery_attempt__shipper_id
foreign key (shipper_id)
references t_cx_shipper (shipper_id);

----

alter table t_cx_delivery
add constraint if not exists fk_cx_delivery__commodity_id
foreign key (commodity_id)
REFERENCES t_cx_commodity (com_id);

alter table t_cx_delivery
add constraint if not exists fk_cx_delivery__current_attempt_id
foreign key (current_attempt_id)
REFERENCES t_cx_delivery_attempt (attempt_id);

alter table t_cx_delivery
add constraint if not exists fk_cx_delivery__delivery_status_id
foreign key (delivery_status_id)
REFERENCES t_cx_delivery_status (status_id);

alter table t_cx_delivery
add constraint if not exists fk_cx_delivery__recipient_id
foreign key (recipient_id)
REFERENCES t_cx_recipient (recipient_id);

----

create table if not exists t_cx_note (
  note_id int primary key,
  note_content text,
  note_time timestamp with time zone,
  recipient_id int,
  delivery_id varchar(100),
  delivery_attempt_id varchar(100),
  import_id int,
  warehouse_item_id varchar(100)
);

alter table t_cx_note
add constraint if not exists fk_cx_note__recipient_id
foreign key (recipient_id)
references t_cx_recipient (recipient_id)
;

alter table t_cx_note
add constraint if not exists fk_cx_note__delivery_id
foreign key (delivery_id)
references t_cx_delivery (delivery_id)
;

alter table t_cx_note
add constraint if not exists fk_cx_note__delivery_attempt_id
foreign key (delivery_attempt_id)
references t_cx_delivery_attempt (attempt_id)
;

alter table t_cx_note
add constraint if not exists fk_cx_note__import_id
foreign key (import_id)
references t_cx_commodity_import (import_id)
;

alter table t_cx_note
add constraint if not exists fk_cx_note__warehouse_item_id
foreign key (warehouse_item_id)
references t_cx_warehouse_item (item_id)
;

---- 2024-12-21

create table if not exists t_cx_commodity_export (
  export_id int primary key,
  export_name nvarchar(200),
  warehouse_id int not null,
  export_time timestamp with time zone
);

alter table t_cx_commodity_export
add constraint if not exists fk_cx_commodity_export__warehouse_id
foreign key (warehouse_id)
references t_cx_warehouse (warehouse_id);

create table if not exists t_cx_warehouse_export_item (
  item_id varchar(100) primary key,
  export_id int,
  warehouse_id int,
  com_id int,
  quantity numeric,
  create_time timestamp with time zone
);

alter table t_cx_warehouse_export_item
add constraint if not exists fk_cx_warehouse_export_item__export_id
foreign key (export_id)
references t_cx_commodity_export (export_id);

alter table t_cx_warehouse_export_item
add constraint if not exists fk_cx_warehouse_export_item__com_id
foreign key (com_id)
references t_cx_commodity (com_id);

alter table t_cx_warehouse_export_item
add constraint if not exists fk_cx_warehouse_export_item__warehouse_id
foreign key (warehouse_id)
references t_cx_warehouse (warehouse_id);

----

create table if not exists t_cx_delivery_package (
  package_id int primary key,
  delivery_id varchar(200),
  package_name nvarchar(100),
  package_time timestamp with time zone
);

alter table t_cx_delivery_package
add constraint if not exists fk_cx_delivery_package__delivery_id
foreign key (delivery_id)
references t_cx_delivery (delivery_id);


create table if not exists t_cx_delivery_package_item (
  item_id varchar(100) primary key,
  package_id int,
  warehouse_id int,
  export_id int,
  com_id int,
  quantity numeric,
  create_time timestamp with time zone
);

alter table t_cx_delivery_package_item
add constraint if not exists fk_cx_delivery_package_item__package_id
foreign key (package_id)
references t_cx_delivery_package (package_id);

alter table t_cx_delivery_package_item
add constraint if not exists fk_cx_delivery_package_item__com_id
foreign key (com_id)
references t_cx_commodity (com_id);

alter table t_cx_delivery_package_item
add constraint if not exists fk_cx_delivery_package_item__warehouse_id
foreign key (warehouse_id)
references t_cx_warehouse (warehouse_id);

alter table t_cx_delivery_package_item
add constraint if not exists fk_cx_delivery_package_item__export_id
foreign key (export_id)
references t_cx_commodity_export (export_id);