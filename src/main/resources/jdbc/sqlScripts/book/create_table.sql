create database sharding0;

create database sharding1;

create table sharding0.book_0
(
  id     bigint unsigned primary key comment '主键',
  name   varchar(100) comment '名称',
  isbn   varchar(64) unique comment 'ISBN',
  price  double comment '价格',
  author varchar(50) comment '作者',
  category varchar(50) comment '分类'
)charset 'UTF8';

create table sharding0.book_1
(
  id     bigint unsigned primary key comment '主键',
  name   varchar(100) comment '名称',
  isbn   varchar(64) unique comment 'ISBN',
  price  double comment '价格',
  author varchar(50) comment '作者',
  category varchar(50) comment '分类'
)charset 'UTF8';

create table sharding0.book_2
(
  id     bigint unsigned primary key comment '主键',
  name   varchar(100) comment '名称',
  isbn   varchar(64) unique comment 'ISBN',
  price  double comment '价格',
  author varchar(50) comment '作者',
  category varchar(50) comment '分类'
)charset 'UTF8';

create table sharding1.book_0
(
  id     bigint unsigned primary key comment '主键',
  name   varchar(100) comment '名称',
  isbn   varchar(64) unique comment 'ISBN',
  price  double comment '价格',
  author varchar(50) comment '作者',
  category varchar(50) comment '分类'
)charset 'UTF8';

create table sharding1.book_1
(
  id     bigint unsigned primary key comment '主键',
  name   varchar(100) comment '名称',
  isbn   varchar(64) unique comment 'ISBN',
  price  double comment '价格',
  author varchar(50) comment '作者',
  category varchar(50) comment '分类'
)charset 'UTF8';

create table sharding1.book_2
(
  id     bigint unsigned primary key comment '主键',
  name   varchar(100) comment '名称',
  isbn   varchar(64) unique comment 'ISBN',
  price  double comment '价格',
  author varchar(50) comment '作者',
  category varchar(50) comment '分类'
)charset 'UTF8';