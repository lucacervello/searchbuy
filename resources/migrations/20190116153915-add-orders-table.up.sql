CREATE TABLE orders
(id serial PRIMARY KEY,
 order_date TIMESTAMP,
 estimated_delivery_date TIMESTAMP,
 merchant VARCHAR(300),
 product VARCHAR(300),
 user VARCHAR(300))
