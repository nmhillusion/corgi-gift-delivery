
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

insert into t_cx_delivery_status ( status_id , status_name, status_order)
values (1, 'Tạo giao hàng. Chưa tiếp nhận.', 1);

insert into t_cx_delivery_status ( status_id , status_name, status_order)
values (2, 'Đang giao hàng', 2);

insert into t_cx_delivery_status ( status_id , status_name, status_order)
values (3, 'Giao hàng thành công', 3);

insert into t_cx_delivery_status ( status_id , status_name, status_order)
values (4, 'Giao hàng thất bại', 4);

---------

insert into t_cx_shipper_type (type_id, type_name, delivery_type_id)
values (1, 'Agent', 1);

insert into t_cx_shipper_type (type_id, type_name, delivery_type_id)
values (2, 'CA', 2);

insert into t_cx_shipper_type (type_id, type_name, delivery_type_id)
values (3, 'Bưu điện', 3);

---------

insert into t_cx_recipient_type (type_id, type_name)
values (1, 'Khách hàng');

insert into t_cx_recipient_type (type_id, type_name)
values (2, 'Đại lý');

