(ns searchbuy.product
  (:require [schema.core :as s]))

(s/defschema Product {:id String
                      :name String
                      :type String
                      :description String
                      :price Integer
                      :shipping_type String
                      :revision Integer
                      :merchant String})

(s/defschema NewProduct (dissoc Product :id))

(s/defschema Preferences {:id Long})

(s/defschema NewPreferences (dissoc Preferences :id))

