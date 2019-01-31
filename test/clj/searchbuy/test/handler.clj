(ns searchbuy.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [searchbuy.handler :refer :all]
            [searchbuy.middleware.formats :as formats]
            [searchbuy.util :refer [get-uuid]]
            [muuntaja.core :as m]
            [mount.core :as mount]))

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
          _ (app (request :delete (str "/merchants/" response-id)))]
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
