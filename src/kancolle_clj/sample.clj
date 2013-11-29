(ns kancolle-clj.sample
  (:use [kancolle-clj.setting]
        [kancolle-clj.wrapper]
        [kancolle-clj.api]))

(defn leveling-fleet [fleet-number]
  (let [fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (mapcell :port :api_mapinfo_no 2 :api_maparea_id 1)
    (battle-start :port :api_formation_id 1 :api_deck_id fleet-number :api_mapinfo_no 2 :api_maparea_id 3)
    (battle :battle :api_formation 1)
    (let [result (battle-result :battle)]
      (ship2 :battle :api_sort_order 2 :api_sort_key 1)
      (slotitem :battle)
      (deck :battle)
      (logincheck :port)
      (material :port)
      (deck-port :port)
      (ndock :port)
      (ship3 :port :api_sort_order 2 :api_sort_key 1)
      (basic :port)
      (charge-fleet fleet)
      result)))

(defn get-devtool-by [fleet-number]
  (let [fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (mission-start :port :api_deck_id fleet-number :api_mission_id 2)
    (deck :port)))

(defn get-bauxite-by [fleet-number]
  (let [fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (mission-start :port :api_deck_id fleet-number :api_mission_id 6)
    (deck :port)))

(defn get-fuel-by [fleet-number]
  (let [fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (mission-start :port :api_deck_id fleet-number :api_mission_id 5)
    (deck :port)))

(defn recover-most-damaged-ship []
  (let [ndock-status (ndock :port)
                     empty-docks (map :api_id (filter (fn [dock] (= (:api_state dock) 0)) (:api_data ndock-status)))]
    (if (not (zero? (count empty-docks)))
      (recover-ship
        (first
          (filter (fn [ship]
                    (and (not= (:api_nowhp ship) (:api_maxhp ship))
                         (not-any? #{(:api_id ship)}
                                   (map :api_ship_id (:api_data ndock-status)))))
                  (sort-by
                    (fn [ship] (/ (:api_nowhp ship) (:api_maxhp ship)))
                    (:api_data (ship :port))))) (first empty-docks)))))

(comment
  (for [fleet-id [1 2 3 4]]
    (leveling-fleet fleet-id))
  (for [fleet-id [1 3 4]]
    (get-bauxite-by fleet-id))
  (for [fleet-id [1 3 4]]
    (get-fuel-by fleet-id))
  (recover-most-damaged-ship)
  (leveling-fleet 4)
  
  (let [fleet-number 1
        fleet (nth (get-fleets-ids) (- fleet-number 1))]
    (charge-fleet fleet)
    (mapcell :port :api_mapinfo_no 2 :api_maparea_id 1)
    (battle-start :port :api_formation_id 1 :api_deck_id fleet-number :api_mapinfo_no 2 :api_maparea_id 3)
    (battle :battle :api_formation 1)
    (let [result (battle-result :battle)]
      (ship2 :battle :api_sort_order 2 :api_sort_key 1)
      (slotitem :battle)
      (deck :battle)
      (logincheck :port)
      (material :port)
      (deck-port :port)
      (ndock :port)
      (ship3 :port :api_sort_order 2 :api_sort_key 1)
      (basic :port)
      (charge-fleet fleet)
      result))
  )
