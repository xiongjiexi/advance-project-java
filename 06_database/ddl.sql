create table t_order
(
    id       int auto_increment
        primary key,
    user_id  int                     not null comment '用户id',
    order_no varchar(128) default '' not null comment '订单编号',
    amount   decimal      default 0  not null comment '总金额'
);

create table t_order_product
(
    id            int auto_increment
        primary key,
    order_id      int                     not null comment '订单id',
    order_no      varchar(128) default '' not null comment '订单编号',
    product_id    int                     not null comment '商品id',
    product_num   int          default 0  not null comment '订单下商品数量',
    product_price decimal      default 0  not null comment '商品单价'
)
    comment '订单商品关系表';

create index t_order_product_order_id_index
    on t_order_product (order_id);

create table t_product
(
    id           int auto_increment
        primary key,
    product_name varchar(512) default '' not null comment '商品名',
    product_no   varchar(128) default '' not null comment '商品编号',
    price        decimal      default 0  not null comment '商品单价'
)
    comment '商品表';

create table t_user
(
    id        int auto_increment
        primary key,
    username  varchar(512) default '' not null comment '用户名',
    passwd    varchar(512) default '' not null comment '密码',
    telephone varchar(128) default '' not null comment '电话'
)
    comment '用户表';

