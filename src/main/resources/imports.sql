-- Insert sample categories
INSERT INTO category (name) VALUES
    ('Mountain Bikes'),
    ('Road Bikes'),
    ('Hybrid Bikes'),
    ('City Bikes'),
    ('uncategorized');

-- Insert sample brands
INSERT INTO brand (name) VALUES
    ('Trek'),
    ('Giant'),
    ('Specialized'),
    ('Cannondale'),
    ('othere');

-- Insert sample bicycles
INSERT INTO bicycle (name, price, category_id, brand_id) VALUES
    ('Trek Fuel EX 8', 2499.99, 1, 1),
    ('Giant Defy Advanced Pro', 3499.99, 2, 2),
    ('Specialized Sirrus X 4.0', 899.99, 3, 2),
    ('Cannondale Quick CX 3', 799.99, 4, 4);

-- Insert sample customers
INSERT INTO customer (username, email,password, phone_number) VALUES
    ('JohnDoe', 'johndoe@example.com','password1', '123-456-7890'),
    ('JaneSmith', 'janesmith@example.com','password2', '987-654-3210');

-- Insert sample orders
INSERT INTO order_table (customer_id, order_date, total_amount) VALUES
    (1, '2023-05-01', 2499.99),
    (2, '2023-05-05', 3499.99);

-- Insert sample order items
INSERT INTO order_item (order_id, bicycle_id, quantity, price) VALUES
    (1, 1, 1, 2499.99),
    (2, 2, 1, 3499.99);

---- Insert statements for the user table
--INSERT INTO user (username, password) VALUES ('Mitar', 'password');
--INSERT INTO user (username, password) VALUES ('jane', 'secure456');
--INSERT INTO user (username, password) VALUES ('admin', 'adminpass');
--
-- Insert statements for the role table
INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_MANAGER');

---- Insert statements for the user_role table (assigning roles to users)
--INSERT INTO user_role (user_id, role_id) VALUES (1, 1); -- User 'john' has ROLE_USER
--INSERT INTO user_role (user_id, role_id) VALUES (2, 1); -- User 'jane' has ROLE_USER
--INSERT INTO user_role (user_id, role_id) VALUES (3, 2); -- User 'admin' has ROLE_ADMIN
--INSERT INTO user_role (user_id, role_id) VALUES (3, 3); -- User 'admin' has ROLE_MANAGER

INSERT INTO customer_role (customer_id, role_id) VALUES (1, 1);
INSERT INTO customer_role (customer_id, role_id) VALUES (2, 2);
