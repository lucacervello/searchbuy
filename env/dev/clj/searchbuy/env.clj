(ns searchbuy.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [searchbuy.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[searchbuy started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[searchbuy has shut down successfully]=-"))
   :middleware wrap-dev})
