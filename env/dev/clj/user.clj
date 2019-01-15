(ns user
  (:require [searchbuy.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [searchbuy.core :refer [start-app]]
            [searchbuy.db.core]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'searchbuy.core/repl-server))

(defn stop []
  (mount/stop-except #'searchbuy.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'searchbuy.db.core/*db*)
  (mount/start #'searchbuy.db.core/*db*)
  (binding [*ns* 'searchbuy.db.core]
    (conman/bind-connection searchbuy.db.core/*db* "sql/queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


