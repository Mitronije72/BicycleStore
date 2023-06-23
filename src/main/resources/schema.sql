-- Create the tables
CREATE TABLE IF NOT EXISTS category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS brand (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS bicycle (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    price DECIMAL(8, 2) NOT NULL,
    category_id INT,
    brand_id INT,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (brand_id) REFERENCES brand(id)
);

CREATE TABLE IF NOT EXISTS customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS order_table (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date DATE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS order_item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    bicycle_id INT,
    quantity INT NOT NULL,
    price DECIMAL(8, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES order_table(id),
    FOREIGN KEY (bicycle_id) REFERENCES bicycle(id)
);
--CREATE TABLE IF NOT EXISTS user (
--    id INT PRIMARY KEY AUTO_INCREMENT,
--    username VARCHAR(50) NOT NULL,
--    password VARCHAR(100) NOT NULL
--);
-- Create the role table
CREATE TABLE role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

-- Create the join table for the many-to-many relationship
--CREATE TABLE user_role (
--  user_id BIGINT NOT NULL,
--  role_id BIGINT NOT NULL,
--  PRIMARY KEY (user_id, role_id),
--  FOREIGN KEY (user_id) REFERENCES user (id),
--  FOREIGN KEY (role_id) REFERENCES role (id)
--);
CREATE TABLE customer_role (
  customer_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (customer_id, role_id),
  FOREIGN KEY (customer_id) REFERENCES customer (id),
  FOREIGN KEY (role_id) REFERENCES role (id)
);
