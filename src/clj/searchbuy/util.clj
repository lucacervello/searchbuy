(ns searchbuy.util)

(defn get-uuid []
  (.toString (java.util.UUID/randomUUID)))
