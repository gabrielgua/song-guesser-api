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

create table pergunta (
    id bigint not null auto_increment,
    musica_id bigint not null,

    primary key (id)
) engine=InnoDB default charset=UTF8MB4;

create table pergunta_alternativa (
    pergunta_id bigint not null,
    alternativa_id bigint not null,

    primary key (pergunta_id, alternativa_id)
) engine=InnoDB default charset=UTF8MB4;

alter table alternativa add constraint fk_alternativa_musica
foreign key (musica_id) references musica (id);

alter table pergunta add constraint fk_pergunta_musica
foreign key (musica_id) references musica (id);

alter table pergunta_alternativa add constraint fk_pergunta_alternativa_pergunta
foreign key (pergunta_id) references pergunta (id);

alter table pergunta_alternativa add constraint fk_pergunta_alternativa_alternativa
foreign key (alternativa_id) references alternativa (id);
