(ns searchbuy.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [searchbuy.user :as user :refer :all]
            [searchbuy.merchant :as merchant :refer :all]
            [searchbuy.order :as order :refer :all]
            [searchbuy.product :as product :refer :all]
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
        :return [Merchant]
        (ok [{:id 1}]))
      (POST "/" []
        :body [body NewMerchant]
        :return Merchant
        (ok {:id 1}))
      (GET "/:id" []
        :path-params [id :- String]
        :return Merchant
        (ok))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body NewMerchant]
        :return Merchant
        (ok))
      (DELETE "/:id" []
        :path-params [id :- String]
        (ok))
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
        (ok))
      (GET "/:id" []
        :path-params [id :- String]
        :return Product
        (ok))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body Product]
        :return Product
        (ok))
      (DELETE "/:id" []
        :path-params [id :- String]
        (ok)))
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
        (ok))
      (GET "/:id" []
        :path-params [id :- String]
        :return User
        (ok))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body NewUser]
        :return User
        (ok))
      (DELETE "/:id" []
        :path-params [id :- String]
        (ok))
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
