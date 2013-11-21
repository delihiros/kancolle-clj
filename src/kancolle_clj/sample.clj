(ns kancolle-clj.sample
  (:use [kancolle-clj.setting]
        [kancolle-clj.wrapper]
        [kancolle-clj.api]))

;; 第一艦隊を自動で回復-> 3-2-1
;; (leveling-fleet 1)

(defn leveling-fleet [fleet-number]
  (let [fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (battle-start :api_formation_id 1 :api_deck_id fleet-number :api_mapinfo_no 2 :api_maparea_id 3)
    (battle :api_formation fleet-number)
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

(leveling-fleet 2)

(defn get-devtool-by [fleet-number]
  (let [fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (mission-start :api_deck_id 3 :api_mission_id 2)
    (deck)))

(comment
  (charge-fleet (nth (get-fleets-ids) 2))
  (get-devtool-by 3)
  (mission-result :api_deck_id 3)
  )

(clojure.pprint/pprint
  (recover-ship
    (first
      (filter (fn [ship]
                (and (not= (:api_nowhp ship) (:api_maxhp ship))
                     (not-any? #{(:api_id ship)}
                               (map :api_ship_id (:api_data (get-ndock))))))
              (sort-by
                (fn [ship] (/ (:api_nowhp ship) (:api_maxhp ship)))
                (:api_data (get-ship))))) 1))
