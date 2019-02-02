(ns searchbuy.user
  (:require [schema.core :as s]))

(s/defschema User {:id String
                   :first_name String
                   :last_name String
                   :email String
                   :pass String})

(s/defschema NewUser (dissoc User :id))

