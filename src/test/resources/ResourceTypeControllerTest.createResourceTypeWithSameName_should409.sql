DELETE FROM resource_type WHERE id > 0;

INSERT INTO resource_type (id, name, quota)
VALUES (1, 'tiger', 100);