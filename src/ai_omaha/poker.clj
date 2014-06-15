(ns ai-omaha.poker
  "This namespace holds algorithms and utilities for computing probabilities
   and whatnot"
  (:require [ai-omaha.cards :as c]
            [clojure.math.combinatorics :refer :all]
            [clojure.set :as set]))

(def
  ^{:doc "Holds the possible hand-values in decreasing order of value"}
  hand-values
  [:straight-flush
   :four-of-a-kind
   :full-house
   :flush
   :straight
   :three-of-a-kind
   :two-pair
   :one-pair
   :high-card])

(defn pascal-row
  "Returns a lazy seq of the n+1 elements of pascals row"
  [n]
  (letfn [(next-col [prev k]
            (* prev (/ (- n (dec k)) k)))]
    (reductions next-col 1 (range 1 (inc n)))))

(def binomial
  (let [pascal-row (memoize pascal-row)]
    (fn [n k] (nth (pascal-row n) k))))

(defn card-combinations
  "Creates a lazy-seq of all sets containing 2 hand and 5 comm cards"
  [hole-cards comm-cards]
  (letfn [(to-set [[h c]]
            (set (set/union h c)))]
    (map to-set
       (cartesian-product
         (combinations hole-cards 2) (combinations comm-cards 3)))))

(defn evaluate
  "Takes a coll of 4 hole-cards and 5 community-cards and returns the hand value"
  [hole-cards comm-cards]
  (let [card-combs (card-combinations hole-cards comm-cards)]
    card-combs))

(defn straight-flush? [hand]
  (every? #{10 11 12 13 1} (map c/rank hand)))

