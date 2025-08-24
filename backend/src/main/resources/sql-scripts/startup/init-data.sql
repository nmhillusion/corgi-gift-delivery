
insert into t_cx_delivery_type (type_id, type_name, type_desc) values (
  1, 'AGENT_GIAO_HANG', 'Agent trao hàng'
);

insert into t_cx_delivery_type (type_id, type_name, type_desc) values (
  2, 'CA_TRAO_HANG', 'CA trao hàng'
);

insert into t_cx_delivery_type (type_id, type_name, type_desc) values (
  3, 'GUI_BUU_DIEN', 'Gửi hàng bưu điện'
);

---------

insert into t_cx_delivery_status ( status_id , status_name, status_desc, status_order)
values (1, 'TAO_GIAO_HANG', 'Tạo giao hàng. Chưa tiếp nhận.', 1);

insert into t_cx_delivery_status ( status_id , status_name, status_desc, status_order)
values (2, 'DANG_GIAO_HANG', 'Đang giao hàng', 2);

insert into t_cx_delivery_status ( status_id , status_name, status_desc, status_order)
values (3, 'GIAO_HANG_THANH_CONG', 'Giao hàng thành công', 3);

insert into t_cx_delivery_status ( status_id , status_name, status_desc, status_order)
values (4, 'GIAO_HANG_THAT_BAI', 'Giao hàng thất bại', 4);

---------

insert into t_cx_delivery_return_status (status_id, status_name, status_desc)
values (1, 'YEU_CAU_TRA_HANG', 'Đang yêu cầu trả hàng');

insert into t_cx_delivery_return_status (status_id, status_name, status_desc)
values (2, 'TIEP_NHAN_TRA_HANG', 'Tiếp nhận trả hàng');

insert into t_cx_delivery_return_status (status_id, status_name, status_desc)
values (3, 'DA_TRA_HANG', 'Đã trả hàng');
