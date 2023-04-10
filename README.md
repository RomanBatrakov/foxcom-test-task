# Foxcom test task
Тестовое задание на позицию java разработчика: "Автоматическое распределение заявок на добычу охотничьих ресурсов"

## Технический стек:
- Java 17;
- Spring MVC, Mapstruct;
- Spring Data, PostgreSQL, Hibernate;
- Lombok, Junit;
- Docker, Postman.

## Описание проекта:
RESTful-сервис (CRUD) для управления данными заявок на добычу охотничьих ресурсов. 
Состав данных заявки:
- ФИО
- Тип заявки (массовые виды или жеребьевочные)
- Дата выдачи охотбилета
- Серия охотбилета
- Номер охотбилета
- Дата подачи заявки
- Запрашиваемые ресурсы:
	- Район ресурса
	- Название ресурса
	- Количество ресурса
	- Статус рассмотрения ресурса
- Статус рассмотрения заявки

По каждому ресурсу предусмотрена квота, ограничивающая общее количество запрашиваемых ресурсов и сроки подачи заявок в виде даты начала и даты окончания. 

>__Основной функционал:__
> - Создание и управление заявками;
> - Автоматическая генерация тестового массива заявок, как уникальных, так и с повторами данных;
> - Автоматическая проверка заявок (одобрение/отклонение) по условиям:
> 
>       1) Возможность старта/остановки проверки заявок.
>       2) Заявки проверяются поочередно, в зависимости от срока поступления.
>       3) Ресурсы в заявке одобряются, если уникальным заявителем запрошено количество ресурсов, не превышающих общий размер квоты на ресурс.
>       4) Заявитель может получить одобрение разных ресурсов в разных заявках.
>          При одобрении ресурса в рамках одного района, этот же ресурс в рамках других районов не одобряется.
>       5) Ресурс отклоняется, если дата подачи заявки не вписывается в рамки сроков подачи на этот ресурс. 

- Сервер запущен на порту 8084.
- База данных запущена на порту 5431.

## Рекомендации по запуску:
В командной строке, находясь в директории проекта, ввести команды:

`mvn package`  
`docker-compose up`

## Коллекция запрососв для Postman:
- [Postman](foxcomTestTask.postman_collection.json)
