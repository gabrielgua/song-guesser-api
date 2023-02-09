set foreign_key_checks = 0;

delete from alternativa;
delete from musica;

set foreign_key_checks = 1;

alter table alternativa auto_increment = 1;
alter table musica auto_increment = 1;

insert into musica (id, nome) values
(1, 'Dirtmouth'),
(2, 'Greenpath'),
(3, 'Crossroads'),
(4, 'City of Tears'),
(5, 'Hornet'),
(6, 'Mantis Lords'),
(7, 'Crystal Peak'),
(8, 'Fungal Wastes'),
(9, 'Soul Sanctum'),
(10, 'Resting Grounds'),
(11, 'Enter Hollownest');

insert into alternativa (id, nome, musica_id) values
(1, 'Dirtmouth', 1),
(2, 'Crossroads', 3),
(3, 'Greenpath', 2),
(4, 'Mantis Lords', 6),
(5, 'Hornet', 5),
(6, 'Soul Sanctum', 9),
(7, 'Resting Grounds', 10),
(8, 'Fungal Wastes', 8),
(9, 'Crystal Peak', 7),
(10, 'City of Tears', 4),
(11, 'Enter Hollownest', 11);

insert into usuario (id, email, senha) values (1, 'admin@guesser.com', '$2a$12$WJLg78O1Q6LAOUwyW67Oie8zlSlSEbGvBeSViIvXgpKC5JUTTIJdi');


