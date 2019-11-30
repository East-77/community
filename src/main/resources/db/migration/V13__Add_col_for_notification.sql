alter table notification
	add notifier_name varchar(50) not null after notifier;

alter table notification
	add outer_title varchar(256) not null after outerId;