create table alternativa (
    id bigint not null auto_increment,
    nome varchar(60) not null,
    musica_id bigint not null,

    primary key (id)
) engine=InnoDB default charset=UTF8MB4;

create table musica (
    id bigint not null auto_increment,
    nome varchar(60) not null,
    diretorio varchar(60),

    primary key (id)
) engine=InnoDB default charset=UTF8MB4;

alter table alternativa add constraint fk_alternativa_musica
foreign key (musica_id) references musica (id);



