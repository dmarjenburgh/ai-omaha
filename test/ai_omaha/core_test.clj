(ns ai-omaha.core-test
  (:require [ai-omaha.core :refer :all]
            [ai-omaha.state :as state]
            [midje.sweet :refer :all]))

(facts "abouts state `Settings`"
  (fact "we can update settings"
    (with-state-changes [(before :facts (reset! state/settings {}))]
      (fact "timeBank"
        (handle-input
          ["Settings" "timeBank" "5000"]) => (contains {"timeBank" 5000}))
      (fact "yourBot name"
        (handle-input
          ["Settings" "yourBot" "player2"]) => (contains {"yourBot" "player2"})))))

(facts "about state `Match`"
  (fact "we can update match info"
    (with-state-changes [(before :facts (reset! state/match {}))]
      (fact "round"
        (handle-input ["Match" "round" "2"])  => (contains {"round" 2}))
      (fact "onButton"
        (handle-input
          ["Match" "onButton" "player1"]) => (contains {"onButton" "player1"}))
      (fact "table"
        (handle-input ["Match" "table" "[3s,7c,Th,Ad]"])
        => (contains {"table" #{[3 \s] [7 \c] [10 \h] [1 \d]}})))))

(facts "about bot moves"
  (fact "we can store the state of the players"
    (with-state-changes [(before :facts (reset! state/bots {"player1" {} "player2" {}}))]
      (fact "stack and post"
        (handle-input ["player1" "stack" "1500"]) => (contains {"player1" {"stack" 1500}})
        (handle-input ["player1" "post" "100"]) => (contains {"player1" {"stack" 1400}}))
      (fact "hand"
        (handle-input
          ["player2" "hand" "[2d,Ts,8s,7h]"]) => (contains {"player2"
                                                            {"hand" #{[2 \d] [10 \s]
                                                                      [8 \s] [7 \h]}}})))))

(fact "the default strategy returns `call 0`"
  (with-state-changes [(before :facts [(reset! state/settings {"yourBot" "player1"})
                                       (reset! state/match {"amountToCall" 100})
                                       (reset! state/bots {"player1" {"stack" 1300}})])]
    (fact
      (with-out-str
        (handle-input ["Action" "player1" "3000"])) => "call 0\n")))
