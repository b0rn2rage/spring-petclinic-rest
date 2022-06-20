# Для запуска приложения совместно с postgres:
1. Добавить в dockerfile ENTRYPOINT команду "--spring.profiles.active=postgres"
2. Добавить подключение к БД, креды брать в docker-compose-postgres.yml файле
3. В терминале выполнить docker-compose -f ./docker-compose-postgres.yml up
4. Проверить, что в схеме БД отображаются таблицы
