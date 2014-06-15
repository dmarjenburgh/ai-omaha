(ns ai-omaha.scenario
  (:require [ai-omaha.core :refer :all]
            [ai-omaha.state :as state]
            [clojure.string :as str]
            [midje.sweet :refer :all]))

(def commands
  ["Settings timeBank 5000"
   "Settings timePerMove 500"
   "Settings handsPerLevel 10"
   "Settings startingStack 1500"
   "Settings yourBot player1"
   "Match round 1"
   "Match smallBlind 15"
   "Match bigBlind 30"
   "Match onButton player1"
   "player1 stack 1500"
   "player2 stack 1500"
   "player1 post 15"
   "player2 post 30"
   "player1 hand [2d,Ts,8s,7h]"
   "Match maxWinPot 45"
   "Match amountToCall 15"
   "Action player1 5000"])

(defn run-once []
  (try
    (let [command (read-line)]
      (handle-input (str/split command #" ")))
    (catch Exception e
      (.getMessage e))))

(defn run-scenario [commands]
  (loop [commands commands]
    (when (seq commands)
      (with-in-str (first commands) (run-once))
      (println @state/bots)
      (recur (next commands)))))

(comment
  (run-scenario commands)

  )
