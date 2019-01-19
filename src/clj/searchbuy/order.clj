(ns searchbuy.order
  (:require [schema.core :as s]))

(s/defschema Order {:id String})

(s/defschema NewOrder (dissoc Order :id))

