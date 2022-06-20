# Авторы:
1. Смирнов Антон
2. Кузнецов Виктор
# Для запуска приложения совместно с postgres:
1. Добавить в dockerfile ENTRYPOINT команду "--spring.profiles.active=postgres"
2. Добавить подключение к БД, креды брать в docker-compose-postgres.yml файле
3. В терминале выполнить docker-compose -f ./docker-compose-postgres.yml up
4. Проверить, что в схеме БД отображаются таблицы

# Для запуска автотестов с использованием rest-assured:
1. Добавляем зависимость restdocs-api-spec-restassured в pom-файл
2. Для валидации ответа добавляем json-schema-validator в pom-файл
