DELETE FROM category;

INSERT INTO category (id, name, description, is_active) VALUES (
UNHEX(REPLACE('54f22ca3-866f-46d3-a149-198090353651', "-","")),
'Ação',
'Lançamentos de Ação',
1);

INSERT INTO category (id, name, description, is_active) VALUES (
UNHEX(REPLACE('94f27aa3-866f-46d3-a149-198090353651', "-","")),
'Terror',
'Filmes de Terror',
1);