(ns searchbuy.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[searchbuy started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[searchbuy has shut down successfully]=-"))
   :middleware identity})
