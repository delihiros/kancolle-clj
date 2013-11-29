(ns kancolle-clj.api
  (:use [kancolle-clj.core]))

(def basic
  "can get basic data such as who you are, how many time you've won, ..."
  (create-api-call "/api_get_member/basic"))

(def record
  "kinda same with basic, but more like a records"
  (create-api-call "/api_get_member/record"))

(def mapinfo
  "can get information about map such as its name and description"
  (create-api-call "/api_get_master/mapinfo"))

(def mapcell
  "can get information about map you selected"
  (create-api-call "/api_get_master/mapcell"))

(def ndock
 "can get information about recovery dock" 
  (create-api-call "/api_get_member/ndock"))

(def ship
  "can get information of all ships"
  (create-api-call "/api_get_member/ship"))

(def ship2
  "can get information about ships. This api is more compact then ship
  examples:
    (ship2 :api_sort_order 2 :api_sort_key 1)
  "
  (create-api-call "/api_get_member/ship2"))

(def ship3
  "can get information about ships. difference between ship2?
  examples:
    (ship3 :api_sort_order 2 :api_sort_key 1)
  "
  (create-api-call "/api_get_member/ship3"))

(def material
  "can get information about materials"
  (create-api-call "/api_get_member/material"))

(def actionlog
  "can get some logs"
  (create-api-call "/api_get_member/actionlog"))

(def deck-port
  "can get information about fleets in the dock"
  (create-api-call "/api_get_member/deck_port"))

(def deck
  "no difference between deck-port? can get information about fleets in the dock. need this to end battle?"
  (create-api-call "/api_get_member/deck"))

(def slotitem
  "can get information about items you have"
  (create-api-call "/api_get_member/slotitem"))

(def logincheck
  "check if you are still logged in and get material if ready"
  (create-api-call "/api_auth_member/logincheck"))

(def speedchange
  ""
  (create-api-call "/api_req_nyukyo/speedchange"))

(def nyukyo-start
  "recovery ship.
  examples:
  (nyukyo-start :api_ship_id 550 :api_ndock_id 1 :api_highspeed 0)
  "
  (create-api-call "/api_req_nyukyo/start"))

(def charge
  "charge ship.
  examples:
  (charge :api_kind 3 :api_id_items \"219,61,27,531,491,290\")
  "
  (create-api-call "/api_req_hokyu/charge"))

(def battle-start 
  "let fleet go to battle.
  examples:
  (battle-start :api_formation_id 1 :api_deck_id 1 :api_mapinfo_no 2 :api_maparea_id 3)
  "
  (create-api-call "/api_req_map/start"))

(def battle
  "battle with given formation. 
  examples:
  (battle :battle :api_formation 1)
  "
  (create-api-call "/api_req_sortie/battle"))

(def battle-result
  "get information of battle result"
  (create-api-call "/api_req_sortie/battleresult"))

(def mission-start
  "start mission.
  examples:
  (mission-start :api_deck_id 2 :api_mission_id 2)
  "
  (create-api-call "/api_req_mission/start"))

(def mission-result
  "get information of mission result
  examples:
  (mission-result :api_deck_id 3)
  "
  (create-api-call "/api_req_mission/result"))

(def clearitemget
  "clear quest and get items.
  examples:
  (clearitemget :api_quest_id 504)
  "
  (create-api-call "/api_req_quest/clearitemget"))

(comment
  "you have to pay to use api below"
  (def open-new-dock (create-api-call "/api_req_nyukyo/open_new_dock"))
  (def open-new-kousyou (create-api-call "/api_req_kousyou/open_new_dock"))
  )
