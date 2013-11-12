(ns kancolle-clj.sample
  (:use [kancolle-clj.setting]
        [kancolle-clj.wrapper]
        [kancolle-clj.api]))

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


(clojure.pprint/pprint
  (recover-ship
    (first
      (filter (fn [ship]
                (and (not= (:api_nowhp ship) (:api_maxhp ship))
                     (not-any? #{(:api_id ship)}
                               (map :api_ship_id (:api_data (get-ndock))))))
              (sort-by
                (fn [ship] (/ (:api_nowhp ship) (:api_maxhp ship)))
                (:api_data (get-ship))))) 2))
