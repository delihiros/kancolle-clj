(ns kancolle-clj.core
  (:use [kancolle-clj.setting])
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def referers
  {:port "/port.swf?version=1.5.2"
   :battle "/battle.swf?version=1.3.3"})

(defn create-api-call [path]
  (fn [referer & options]
    (json/read-str
      (second 
        (clojure.string/split
          (:body 
            (client/post 
              (str "http://" server "/kcsapi" path)
              {:headers 
              {"Referer" (str "http://" server "/kcs/" (referer referers))
              "User-Agent" "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.114 Safari/537.36"}
              :form-params (merge {:api_verno 1, :api_token api-token} (apply hash-map options))}))
          #"svdata="))
      :key-fn keyword)))

