DELETE FROM resource_type WHERE id > 0;
DELETE FROM area WHERE id > 0;
DELETE FROM application WHERE id > 0;
DELETE FROM resource WHERE id > 0;

INSERT INTO resource_type (id, name, quota, start_date, end_date)
VALUES (1, 'tiger', 10, CURRENT_TIMESTAMP(), DATEADD('MONTH', 3, CURRENT_TIMESTAMP()));

INSERT INTO area (id, name)
VALUES (1, 'Moscow');

INSERT INTO application (id, application_date, category, full_name, issue_date, status, ticket_number, ticket_series)
VALUES (1, CURRENT_TIMESTAMP, 'LOTTERY', 'Test test', DATEADD('YEAR', -2, CURRENT_TIMESTAMP()), 'IN_PROGRESS', 10000, 'AAA');

INSERT INTO resource (id, amount, status, application_id, area_id, resource_type_id)
VALUES (1, 1, 'IN_PROGRESS', 1, 1, 1);