(ns ai-omaha.cards
  (:require [clojure.string :as s]))

;; A rank is a number from 1 to 13 for quick rank comparison (1 = ace, 13 = King)
;; A suit is a character in #{\d \s \h \c}
;; A card is represented as a vector [rank suit]
;; A hand is a set of cards

(defn make-card [rank suit]
  [rank suit])

(defn rank [card] (card 0))
(defn suit [card] (card 1))

;; Parsing
(def map-rank (zipmap (concat (range 2 10) [\T \J \Q \K \A]) (conj (vec (range 2 14)) 1)))
(def cards-map
  (into {} (for [r map-rank s [\d \c \h \s]]
             [(str (key r) s) (make-card (val r) s)])))
(def deck (set (vals cards-map)))

(defn parse-card
  "Parses a card representation like `Td` from the engine"
  [card-string]
  (cards-map card-string))

(defn parse-cards
  "Parses a collection of cards like [3d,8s,Jh]"
  [cards-string]
  (set (map parse-card (-> cards-string
                           (subs 1 (dec (count cards-string)))
                           (s/split #",")))))
