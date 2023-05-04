;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.test-clojure.java.stream
  (:use clojure.test)
  (:require [clojure.java.stream :as jstream])
  (:import [java.util.stream Stream]
           [java.util.function Supplier]))

(deftest stream-reduce-test
  (let [^Stream stream (.stream ["a" "b" "c" "d"])]
    (is (= ["a" "b" "c" "d"] (reduce conj [] stream))))

  (let [^Stream stream (.stream ["a" "b" "c" "d"])]
    (is (= "abcd" (reduce str stream))))

  (let [^Stream stream (.stream [])]
    (is (nil? (reduce str stream)))))

(deftest stream-seq!-test
  (let [one (Stream/of "a")
        n   (.stream ["a" "b" "c"])
	inf (Stream/generate (reify Supplier (get [_] 42)))
        st  (jstream/stream-seq! one)]
    (is (seq? st))
    (is (= ["a"] (map identity st)))
    (is (= ["a" "b" "c"] (map identity (jstream/stream-seq! n))))
    (is (= [42 42 42 42 42] (take 5 (jstream/stream-seq! inf))))))

(deftest stream-into!-test
  (let [one (Stream/of "a")
        n   (.stream ["a" "b" "c"])
        inf (Stream/generate (reify Supplier (get [_] 42)))]
    (is (= ["a"] (jstream/stream-into! [] one)))
    (is (= ["a" "b" "c"] (jstream/stream-into! [] n)))
    (is (= [] (jstream/stream-into! [] nil)))
    (is (= [42 42 42 42 42]
           (jstream/stream-into! [] (.limit inf 5))))))
