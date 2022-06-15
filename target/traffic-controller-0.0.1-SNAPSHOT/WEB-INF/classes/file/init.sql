create table users (
    id bigint(20) not null comment '主键',
    name varchar(260) not null comment '名字',
    age int(2) not null comment '年龄',
    primary key (id) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;