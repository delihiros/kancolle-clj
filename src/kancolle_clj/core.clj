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

(def get-basic (create-api-call "/api_get_member/basic"))
(def get-record (create-api-call "/api_get_member/record"))
(def get-mapinfo (create-api-call "/api_get_master/mapinfo"))
(def login-check (create-api-call "/api_auth_member/logincheck"))
(def get-ndock (create-api-call "/api_get_member/ndock"))
(def get-ship2 (create-api-call "/api_get_member/ship2"))
(def speed-change (create-api-call "/api_req_nyukyo/speedchange"))
(def get-material (create-api-call "/api_get_member/material"))
(def start-recovery (create-api-call "/api_req_nyukyo/start"))
(def get-ship (create-api-call "/api_get_member/ship"))
(def actionlog (create-api-call "/api_get_member/actionlog"))
(def charge (create-api-call "/api_req_hokyu/charge"))
(def battle-start (create-api-call "/api_req_map/start"))
(def battle (create-api-call "/api_req_sortie/battle"))
(def battle-result (create-api-call "/api_req_sortie/battleresult"))
(def slotitem (create-api-call "/api_get_member/slotitem"))
(def deck (create-api-call "/api_get_member/deck"))
(def deck-port (create-api-call "/api_get_member/deck_port"))

(comment
  (clojure.pprint/pprint (let [result (:api_data (get-ship))]
                           (sort-by #(nth % 2)
                                    (map #(list (:api_id %) (:api_ship_id %) 
                                                (:api_name %) (:api_lv %))
                                         result))))

  ;; 第一艦隊を自動で回復-> 3-2-1
  (clojure.pprint/pprint 
    (let [fleet (first (get-fleets-ids))]
      (charge-fleet fleet)
      (battle-start :api_formation_id 1 :api_deck_id 1 :api_mapinfo_no 2 :api_maparea_id 3)
      (battle :api_formation 1)
      (battle-result)
      (get-ship2 :api_sort_order 2 :api_sort_key 1)
      (slotitem)
      (deck)
      (login-check)
      (get-material)
      (deck-port)
      (get-ndock)
      (get-ship2 :api_sort_order 2 :api_sort_key 1)
      (get-basic)
      (charge-fleet fleet)))

  (defn get-fleets-ids []
    (map #(clojure.string/join "," %)
         (map :api_ship (:api_data_deck (get-ship2 :api_sort_order 2 :api_sort_key 1)))))

  (defn get-damaged-ships []
    (sort-by #(/ (:api_nowhp %) (:api_maxhp %))
             (filter #(and (not= (:api_nowhp %) (:api_maxhp %))  
                           (not-any? #{(:api_id %)} (map  :api_ship_id (:api_data (get-ndock)))))
                     (:api_data (get-ship)))))

  (defn charge-fleet [fleet]
    (charge :api_kind 3, :api_id_items fleet))
  (map charge-fleet (get-fleets-ids))


  (defn recover-ship [ship ndock_id]
    (start-recovery :api_ship_id (:api_id ship) :api_ndock_id ndock_id :api_highspeed 0))
  )
