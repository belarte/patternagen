(ns patterns
  (:require [clojure.string :as str]
            [babashka.process :refer [process shell]]))

(def patterns [{:pattern "RllK"}
               {:pattern "RLKK"}])

(defn valid-pattern? [pattern]
  (boolean (some #(= pattern (:pattern %)) patterns)))

(defn valid-patterns? [patterns]
  (every? valid-pattern? patterns))

(defn indent [line]
  (str (apply str (repeat 10 " ")) line))

(def strokes {\R (indent "sn16^\"R\"^\">\"")
              \L (indent "sn16^\"L\"^\">\"")
              \l (str (indent "\\once \\override Stem.font-size = -3\n")
                      (indent "\\once \\override NoteHead.font-size = -3\n")
                      (indent "\\parenthesize sn16^\"l\""))
              \K (indent "bd16^\"K\"")})

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
        header  (str (indent "%-- ") pattern)]
    (->> (conj strokes header)
         (str/join "\n"))))

(defn- patterns-to-strokes [patterns]
  (->> (prepare patterns)
       (map pattern-to-strokes)
       (str/join "\n\n")))

(defn ls [] (prn patterns))

(def file-top "\\version \"2.24.4\"

\\score {
  \\new DrumStaff {
    \\new DrumVoice {
      \\drummode {
        \\repeat volta 4 {
")

(def file-bottom "
        }
      }
    }
  }
}")

(defn lilypond [file-path]
  (let [cmd (process ["lilypond" "-o" "bin/output" file-path] {:inherit true})]
    @cmd))

(defn generate [& args]
  (spit "bin/output.ly" (str file-top (patterns-to-strokes (first args)) file-bottom))
  (lilypond "bin/output.ly")
  (shell "open" "bin/output.pdf"))

(comment
  (ls)
  (generate "RllK,RllK,RLKK,RLKK"))
