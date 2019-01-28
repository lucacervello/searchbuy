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

(deftest product-test )

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= 404 (:status response))))))
