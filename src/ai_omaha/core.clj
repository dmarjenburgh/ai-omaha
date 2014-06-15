(ns ai-omaha.core
  (:require [ai-omaha.cards :as cards]
            [ai-omaha.state :as state]
            [ai-omaha.strategy :as strat]
            [clojure.string :as str]
            [clojure.stacktrace :refer [print-stack-trace]])
  (:gen-class))

(def ^:dynamic *current-strategy*)
;; Set default strategy implementation. Change this to your own
(alter-var-root #'*current-strategy* (fn [_] strat/default-strategy))

(defn handle-settings [[k v]]
  (if (= k "yourBot")
    (swap! state/settings assoc k v)
    (swap! state/settings assoc k (Long. v))))

(defn handle-match [[k v]]
  (case k
    "onButton" (swap! state/match assoc k v)
    "table" (swap! state/match assoc k (cards/parse-cards v))
    (swap! state/match assoc k (Long. v))))

(defn handle-action [[b t]]
  (when (= b (get @state/settings "yourBot"))
    (let [[act v] (*current-strategy* (Long. t))]
      (println (str (name act) " " v)))))

(defn handle-bot-move [[b m v]]
  (case m
    "hand" (swap! state/bots assoc-in [b m] (cards/parse-cards v))
    "post" (swap! state/bots update-in [b "stack"] - (Long. v))
    "wins" (swap! state/bots update-in [b "stack"] + (Long. v))
    (swap! state/bots assoc-in [b m] (Long. v))))

(defn handle-input [words]
  (case (first words)
    "Settings" (handle-settings (subvec words 1))
    "Match" (handle-match (subvec words 1))
    "Action" (handle-action (subvec words 1))
    ("player1" "player2") (handle-bot-move words)))

(defn run []
  (while true
    (let [command (read-line)]
      (try
        (handle-input (str/split command #" "))
        (catch Exception e
          (binding [*out* *err*]
            (println "Error processing command: " command)
            (println (print-stack-trace e))))))))

(defn -main [& args]
  (run))
