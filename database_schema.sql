-- Rent Car Database Schema
-- Run this script in PostgreSQL to create the database structure

-- Create users table for authentication
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);

-- Create customer table
CREATE TABLE IF NOT EXISTS customer (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

-- Create car table
CREATE TABLE IF NOT EXISTS car (
    car_id SERIAL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INTEGER NOT NULL,
    price_per_day DECIMAL(10, 2) NOT NULL,
    category VARCHAR(20) NOT NULL CHECK (category IN ('ECONOMY', 'SUV', 'LUXURY'))
);

-- Create service table
CREATE TABLE IF NOT EXISTS service (
    service_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Create rental_order table
CREATE TABLE IF NOT EXISTS rental_order (
    order_id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL REFERENCES customer(customer_id),
    car_id INTEGER NOT NULL REFERENCES car(car_id),
    days INTEGER NOT NULL CHECK (days > 0)
);

-- Create rental_order_service junction table
CREATE TABLE IF NOT EXISTS rental_order_service (
    order_id INTEGER NOT NULL REFERENCES rental_order(order_id),
    service_id INTEGER NOT NULL REFERENCES service(service_id),
    PRIMARY KEY (order_id, service_id)
);

-- Insert sample admin user (username: admin, password: admin123)
INSERT INTO users (username, password, role) 
VALUES ('admin', 'admin123', 'ADMIN')
ON CONFLICT (username) DO NOTHING;

-- Insert sample manager user (username: manager, password: manager123)
INSERT INTO users (username, password, role) 
VALUES ('manager', 'manager123', 'MANAGER')
ON CONFLICT (username) DO NOTHING;
