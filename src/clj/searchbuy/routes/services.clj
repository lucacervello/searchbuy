(ns searchbuy.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [searchbuy.user :as user :refer :all]
            [searchbuy.merchant :as merchant :refer :all]
            [searchbuy.order :as order :refer :all]
            [searchbuy.product :as product :refer :all]
            [searchbuy.db.core :refer [*db*] :as db]
            [searchbuy.util :refer [get-uuid]]
            [schema.core :as s]))

(def service-routes
  (api
    {:swagger {:ui "/swagger-ui"
               :spec "/swagger.son"
               :data {:info {:version "1.0.0"
                             :title "Sample API"
                             :description "Sample Services"}}}}
    (context "/merchants" []
      :tags ["merchant"]
      (GET "/" []
        :query-params [{q :- String ""}]
        :summary "return all merchants or query them with q param"
        :return [String]
        (ok (map :id (db/get-merchants-by-name *db* {:name q}))))
      (POST "/" []
        :body [body NewMerchant]
        :return String
        (let [uuid (get-uuid)]
          (if (= 1 (db/create-merchant! *db* (assoc body :id uuid)))
            (ok uuid)
            (internal-server-error))))
      (GET "/:id" []
        :path-params [id :- String]
        :return Merchant
        (ok (db/get-merchant *db* {:id id})))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body NewMerchant]
        :return String
        (if (= 1 (db/update-merchant! *db* (assoc body :id id)))
          (ok id)
          (internal-server-error)))
      (DELETE "/:id" []
        :path-params [id :- String]
        (if (= 1 (db/delete-merchant! *db* {:id id}))
          (ok)
          (internal-server-error)))
      (GET "/:id/products" []
        :path-params [id :- String]
        :return [String]
        (ok (db/get-products-by-merchant *db* {:merchant id}))))
    (context "/products" []
      :tags ["product"]
      (GET "/" []
        :query-params [{q :- String ""}]
        :summary "return all products or query them with q param"
        :return [String]
        (ok (map :id (db/get-products-by-name *db* {:name q}))))
      (POST "/" []
        :body [body NewProduct]
        :return String
        (let [uuid (get-uuid)]
          (if (= 1 (db/create-product! *db* (assoc body :id uuid)))
            (ok uuid)
            (internal-server-error))))
      (GET "/:id" []
        :path-params [id :- String]
        :return Product
        (ok (db/get-product *db* {:id id})))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body NewProduct]
        :return String
        (if (= 1 (db/update-product! *db* (assoc body :id id)))
          (ok id)
          (internal-server-error)))
      (DELETE "/:id" []
        :path-params [id :- String]
        (if (= 1 (db/delete-product! *db* {:id id}))
          (ok)
          (internal-server-error))))
    (context "/users" []
      :tags ["user"]
      (GET "/" []
        :query-params [{q :- String ""}]
        :summary "return all users or query them with q param"
        :return [String]
        (ok (map :id (db/get-users-by-name *db* {:name q}))))
      (POST "/" []
        :body [body NewUser]
        :return String
        (let [uuid (get-uuid)]
          (if (= 1 (db/create-user! *db* (assoc body :id uuid)))
            (ok uuid)
            (internal-server-error))))
      (GET "/:id" []
        :path-params [id :- String]
        :return User
        (ok (db/get-user *db* {:id id})))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body NewUser]
        :return String
        (if (= 1 (db/update-user! *db* (assoc body :id id)))
          (ok id)
          (internal-server-error)))
      (DELETE "/:id" []
        :path-params [id :- String]
        (do (db/delete-user! *db* {:id id})
            (ok)))
      (GET "/:id/preferences" []
        :path-params [id :- String]
        :return Preferences
        (ok))
      (POST "/:id/preferences" []
        :path-params [id :- String]
        :body [body Preferences]
        :return Preferences
        (ok))
      (GET "/:id/orders" []
        :query-params [{q :- String ""}]
        :path-params [id :- String]
        :return [Order]
        (ok))
      (POST "/:id/orders" []
        :path-params [id :- String]
        :body [body NewOrder]
        :return Order
        (ok)))
    (context "/orders" []
      :tags ["order"]
      (GET "/" []
        :query-params [{q :- String ""}]
        :summary "return all products or query them with q param"
        :return [Order]
        (ok {:query q}))
      (POST "/" []
        :body [body NewOrder]
        :return Order
        (ok))
      (GET "/:id" []
        :path-params [id :- String]
        :return Order
        (ok))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body NewOrder]
        :return Order
        (ok))
      (DELETE "/:id" []
        :path-params [id :- String]
        (ok)))))
