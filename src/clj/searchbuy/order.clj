(ns searchbuy.order
  (:require [schema.core :as s]
            [java.time :refer [LocalDate]]))

(s/defschema Order {:id String
                    :order_date LocalDate
                    :estimated_delivery_date LocalDate
                    :merchant_id String
                    :product_id String
                    :user_id String})

(s/defschema NewOrder (dissoc Order :id))

