(ns searchbuy.merchant
  (:require [schema.core :as s]))

(s/defschema Merchant {:id Long})
(s/defschema NewMerchant (dissoc Merchant :id))
