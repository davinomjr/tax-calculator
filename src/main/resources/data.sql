DROP TABLE IF EXISTS taxcalculator;

CREATE TABLE Orders (
    id int AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE Products (
    id int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    value DECIMAL(20, 2) NOT NULL,
);

CREATE TABLE ProductOrders (
    productId int PRIMARY KEY,
    orderId int PRIMARY KEY
);