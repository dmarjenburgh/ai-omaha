(ns ai-omaha.poker-test
  (:require [ai-omaha.cards :refer [make-card cards-map deck]]
            [ai-omaha.poker :refer :all]
            [criterium.core :refer [quick-bench]]
            [clojure.math.combinatorics :refer :all]))

(def ranks (vec (concat (range 2 10) [\T \J \Q \K \A])))
(def suits [\d \c \h \s])

(defn rand-card [] (make-card (rand-nth ranks) (rand-nth suits)))
(defn rand-cards [n]
  (loop [n n deck deck cards #{}]
    (if (pos? n)
      (let [c (rand-nth deck)]
        (recur (dec n) (remove #{c} deck) (conj cards c)))
      cards)))

(def all-hands (combinations deck 5))

(def straight-flush-hand #{[10 \d] [11 \d] [12 \d] [13 \d] [1 \d]})
