(ns searchbuy.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [searchbuy.handler :refer :all]
            [searchbuy.middleware.formats :as formats]
            [searchbuy.util :refer [get-uuid]]
            [muuntaja.core :as m]
            [mount.core :as mount]
            [java-time :as time]))

(defn parse-json [body]
  (m/decode formats/instance "application/json" body))

(use-fixtures
  :once
  (fn [f]
    (mount/start #'searchbuy.config/env
                 #'searchbuy.handler/app)
    (f)))

(deftest test-merchants
  (testing "POST /merchants"
    (let [merchant {:name "merchant"
                    :type "solo"
                    :telephone "233423453233"
                    :social_number "jdaskdjasdk"}
          response (app (-> (request :post "/merchants" )
                            (json-body merchant)))
          response-id (parse-json (:body response))
          _ (app (request :delete (str "/merchants/" response-id)))]
      (is (= 200 (:status response)))
      (is (string? response-id))))
  (testing "GET /merchants"
    (let [response (app (request :get "/merchants"))]
      (is (= 200 (:status response)))
      (is (= [] (parse-json (:body response))))))
  (testing "PUT /merchants"
    (let [merchant {:name "merchant"
                    :type "solo"
                    :telephone "233423453233"
                    :social_number "jdaskdjasdk"}
          merchant' {:name "merchant 1"
                     :type "solo"
                     :telephone "233423453233"
                     :social_number "jdaskdjasdk"}
          response (app (-> (request :post "/merchants" )
                            (json-body merchant)))
          response-id (parse-json (:body response))
          response' (app (-> (request :put (str "/merchants/" response-id))
                             (json-body merchant')))
          _ (app (request :delete (str "/merchants/" response-id)))]
      (is (= 200 (:status response)))
      (is (string? response-id))
      (is (= 200 (:status response')))
      (is (= response-id (parse-json (:body response')))))))

(deftest product-test
  (testing "POST product"
    (let [merchant-id (get-uuid)
          product {:name "product"
                   :type "tech"
                   :description "good monitor"
                   :price 30000
                   :shipping_type "fast"
                   :revision 1
                   :merchant merchant-id}
          response (app (-> (request :post "/products")
                            (json-body product)))
          response-id (parse-json (:body response))
          _ (app (request :delete (str "/products/" response-id)))]
      (is (= 200 (:status response)))
      (is (string? response-id))))
  (testing "GET /products"
    (let [response (app (request :get "/products"))]
      (is (= 200 (:status response)))
      (is (= [] (parse-json (:body response))))))
  (testing "PUT /products"
    (let [merchant-id (get-uuid)
          product {:name "product"
                   :type "tech"
                   :description "good monitor"
                   :price 30000
                   :shipping_type "fast"
                   :revision 1
                   :merchant merchant-id}
          product' {:name "product 1"
                   :type "technology"
                   :description "good monitor"
                   :price 30000
                   :shipping_type "fast"
                   :revision 1
                   :merchant merchant-id}
          response (app (-> (request :post "/products")
                            (json-body product)))
          response-id (parse-json (:body response))
          response' (app (-> (request :put (str "/products/" response-id))
                             (json-body product')))
          _ (app (request :delete (str "/products/" response-id)))]
      (is (= 200 (:status response)))
      (is (string? response-id))
      (is (= 200 (:status response')))
      (is (= response-id (parse-json (:body response')))))))

(deftest users-test
  (testing "POST user"
    (let [user {:first_name "Luca"
                :last_name "Cervello"
                :email "l.c@luca.com"
                :pass "hey"}
          response (app (-> (request :post "/users")
                            (json-body user)))
          response-id (parse-json (:body response))
          _ (app (request :delete (str "/users/" response-id)))]
      (is (= 200 (:status response)))
      (is (string? response-id))))
  (testing "GET user"
    (let [response (app (request :get "/users"))]
      (is (= 200 (:status response)))
      (is (= [] (parse-json (:body response))))))
  (testing "PUT user"
    (let [user {:first_name "Luca"
                :last_name "Cervello"
                :email "l.c@luca.com"
                :pass "hey"}
          user' {:first_name "Marco"
                 :last_name "Cervello"
                 :email "l.c@luca.com"
                 :pass "hey"}
          response (app (-> (request :post "/users")
                            (json-body user)))
          response-id (parse-json (:body response))
          response' (app (-> (request :put (str "/users/" response-id))
                             (json-body user')))
          _ (app (request :delete (str "/users/" response-id)))]
      (is (= 200 (:status response)))
      (is (string? response-id))
      (is (= 200 (:status response')))
      (is (= response-id (parse-json (:body response'))))))
  (testing "GET orders from merchant"
    (let [merchant-id (get-uuid)
          product-id (get-uuid)
          user {:first_name "Marco"
                :last_name "Cervello"
                :email "l.c@luca.com"
                :pass "hey"}
          user-response (app (-> (request :post "/users")
                            (json-body user)))
          user-id (parse-json (:body user-response))
          order {:order_date (time/local-date 2019 1 1)
                 :estimated_delivery_date (time/local-date 2019 1 7)
                 :merchant_id merchant-id
                 :product_id product-id
                 :user_id user-id}
          order-response (app (-> (request :post "/orders")
                            (json-body order)))
          order-id (parse-json (:body order-response))
          response (app (request :get (str "/users/" user-id "/orders")))
          _ (app (request :delete (str "/users/" user-id)))
          _ (app (request :delete (str "/orders/" order-id)))]
      (is (= 200 (:status response)))
      (is (= [order-id] (parse-json (:body response)))))))

(deftest orders-test
  (testing "POST order"
    (let [merchant-id (get-uuid)
          product-id (get-uuid)
          user-id (get-uuid)
          order {:order_date (time/local-date 2019 1 1)
                 :estimated_delivery_date (time/local-date 2019 1 7)
                 :merchant_id merchant-id
                 :product_id product-id
                 :user_id user-id}
          response (app (-> (request :post "/orders")
                            (json-body order)))
          response-id (parse-json (:body response))
          _ (app (request :delete (str "/orders/" response-id)))]
      (is (= 200 (:status response)))
      (is (string? response-id))))
  (testing "GET /orders"
    (let [response (app (request :get "/orders"))]
      (is (= 200 (:status response)))
      (is (= [] (parse-json (:body response))))))
  (testing "PUT /orders"
    (let [merchant-id (get-uuid)
          product-id (get-uuid)
          user-id (get-uuid)
          order {:order_date (time/local-date 2019 1 1)
                 :estimated_delivery_date (time/local-date 2019 1 7)
                 :merchant_id merchant-id
                 :product_id product-id
                 :user_id user-id}
          order' {:order_date (time/local-date 2019 1 1)
                  :estimated_delivery_date (time/local-date 2019 1 9)
                  :merchant_id merchant-id
                  :product_id product-id
                  :user_id user-id}
          response (app (-> (request :post "/orders" )
                            (json-body order)))
          response-id (parse-json (:body response))
          response' (app (-> (request :put (str "/orders/" response-id))
                             (json-body order')))
          _ (app (request :delete (str "/orders/" response-id)))]
      (is (= 200 (:status response)))
      (is (string? response-id))
      (is (= 200 (:status response')))
      (is (= response-id (parse-json (:body response')))))))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= 404 (:status response))))))
