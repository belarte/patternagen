(ns patterns
  (:require [clojure.string :as str]))

(def patterns [{:pattern "RllK"}
               {:pattern "RLKK"}])

(def strokes {\R :right-accent
              \L :left-accent
              \l :left-ghost
              \K :kick})

(defn ls [] (prn patterns))

(defn- prepare
  "Transforms the `input` string into a list of patterns."
  [input]
  {:pre [(string? input)]}
  (-> input
      (str/replace #"\s" "")
      (str/split #",")))

(defn- pattern-to-strokes [pattern]
  (map #(strokes %) pattern))

(defn- patterns-to-strokes [patterns]
  (->> (prepare patterns)
       (map pattern-to-strokes)))

(defn generate [& args]
  (prn (patterns-to-strokes (first args))))

(comment
  (pattern-to-strokes "RllK")
  (prepare "RllK, RLKK ")
  (let [args "RllK,RllK,RLKK,RLKK"]
    (patterns-to-strokes args)))
