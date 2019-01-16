(ns searchbuy.test.db.core
  (:require [searchbuy.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [searchbuy.config :refer [env]]
            [mount.core :as mount]))

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
               {:id         "1"
                :first_name "Sam"
                :last_name  "Smith"
                :email      "sam.smith@example.com"
                :pass       "pass"})))
    (is (= {:id         "1"
            :first_name "Sam"
            :last_name  "Smith"
            :email      "sam.smith@example.com"
            :pass       "pass"
            :admin      nil
            :last_login nil
            :is_active  nil}
           (db/get-user t-conn {:id "1"})))))

(deftest test-products
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-product!
              t-conn
              {:id         "1"
               :name "prodotto"
               :type "fisico"
               :description "bello",
               :price 10
               :shipping_type "aria"
               :revision 1
               :merchant "3"})))
    (is (= {:id         "1"
            :name "prodotto"
            :type "fisico"
            :description "bello",
            :price 10
            :shipping_type "aria"
            :revision 1
            :merchant "3"}
           (db/get-product t-conn {:id "1"})))))
