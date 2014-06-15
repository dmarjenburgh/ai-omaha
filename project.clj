(defproject ai-omaha "0.1.0"
  :description "Starter bot for Heads up Omaha on theaigames.com"
  :url "http://theaigames.com"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/math.combinatorics "0.0.7"]]
  :main ai-omaha.core
  :profiles {:dev {:dependencies [[midje "1.6.3"]
                                  [criterium "0.4.3"]]}
             :uberjar {:aot :all}})
