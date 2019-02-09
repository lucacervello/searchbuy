(ns searchbuy.test.db.core
  (:require [searchbuy.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [searchbuy.config :refer [env]]
            [searchbuy.util :refer [get-uuid]]
            [mount.core :as mount]))

(defonce uuid (get-uuid))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'searchbuy.config/env
      #'searchbuy.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-users
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)

    (is (= 1 (db/create-user!
              t-conn
              {:id         uuid
               :first_name "Sam"
               :last_name  "Smith"
               :email      "sam.smith@example.com"
               :pass       "pass"})))
    (is (= {:id         uuid
            :first_name "Sam"
            :last_name  "Smith"
            :email      "sam.smith@example.com"
            :pass       "pass"}
           (db/get-user t-conn {:id uuid})))
    (is (= 1 (db/update-user!
              t-conn
              {:id         uuid
               :first_name "Samantha"
               :last_name  "Smith"
               :email      "sam.smith@example.com"
               :pass       "pass"})))
    (is (= {:id         uuid
            :first_name "Samantha"
            :last_name  "Smith"
            :email      "sam.smith@example.com"
            :pass       "pass"}
           (db/get-user t-conn {:id uuid})))
    (is (= 1 (db/delete-user! t-conn {:id uuid})))
    (is (= nil (db/get-user t-conn {:id uuid})))))

(deftest test-products
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-product!
              t-conn
              {:id uuid
               :name "prodotto"
               :type "fisico"
               :description "bello",
               :price 10
               :shipping_type "aria"
               :revision 1
               :merchant "3"})))
    (is (= {:id uuid
            :name "prodotto"
            :type "fisico"
            :description "bello",
            :price 10
            :shipping_type "aria"
            :revision 1
            :merchant "3"}
           (db/get-product t-conn {:id uuid})))
    (is (= 1 (db/update-product!
              t-conn
              {:id uuid
               :name "prodotto 1"
               :type "fisico"
               :description "bello",
               :price 10
               :shipping_type "aria"
               :revision 1
               :merchant "3"})))
    (is (= {:id uuid
            :name "prodotto 1"
            :type "fisico"
            :description "bello",
            :price 10
            :shipping_type "aria"
            :revision 1
            :merchant "3"}
           (db/get-product t-conn {:id uuid})))
    (is (= 1 (db/delete-product! t-conn {:id uuid})))
    (is (= nil (db/get-product t-conn {:id uuid})))))

(deftest test-merchants
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-merchant!
              t-conn
              {:id uuid
               :name "merchant"
               :type "fisico"
               :telephone "333232313"
               :social_number "crvelelejd94jnb2"})))
    (is (= [{:id uuid
            :name "merchant"
            :type "fisico"
            :telephone "333232313"
            :social_number "crvelelejd94jnb2"}]
           (db/get-merchants-by-name t-conn {:name "%"})))
    (is (= {:id uuid
            :name "merchant"
            :type "fisico"
            :telephone "333232313"
            :social_number "crvelelejd94jnb2"}
           (db/get-merchant t-conn {:id uuid})))
    (is (= 1 (db/update-merchant!
              t-conn
              {:id uuid
               :name "merchant 1"
               :type "fisico"
               :telephone "333232313"
               :social_number "crvelelejd94jnb2"})))
    (is (= {:id uuid
            :name "merchant 1"
            :type "fisico"
            :telephone "333232313"
            :social_number "crvelelejd94jnb2"}
           (db/get-merchant t-conn {:id uuid})))
    (is (= 1 (db/delete-merchant! t-conn {:id uuid})))
    (is (= nil (db/get-merchant t-conn {:id uuid})))))
