(ns ai-omaha.state)

(def settings (atom {"timeBank" 0
                     "timePerMove" 0
                     "handsPerLevel" 0
                     "startingStack" 0
                     "yourBot" nil}))

(def match (atom {"round" 0
                  "smallBlind" 0
                  "bigBlind" 0
                  "onButton" nil
                  "table" []
                  "maxWinPot" 0
                  "amountToCall" 0}))

(def bots (atom {"player1" {}
                 "player2" {}}))

(defn my-name [] (get @settings "yourBot"))
(defn amount-to-call []
  (get @match "amountToCall"))
(defn my-stack []
  (get-in @bots [(my-name) "stack"]))
