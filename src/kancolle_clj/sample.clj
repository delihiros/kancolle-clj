(ns kancolle-clj.sample
  (:use [kancolle-clj.setting]
        [kancolle-clj.wrapper]
        [kancolle-clj.api]))

(defn leveling-fleet [fleet-number]
  (let [fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (battle-start :api_formation_id 1 :api_deck_id fleet-number :api_mapinfo_no 2 :api_maparea_id 3)
    (battle :api_formation 1)
    (battle-result)
    (ship2 :api_sort_order 2 :api_sort_key 1)
    (slotitem)
    (deck)
    (logincheck)
    (material)
    (deck-port)
    (ndock)
    (ship2 :api_sort_order 2 :api_sort_key 1)
    (basic)
    (charge-fleet fleet)))

(defn get-devtool-by [fleet-number]
  (let [fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (mission-start :api_deck_id fleet-number :api_mission_id 2)
    (deck)))

(defn get-bauxite-by [fleet-number]
  (let [fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (mission-start :api_deck_id fleet-number :api_mission_id 15)
    (deck)))

(defn recover-most-damaged-ship []
  (let [ndock-status (ndock)
                     empty-docks (map :api_id (filter (fn [dock] (= (:api_state dock) 0)) (:api_data (ndock))))]
    (if (not (zero? (count empty-docks)))
      (recover-ship
        (first
          (filter (fn [ship]
                    (and (not= (:api_nowhp ship) (:api_maxhp ship))
                         (not-any? #{(:api_id ship)}
                                   (map :api_ship_id (:api_data (ndock))))))
                  (sort-by
                    (fn [ship] (/ (:api_nowhp ship) (:api_maxhp ship)))
                    (:api_data (ship))))) (first empty-docks)))))

(comment
  (for [fleet-id [2 3 4]]
    (leveling-fleet fleet-id))
  (get-devtool-by 3)
  (get-bauxite-by 4)
  )
