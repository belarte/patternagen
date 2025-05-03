(ns patterns
  (:require [clojure.string :as str]))

(def patterns [{:pattern "RllK"}
               {:pattern "RLKK"}])

(defn valid-pattern? [pattern]
  (boolean (some #(= pattern (:pattern %)) patterns)))

(defn valid-patterns? [patterns]
  (every? valid-pattern? patterns))

(def strokes {\R "sn16^\"R\"^\">\""
              \L "sn16^\"L\"^\">\""
              \l (str "\\once \\override Stem.font-size = -3\n"
                      "\\once \\override NoteHead.font-size = -3\n"
                      "\\parenthesize sn16^\"l\"")
              \K "bd16^\"K\""})

(defn- prepare
  "Transforms the `input` string into a valid list of patterns."
  [input]
  {:pre  [(string? input)]
   :post [(valid-patterns? %)]}
  (-> input
      (str/replace #"\s" "")
      (str/split #",")))

(defn- pattern-to-strokes
  "Transform the input pattern into the corresponding strokes."
  [pattern]
  {:pre  [(valid-pattern? pattern)]
   :post [(string? %)]}
  (let [strokes (map #(strokes %) pattern)
        header  (str "%-- " pattern)]
    (->> (conj strokes header)
         (str/join "\n"))))

(defn- patterns-to-strokes [patterns]
  (->> (prepare patterns)
       (map pattern-to-strokes)
       (str/join "\n\n")))

(defn ls [] (prn patterns))

(defn generate [& args]
  (println (patterns-to-strokes (first args))))

(comment
  (valid-patterns? ["RllK" "RLK"])
  (pattern-to-strokes "RllK")
  (prepare "RllK, RLKK ")
  (let [input "RllK"]
    (->> input
         (pattern-to-strokes)
         (spit "test.ly")))
  (let [args "RllK,RllK,RLKK,RLKK"]
    (patterns-to-strokes args)))
