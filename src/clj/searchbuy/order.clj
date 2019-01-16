(ns searchbuy.order
  (:require [schema.core :as s]))

(s/defschema Order {:id Long})

(s/defschema NewOrder (dissoc Order :id))

