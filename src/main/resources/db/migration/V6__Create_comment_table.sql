create table comment
(
	id bigint auto_increment,
	parent_id bigint not null comment '父类id',
	type int not null comment '回复类型（层级）',
	commentator int not null comment '评论员id',
	gmt_create bigint not null comment '创建时间',
	gmt_modified bigint not null comment '最新修改时间',
	like_count int not null comment '点赞数',
	constraint comment_pk
		primary key (id)
);
