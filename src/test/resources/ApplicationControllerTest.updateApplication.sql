DELETE FROM resource_type WHERE id > 0;
DELETE FROM area WHERE id > 0;
DELETE FROM application WHERE id > 0;
DELETE FROM resource WHERE id > 0;

INSERT INTO resource_type (id, name, quota, start_date, end_date)
VALUES (1, 'tiger', 10, CURRENT_DATE, (CURRENT_DATE + INTERVAL '3 months'));

INSERT INTO area (id, name)
VALUES (1, 'Moscow');

INSERT INTO application (id, application_date, category, full_name, issue_date, status, ticket_number, ticket_series)
VALUES (1, CURRENT_DATE, 'LOTTERY', 'Test test', (CURRENT_DATE - INTERVAL '2 years'), 'APPROVED', 10000, 'AAA');

INSERT INTO resource (id, amount, status, application_id, area_id, resource_type_id)
VALUES (1, 1, 'APPROVED', 1, 1, 1);