set foreign_key_checks = 0;

delete from alternativa;
delete from musica;

set foreign_key_checks = 1;

alter table alternativa auto_increment = 1;
alter table musica auto_increment = 1;

insert into musica (id, nome, diretorio) values
(1, 'Dirtmouth', 'http://localhost:8080/musicas/1/arquivo'),
(2, 'Greenpath', 'http://localhost:8080/musicas/2/arquivo'),
(3, 'Crossroads', 'http://localhost:8080/musicas/3/arquivo'),
(4, 'City of Tears', 'http://localhost:8080/musicas/4/arquivo'),
(5, 'Hornet', 'http://localhost:8080/musicas/5/arquivo'),
(6, 'Mantis Lords', 'http://localhost:8080/musicas/6/arquivo'),
(7, 'Crystal Peak', 'http://localhost:8080/musicas/7/arquivo'),
(8, 'Fungal Wastes', 'http://localhost:8080/musicas/8/arquivo'),
(9, 'Soul Sanctum', 'http://localhost:8080/musicas/9/arquivo'),
(10, 'Resting Grounds', 'http://localhost:8080/musicas/10/arquivo'),
(11, 'Enter Hollownest', 'http://localhost:8080/musicas/11/arquivo');

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


