(ns benkoder.decode-test
  (:require [clojure.test :refer :all]
            [benkoder.decode :refer [bdecode]]))

(deftest test-string
  (testing "bdecoding strings."
    (is (= (bdecode "4:test") "test"))))

(deftest test-bad-string
  (testing "bdecoding bad strings."
    (is (= (bdecode "2") ""))
    (is (= (bdecode "2:") ""))
    (is (= (bdecode "6:test") "test"))
    (is (= (bdecode "4:testing") "test"))
    (is (= (bdecode "5:test") "test"))
    (is (= (bdecode "-2:test") nil))
    (is (= (bdecode "a:test") nil))))

(deftest test-integer
  (testing "bdecoding integers."
    (is (= (bdecode "i174e") 174))))

(deftest test-bad-integer
  (testing "bdecoding bad integers."
    (is (= (bdecode "iae") nil))
    (is (= (bdecode "i33") 33))
    (is (= (bdecode "i-3e") -3))))

(deftest test-list
  (testing "bdecoding lists."
    (is (= (bdecode "l3:abc4:testi3562e7:awesomee") ["abc" "test" 3562 "awesome"]))))

(deftest test-nested-list
  (testing "bdecoding nested lists."
    (is (= (bdecode "l1:al2:bb2:ccl3:ddd3:eeeeee") ["a" ["bb" "cc" ["ddd" "eee"]]]))))

(deftest test-bad-list
  (testing "bdecoding bad lists."
    (is (= (bdecode "l") []))
    (is (= (bdecode "lle") [[]]))
    (is (= (bdecode "l11e") [""]))))

(deftest test-dict
  (testing "bdecoding dicts."
    (is (= (bdecode "d3:cow3:mooi1ele4:spam4:eggse") {:cow "moo" :1 [] :spam "eggs"}))))

(deftest test-nested-dict
  (testing "bdecoding nested dicts."
    (is (= (bdecode "d1:ad2:bbd3:cccd4:ddddd5:eeeeei5ee2:d4i4ee2:c3i3ee2:b2i2ee2:a1i1ee")
           {:a {:bb {:ccc {:dddd {:eeeee 5} :d4 4} :c3 3} :b2 2} :a1 1}))))

(deftest test-bad-dict
  (testing "bdecoding bad dicts."
    (is (= (bdecode "dia3l") {(keyword (str nil)) nil}))
    (is (= (bdecode "dlde") {(keyword (str [{}])) nil}))
    (is (= (bdecode "d1de") {:e nil}))
    (is (= (bdecode "dde") {(keyword (str {})) nil}))
    (is (= (bdecode "d") {}))))

(deftest test-intertwined-types
  (testing "bdecoding nested intertwined types."
    (is (= (bdecode "d1:ald3:aa1i2e3:aa24:testee1:bd3:bb1l3:bbbd4:bbb1i111eei3ee3:bb2i222ee1:cl3:ccci33ed3:cc1d4:ccc13:111e3:cc2d4:ccc2l1:ai1e1:bi2eeeee1:d1:11:ei1ee")
           {:a [{:aa1 2 :aa2 "test"}] :b {:bb1 ["bbb" {:bbb1 111} 3] :bb2 222} :c ["ccc" 33 {:cc1 {:ccc1 "111"} :cc2 {:ccc2 ["a" 1 "b" 2]}}] :d "1" :e 1}))))
