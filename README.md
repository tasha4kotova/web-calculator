# Веб-калькулятор

Веб-приложение для нахождения максимального значения среди множества чисел и управления списком студентов.

## Технологии

- Java 24
- Maven 3.9.8
- Tomcat 11
- Jakarta EE 10
- HTML5
- CSS3
- JavaScript (ES6+)
- Gson 2.10.1
- Jakarta JSON API 2.1.1
- Yasson 3.0.3

## Функциональность

### Калькулятор
- Поиск максимального значения среди множества чисел
- Поддержка различных форматов ввода:
  - Строка с разделителем (например: "11, 32, 1, 22, 14")
  - Массив чисел
  - Список чисел
- Валидация входных данных
- Асинхронные запросы к серверу
- Современный пользовательский интерфейс

### Управление студентами
- CRUD операции для работы со студентами:
  - Создание нового студента
  - Просмотр списка студентов
  - Редактирование данных студента
  - Удаление студента
- Валидация данных студента:
  - ФИО (не пустое)
  - Год рождения (1900-2024)
- Модальное окно для добавления/редактирования
- Асинхронные запросы к API
- Интерактивная таблица с действиями

## Структура проекта

```
web-calculator/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           ├── model/
│       │           │   └── Student.java
│       │           └── servlet/
│       │               ├── CalculatorServlet.java
│       │               └── StudentServlet.java
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml
│           ├── css/
│           │   └── styles.css
│           ├── js/
│           │   ├── calculator.js
│           │   └── students.js
│           ├── index.html
│           ├── calculator.html
│           └── students.html
├── pom.xml
└── README.md
```

## Системные требования

- JDK 24
- Maven 3.9.8 или выше
- Apache Tomcat 11
- Современный веб-браузер с поддержкой ES6+

## Установка и запуск

1  Клонируйте репозиторий:
```bash
git clone [URL репозитория]
cd web-calculator
```

2  Соберите проект с помощью Maven:
```bash
mvn clean install
```

3 Скопируйте файл `target/web-calculator.war` в директорию `webapps` вашего Tomcat

4  Запустите Tomcat:
```bash
# Windows
%CATALINA_HOME%\bin\startup.bat

# Linux/Mac
$CATALINA_HOME/bin/startup.sh
```

5  Откройте приложение в браузере:
```
http://localhost:8080/web-calculator/
```

## Разработчик

- **ФИО:** Шаполова Татьяна Леонидовна
- **Группа:** 3ПР
- **Номер индивидуального задания:** 1

## Лицензия

MIT License

Copyright (c) 2024 Шаполова Татьяна Леонидовна

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE. 