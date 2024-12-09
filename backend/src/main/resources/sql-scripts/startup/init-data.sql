
insert into t_cx_delivery_type (type_id, type_name) values (
  1, 'Agent trao hàng'
);

insert into t_cx_delivery_type (type_id, type_name) values (
  2, 'CA trao hàng'
);

insert into t_cx_delivery_type (type_id, type_name) values (
  3, 'Gửi hàng bưu điện'
);

---------

insert into t_cx_delivery_status ( status_id , status_name )
values (1, 'Tiếp nhận giao hàng');

insert into t_cx_delivery_status ( status_id , status_name )
values (2, 'Giao hàng thành công');

insert into t_cx_delivery_status ( status_id , status_name )
values (3, 'Giao hàng thất bại');

---------

insert into t_cx_shipper_type (type_id, type_name)
values (1, 'Agent');

insert into t_cx_shipper_type (type_id, type_name)
values (2, 'CA');

insert into t_cx_shipper_type (type_id, type_name)
values (3, 'Bưu điện');

---------

insert into t_cx_customer_type (type_id, type_name)
values (1, 'Khách hàng');

insert into t_cx_customer_type (type_id, type_name)
values (2, 'Đại lý');

