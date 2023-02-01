create table arquivo_musica (
    musica_id bigint not null,
    nome_arquivo varchar(150) not null,
    content_type varchar(80) not null,
    tamanho int not null,
    diretorio varchar(150),

    primary key (musica_id),
    constraint fk_arquivo_musica_musica foreign key (musica_id) references musica (id)
) engine=InnoDB default charset=UTF8;