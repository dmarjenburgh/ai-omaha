(ns ai-omaha.strategy
  (:require [ai-omaha.state :as state]))

(def actions #{:fold :check :call :raise})
(defn call [] [:call 0])
(defn fold [] [:fold 0])
(defn check [] [:check 0])
(defn raise [amount] [:raise amount])

;; The strategy function is called with the time left in the timeBank
;; It must return the move as a vector [action value]
;; action must be :fold, :check :call or :raise and value is an integer
(defn default-strategy [t]
  {:post [(actions (first %)) (integer? (second %))]}
  (let [call-amount (state/amount-to-call)]
    (cond
      (zero? call-amount) (check)
      (> call-amount (* 0.25 (state/my-stack))) (fold)
      :else (call))))
