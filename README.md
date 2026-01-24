# Rent Car Management System - OOP Project (Iteration 1)

## 🚗 Project Overview
This is a console-based application designed for a **Car Rental Company** to manage its clients. The project is built using **Java** and **PostgreSQL**, following clean architecture principles and SOLID design patterns.

## 🛠 Features
- **User Registration**: Register new clients with name, surname, email, and phone.
- **Client Search**: Find clients by their unique ID or Phone Number.
- **Database Integration**: Persistent storage using PostgreSQL.
- **Adding car models**
- **Viewing car models**
- **Additional services**
- **Creating rental orders**
- **Viewing rental orders**

## 🏗 Project Structure (Layered Architecture)
The project is divided into 5 logical layers to ensure high cohesion and low coupling:
1. **Models**: POJO classes representing entities (`User`, «CarID, CustomerID»,).
2. **Data**: Database connection logic (PostgresDB).
3. **Repositories**: Data Access Object (DAO) layer for SQL execution.
4. **Controllers**: Business logic coordination.
5. **Main**: Command-line interface for user interaction.

## 💻 Technologies Used
- **Java, IntelliJ IDEA**
- **PostgreSQL Driver**
- **Dependency Injection** (Manual)
- **Interface-based Programming**

## 🚀 How to Run
1. Clone the repository.
2. Create a PostgreSQL database using the following SQL:
   ```sql
   CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       name VARCHAR(50),
       surname VARCHAR(50),
       email VARCHAR(100),
       phone VARCHAR(20)
   );
