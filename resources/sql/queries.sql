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

-- :name delete-merchant! :! :n
DELETE FROM merchants
WHERE id = :id
