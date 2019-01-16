(ns searchbuy.user
  (:require [schema.core :as s]))

(s/defschema User {:id Long})

(s/defschema NewUser (dissoc User :id))

