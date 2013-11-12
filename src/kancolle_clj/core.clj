(ns kancolle-clj.core
  (:use [kancolle-clj.setting])
  (:require [clj-http.client :as client]
            [net.cgrand.enlive-html :as enlive]
            [clojure.pprint :only [pprint]]
            [clojure.data.json :as json]))

(defn create-api-call [path]
  (fn [& options]
    (json/read-str
      (second 
        (clojure.string/split
          (:body 
            (client/post 
              (str "http://" server "/kcsapi" path)
              {:headers {"Referer" (str "http://" server "/kcs/scenes/title.swf?version=1.1.2")
              "User-Agent" "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.114 Safari/537.36"}
              :form-params (merge {:api_verno 1, :api_token api-token} (apply hash-map options))}))
          #"svdata="))
      :key-fn keyword)))

