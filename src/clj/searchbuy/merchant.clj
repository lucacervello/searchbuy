(ns searchbuy.merchant
  (:require [schema.core :as s]))

(s/defschema Merchant {:id String
                       :name String
                       :type String
                       :telephone String
                       :social_number String})
(s/defschema NewMerchant (dissoc Merchant :id))

