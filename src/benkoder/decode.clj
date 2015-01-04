(ns benkoder.decode
  (:require [benkoder.utils :as utils]))

(declare bde-dispatch)

(defn- bdecode-int
  "Bdecode a bencoded integer into it's native representation. e.g.: i1e -> 1"
  [src]
  (let [str-integer (apply str (take-while #(not= % \e) (rest src)))]
    {:value (utils/parse-int str-integer) :length (+ 2 (count str-integer))}))

(defn- bdecode-str
  "Bdecode a bencoded string into it's native representation. e.g.: 4:test -> test"
  [src]
  (let [header (utils/number-from-start src)
        header-length (inc (count header))
        str-length (utils/parse-int header)
        decoded-value (apply str (take str-length (drop header-length src)))]
    {:value decoded-value :length (+ header-length str-length)}))

(defn- bdecode-list
  "Bdecode a bencoded sequence into it's native representation. e.g.: l4:testi3ei198ee -> [test 3 198]"
  [src]
  (loop [str-src (apply str (rest src))
         decoded-list []
         length 2]
    (let [item (bde-dispatch str-src)]
      (if (nil? item)
        {:value decoded-list :length length}
        (recur
         (apply str (drop (:length item) str-src))
         (conj decoded-list (:value item))
         (+ length (:length item)))))))

(defn- bdecode-dict
  "Bdecode a bencoded map into it's native representation. e.g.: d4:testi12e5:againi293ee -> {:test 12 :again 293}"
  [src]
  (loop [str-src (apply str (rest src))
         decoded-map {}
         length 2]
    (let [map-key (bde-dispatch str-src)]
      (if (nil? map-key)
        {:value decoded-map :length length}
        (let [new-src (apply str (drop (:length map-key) str-src))
              map-value (bde-dispatch new-src)]
          (if (nil? (:length map-value))
            (recur
             ""
             (assoc decoded-map (keyword (str (:value map-key))) (:value map-value))
             (+ length (:length map-key)))
            (recur
             (apply str (drop (:length map-value) new-src))
             (assoc decoded-map (keyword (str (:value map-key))) (:value map-value))
             (+ length (:length map-key) (:length map-value)))))))))

(defn- bde-dispatch [src]
  (cond
   (= (first src) \i) (bdecode-int src)
   (utils/is-digit? (first src)) (bdecode-str src)
   (= (first src) \l) (bdecode-list src)
   (= (first src) \d) (bdecode-dict src)
   (= (first src) \e) nil))

(defn bdecode [src]
  (:value (bde-dispatch src)))
