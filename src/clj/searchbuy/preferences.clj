(ns searchbuy.preferences
  (:require [schema.core :as s]))

(s/defschema Preferences {:id String
                          :privacy Boolean
                          :user_id String
                          :category [String]})

(s/defschema NewPreferences (dissoc Preferences :id :user_id))

