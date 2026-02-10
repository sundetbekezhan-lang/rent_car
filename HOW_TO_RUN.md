# Инструкция по запуску проекта

## Требования
- Java JDK 8 или выше
- PostgreSQL установлен и запущен
- IntelliJ IDEA (или другая IDE)

## Шаги для запуска:

### 1. Настройка базы данных PostgreSQL

1. Откройте PostgreSQL (pgAdmin или psql)
2. Создайте новую базу данных:
   ```sql
   CREATE DATABASE rent_car;
   ```
3. Подключитесь к базе данных `rent_car`
4. Выполните SQL скрипт `database_schema.sql` для создания таблиц

### 2. Настройка переменных окружения

Установите переменные окружения для подключения к базе данных:

**Windows (PowerShell):**
```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/rent_car"
$env:DB_USER = "postgres"
$env:DB_PASSWORD = "ваш_пароль"
```

**Windows (CMD):**
```cmd
set DB_URL=jdbc:postgresql://localhost:5432/rent_car
set DB_USER=postgres
set DB_PASSWORD=ваш_пароль
```

**Linux/Mac:**
```bash
export DB_URL="jdbc:postgresql://localhost:5432/rent_car"
export DB_USER="postgres"
export DB_PASSWORD="ваш_пароль"
```

**В IntelliJ IDEA:**
1. Run → Edit Configurations
2. Выберите Main class
3. В разделе "Environment variables" добавьте:
   - `DB_URL=jdbc:postgresql://localhost:5432/rent_car`
   - `DB_USER=postgres`
   - `DB_PASSWORD=ваш_пароль`

### 3. Проверка зависимостей

Убедитесь, что PostgreSQL драйвер добавлен в проект:
- В IntelliJ IDEA: File → Project Structure → Libraries
- Должна быть библиотека `postgresql-42.7.9` (или другая версия)

### 4. Запуск приложения

**В IntelliJ IDEA:**
1. Откройте файл `src/Main.java`
2. Нажмите правой кнопкой мыши на класс `Main`
3. Выберите "Run 'Main.main()'"

**Из командной строки:**
```bash
# Скомпилируйте проект
javac -d out -cp "lib/postgresql-42.7.9.jar" src/**/*.java

# Запустите
java -cp "out:lib/postgresql-42.7.9.jar" Main
```

### 5. Использование приложения

После запуска вы увидите меню:
- **1. Login** - войти в систему
- **2. Register** - зарегистрироваться как клиент

**Тестовые пользователи:**
- Admin: username=`admin`, password=`admin123`
- Manager: username=`manager`, password=`manager123`

После входа вы увидите меню с функциями в зависимости от роли:
- **ADMIN**: может добавлять машины, просматривать все заказы
- **MANAGER**: может добавлять клиентов, услуги, создавать заказы
- **USER**: может просматривать машины

## Устранение проблем

**Ошибка подключения к БД:**
- Проверьте, что PostgreSQL запущен
- Проверьте правильность переменных окружения
- Убедитесь, что база данных `rent_car` создана
- Проверьте, что таблицы созданы (выполните `database_schema.sql`)

**Ошибка "ClassNotFoundException: org.postgresql.Driver":**
- Убедитесь, что PostgreSQL драйвер добавлен в classpath
- В IntelliJ: File → Project Structure → Libraries → добавьте JAR файл драйвера
