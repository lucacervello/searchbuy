CREATE TABLE products
(id VARCHAR(300) PRIMARY KEY,
 name VARCHAR(300),
 type VARCHAR(30),
 description VARCHAR(300),
 price INT,
 shipping_type VARCHAR(30),
 revision INT,
 merchant references merchants(merchant)
)
