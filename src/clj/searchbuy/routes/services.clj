(ns searchbuy.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [searchbuy.user :as user :refer :all]
            [searchbuy.merchant :as merchant :refer :all]
            [searchbuy.order :as order :refer :all]
            [searchbuy.product :as product :refer :all]
            [searchbuy.preferences :as preferences :refer :all]
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
        (ok (map :id (db/get-merchants-by-name *db* {:name (str q "%")}))))
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
        (ok (map :id (db/get-products-by-merchant *db* {:merchant id})))))
    (context "/products" []
      :tags ["product"]
      (GET "/" []
        :query-params [{q :- String ""}]
        :summary "return all products or query them with q param"
        :return [String]
        (ok (map :id (db/get-products-by-name *db* {:name (str q "%")}))))
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
        (ok (map :id (db/get-users-by-name *db* {:name (str q "%")}))))
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
        (if (= 1 (db/delete-user! *db* {:id id}))
          (ok)
          (internal-server-error)))
      (GET "/:id/preferences" []
        :path-params [id :- String]
        :return Preferences
        (let [preferences (db/get-preferences-by-user *db* {:user_id id})
              preferences-id (:id preferences)
              categories (db/get-category-by-preferences *db* {:preferences_id preferences-id})]
          (ok (assoc preferences :category (map :name categories)))))
      (POST "/:id/preferences" []
        :path-params [id :- String]
        :body [body NewPreferences]
        :return Preferences
        (let [uuid (get-uuid)
              preferences-without-category (dissoc body :category)
              categories (:category body)
              categories-created? (every? #(= 1 %) (map #(db/create-category! *db* {:id (get-uuid)
                                                                                    :name %
                                                                                    :preferences_id uuid}) categories))]
          (if (and (= 1 (db/create-preferences! *db* (assoc preferences-without-category :id uuid
                                                            :user_id id)))
                   categories-created?)
            (ok (assoc body :id uuid
                       :user_id id))
            (internal-server-error))))
      (PUT "/:id/preferences" []
        :path-params [id :- String]
        :body [body NewPreferences]
        :return Preferences
        (let [updated? (= 1 (db/update-preferences-by-user! *db* (assoc body :user_id id)))
              preferences (db/get-preferences-by-user *db* {:user_id id})
              preferences-id (:id preferences)
              _ (db/delete-category-by-preferences! *db* {:preferences_id preferences-id})
              categories-created? (every? #(= 1 %) (map #(db/create-category! *db* {:id (get-uuid)
                                                                                    :name %
                                                                                    :preferences_id preferences-id}) (:category body)))]
          (if (and updated? categories-created?)
            (ok (assoc preferences :category (:category body)))
            (internal-server-error))))
      (DELETE "/:id/preferences" []
        :path-params [id :- String]
        (if (= 1 (db/delete-preferences-by-user! *db* {:user_id id}))
          (ok)
          (internal-server-error)))
      (GET "/:id/orders" []
        :path-params [id :- String]
        :return [String]
        (ok (map :id (db/get-orders-by-user *db* {:user_id id})))))
    (context "/orders" []
      :tags ["order"]
      (GET "/" []
        :query-params [{q :- String ""}]
        :summary "return all products or query them with q param"
        :return [String]
        (ok (map :id (db/get-all-orders *db*))))
      (POST "/" []
        :body [body NewOrder]
        :return String
        (let [uuid (get-uuid)]
          (if (= 1 (db/create-order! *db* (assoc body :id uuid)))
            (ok uuid)
            (internal-server-error))))
      (GET "/:id" []
        :path-params [id :- String]
        :return Order
        (ok (db/get-order *db* {:id id})))
      (PUT "/:id" []
        :path-params [id :- String]
        :body [body NewOrder]
        :return String
        (if (= 1 (db/update-order! *db* (assoc body :id id)))
          (ok id)
          (internal-server-error)))
      (DELETE "/:id" []
        :path-params [id :- String]
        (if (= 1 (db/delete-order! *db* {:id id}))
          (ok)
          (internal-server-error))))))
