(ns benkoder.utils)

(defn is-digit? [s]
  (try
    (Character/isDigit s)
    (catch IllegalArgumentException e false)))

(defn parse-int [s]
  (try
    (Integer/parseInt s)
    (catch NumberFormatException e nil)))

(defn number-from-start [s]
  (re-find #"\A\d+" s))
