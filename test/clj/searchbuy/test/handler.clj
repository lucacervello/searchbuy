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
  (testing "/ route"
    (let [response (app (request :get "/merchants"))]
      (is (= 200 (:status response)))
      (is (= ["hello" "hi"] (parse-json (:body response))))))
  (testing "POST /merchants"
    (let [merchant {:name "merchant"
                    :type "solo"
                    :telephone "233423453233"
                    :social_number "jdaskdjasdk"}
          response (app (-> (request :post "/merchants" )
                            (json-body merchant)) )]
      (is (= 200 (:status response)))
      (is (= merchant (dissoc (parse-json (:body response)) :id)))))
  )

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= 404 (:status response))))))
