(ns benkoder.encode)

(declare bencode)

(defn- bencode-int
  "Bencode an integer into it's bencode representation. e.g.: 1 -> i1e"
  [x]
  (str "i" x "e"))

(defn- bencode-string
  "Bencode a string into it's bencode representation. e.g.: test -> 4:test"
  [x]
  (str (count x) ":" x))

(defn- bencode-keyword
  "Bencode a keyword into it's bencode representation. e.g.: :some-key -> 8:some-key"
  [x]
  (bencode-string (name x)))

(defn- bencode-seq
  "Bencode a sequence into it's bencode representation. e.g.: [test 3 198] -> l4:testi3ei198ee"
  [x]
  (fn [] (str "l" (apply str (apply concat (map bencode x))) "e")))

(defn- bencode-map
  "Bencode a map into it's bencode representation. e.g.: {:test 12 :again 293} -> d4:testi12e5:againi293ee"
  [x]
  (fn []
    (str "d"
       (apply str
              (apply concat
                     (for [[k v] x] [(bencode k) (bencode v)])))
       "e")))

(defn bencode
  [input]
  (cond
   (integer? input) (bencode-int input)
   (string? input) (bencode-string input)
   (keyword? input) (bencode-keyword input)
   (seq? input) (trampoline bencode-seq input)
   (vector? input) (trampoline bencode-seq input)
   (map? input) (trampoline bencode-map input)
   ))
