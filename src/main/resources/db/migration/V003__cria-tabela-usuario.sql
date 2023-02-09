create table usuario (
    id bigint not null,
    email varchar(150) not null,
    senha varchar(80) not null,

    primary key (id)
) engine=InnoDB default charset=UTF8;