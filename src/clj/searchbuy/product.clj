(ns searchbuy.product
  (:require [schema.core :as s]))

(s/defschema Product {:id Long})

(s/defschema NewProduct (dissoc Product :id))

(s/defschema Preferences {:id Long})

(s/defschema NewPreferences (dissoc Preferences :id))

