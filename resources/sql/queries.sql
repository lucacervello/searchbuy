-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name get-users-by-name :1 :*
SELECT * FROM users
WHERE first_name LIKE :name

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id

-- :name create-product! :! :n
-- :doc creates a new product record
INSERT INTO products
(id, name, type, description, price, shipping_type, revision, merchant)
VALUES (:id, :name, :type, :description, :price, :shipping_type, :revision, :merchant)

-- :name update-product! :! :n
-- :doc updates an existing product record
UPDATE products
SET name = :name, type = :type, description = :description, price = :price, shipping_type = :shipping_type, revision = :revision, merchant = :merchant
WHERE id = :id

-- :name get-product :? :1
SELECT * from products
WHERE id = :id

-- :name get-products-by-merchant :1 :*
SELECT * FROM products
WHERE :merchant = merchant

-- :name get-products-by-name :1 :*
SELECT * FROM products
WHERE name LIKE :name

-- :name delete-product! :! :n
DELETE FROM products
WHERE id = :id

-- :name create-merchant! :! :n
INSERT INTO merchants
(id, name, type, telephone, social_number)
VALUES (:id, :name, :type, :telephone, :social_number)

-- :name update-merchant! :! :n
UPDATE merchants
SET name = :name, type = :type, telephone = :telephone, social_number = :social_number
WHERE id = :id

-- :name get-merchant :? :1
SELECT * FROM merchants
WHERE id = :id

-- :name get-all-merchant-ids :? :*
SELECT id FROM merchants;

-- :name get-merchants-by-name :1 :*
SELECT * from merchants
WHERE name like :name

-- :name delete-merchant! :! :n
DELETE FROM merchants
WHERE id = :id

-- :name delete-all-merchants! :? :n
DELETE FROM merchants;

-- :name create-order! :! :n
INSERT INTO orders
(id, order_date, estimated_delivery_date, merchant_id, product_id, user_id)
VALUES (:id, :order_date, :estimated_delivery_date, :merchant_id, :product_id, :user_id)

-- :name update-order! :! :n
UPDATE orders
SET order_date = :order_date, estimated_delivery_date = :estimated_delivery_date, merchant_id = :merchant_id, product_id = :product_id, user_id = :user_id
WHERE id = :id

-- :name get-order :? :1
SELECT * FROM orders
WHERE id = :id

-- :name get-orders-by-user :1 :*
SELECT * FROM orders
WHERE user_id = :user_id

-- :name get-all-orders :? :*
SELECT * FROM orders

-- :name delete-order! :! :n
DELETE FROM orders
WHERE id = :id
