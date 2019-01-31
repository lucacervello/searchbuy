CREATE TABLE orders
(id serial PRIMARY KEY,
 order_date TIMESTAMP,
 estimated_delivery_date TIMESTAMP,
 merchant_id VARCHAR(300),
 product_id VARCHAR(300),
 user_id VARCHAR(300))
