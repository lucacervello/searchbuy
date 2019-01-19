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
        (ok ["hello" "hi"]))
      (POST "/" []
        :body [body NewMerchant]
        :return Merchant
        (ok (let [uuid (get-uuid)]
              (do (db/create-merchant! *db* (assoc body :id uuid))
                  (db/get-merchant *db* {:id uuid})))))
      (GET "/:id" []
        :path-params [id :- String]
        :return Merchant
        (ok (db/get-merchant *db* {:id id})))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body NewMerchant]
        :return Merchant
        (ok (do (db/update-merchant! *db* (assoc body :id id))
                (db/get-merchant *db* {:id id}))))
      (DELETE "/:id" []
        :path-params [id :- String]
        (do (db/delete-merchant! *db* {:id id})
            (ok)))
      (GET "/:id/products" []
        :path-params [id :- String]
        :return [Product]
        (ok)))
    (context "/products" []
      :tags ["product"]
      (GET "/" []
        :query-params [{q :- String ""}]
        :summary "return all products or query them with q param"
        (ok {:query q}))
      (POST "/" []
        :body [body NewProduct]
        :return Product
        (ok (let [uuid (get-uuid)]
              (do (db/create-product! *db* (assoc body :id uuid))
                  (db/get-product *db* {:id uuid})))))
      (GET "/:id" []
        :path-params [id :- String]
        :return Product
        (ok (db/get-product *db* {:id id})))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body Product]
        :return Product
        (ok (do (db/update-product! *db* (assoc body :id id))
                (db/get-product *db* {:id id}))))
      (DELETE "/:id" []
        :path-params [id :- String]
        (do (db/delete-product! *db* {:id id})
            (ok))))
    (context "/users" []
      :tags ["user"]
      (GET "/" []
        :query-params [{q :- String ""}]
        :summary "return all products or query them with q param"
        :return [User]
        (ok {:query q}))
      (POST "/" []
        :body [body NewUser]
        :return User
        (ok (let [uuid (get-uuid)]
              (do (db/create-user! *db* (assoc body :id uuid))
                  (db/get-user *db* {:id uuid})))))
      (GET "/:id" []
        :path-params [id :- String]
        :return User
        (ok (db/get-user *db* {:id id})))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body NewUser]
        :return User
        (ok (do (db/update-user! *db* (assoc body :id id))
                (db/get-user *db* {:id id}))))
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
