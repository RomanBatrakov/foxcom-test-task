DELETE FROM resource_type WHERE id > 0;
DELETE FROM area WHERE id > 0;

INSERT INTO resource_type (id, name, quota, start_date, end_date)
VALUES (1, 'tiger', 10, CURRENT_DATE, (CURRENT_DATE + INTERVAL '3 months'));

INSERT INTO area (id, name)
VALUES (1, 'Moscow');