(ns kancolle-clj.wrapper
  (:use [kancolle-clj.core]
        [kancolle-clj.api]))


(defn get-fleets-ids []
  (map #(clojure.string/join "," (:api_ship %))
       (:api_data_deck (get-ship2 :api_sort_order 2 :api_sort_key 1))))

(defn get-damaged-ships []
  (sort-by #(/ (:api_nowhp %) (:api_maxhp %))
           (filter #(and (not= (:api_nowhp %) (:api_maxhp %))  
                         (not-any? #{(:api_id %)} (map  :api_ship_id (:api_data (get-ndock)))))
                   (:api_data (get-ship)))))

(defn charge-fleet [fleet]
  (charge :api_kind 3, :api_id_items fleet))

(defn recover-ship [ship ndock_id]
  (start-recovery :api_ship_id (:api_id ship) :api_ndock_id ndock_id :api_highspeed 0))
