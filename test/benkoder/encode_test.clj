(ns benkoder.encode-test
  (:require [clojure.test :refer :all]
            [benkoder.encode :refer [bencode]]))

(deftest test-string
  (testing "bencoding strings."
    (is (= (bencode "test") "4:test"))))

(deftest test-integer
  (testing "bencoding integers."
    (is (= (bencode -4532) "i-4532e"))
    (is (= (bencode 4532) "i4532e"))))


(deftest test-list
  (testing "bencoding lists."
    (is (= (bencode [1 -42 "test" 1935 "dd" {:lvl1 ["a" "b" 3 42] :l2 2} [55 66]])
           "li1ei-42e4:testi1935e2:ddd4:lvl1l1:a1:bi3ei42ee2:l2i2eeli55ei66eee"))))

(deftest test-dict
  (testing "bencoding dicts."
    (is (= (bencode {:a 11 :b "22" :c {:cc1 {:ccc1 "a"} :cc2 {:ccc2 "b"}} :d [1 2 3]})
           "d1:cd3:cc1d4:ccc11:ae3:cc2d4:ccc21:bee1:b2:221:dli1ei2ei3ee1:ai11ee"))))
