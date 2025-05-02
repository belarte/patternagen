(ns patterns
  (:require [clojure.string :as str]))

(def patterns [{:pattern "RllK"}
               {:pattern "RLKK"}])

(defn valid-pattern? [pattern]
  (boolean (some #(= pattern (:pattern %)) patterns)))

(defn valid-patterns? [patterns]
  (every? valid-pattern? patterns))

(def strokes {\R :right-accent
              \L :left-accent
              \l :left-ghost
              \K :kick})

(defn- prepare
  "Transforms the `input` string into a valid list of patterns."
  [input]
  {:pre  [(string? input)]
   :post [(valid-patterns? %)]}
  (-> input
      (str/replace #"\s" "")
      (str/split #",")))

(defn- pattern-to-strokes [pattern]
  (map #(strokes %) pattern))

(defn- patterns-to-strokes [patterns]
  (->> (prepare patterns)
       (map pattern-to-strokes)))

(defn ls [] (prn patterns))

(defn generate [& args]
  (prn (patterns-to-strokes (first args))))

(comment
  (valid-patterns? ["RllK" "RLK"])
  (pattern-to-strokes "RllK")
  (prepare "RllK, RLKK ")
  (let [args "RllK,RllK,RLKK,RLKK"]
    (patterns-to-strokes args)))
