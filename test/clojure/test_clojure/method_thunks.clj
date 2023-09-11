;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.
; Authors: Fogus

(ns clojure.test-clojure.method-thunks
  (:use clojure.test)
  (:import (clojure.lang Compiler Tuple)
           (java.util Arrays UUID Locale)
           clojure.lang.IFn$LL))

(set! *warn-on-reflection* true)

#_(deftest properly-formed-method-descriptor
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "notAMethodThatExists")))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-2-Locale")))
  (is (thrown? UnsupportedOperationException
               (Compiler/maybeProcessMethodDescriptor Arrays "asList")))
  (is (thrown? ClassNotFoundException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-noprim")))
  (is (thrown? ClassNotFoundException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-Instant"))))

(deftest method-arity-selection
  (is (= '([] [] [])
         (take 3 (repeatedly Tuple/create))))
  (is (= '([] [] [])
         (take 3 (repeatedly ^[] Tuple/create))))
  (is (= '([1] [2] [3])
         (map ^[_] Tuple/create [1 2 3])))
  (is (= '([1 4] [2 5] [3 6])
         (map ^[_ _] Tuple/create [1 2 3] [4 5 6]))))

(deftest method-signature-selection
  (is (= [1.23 3.14]
         (map ^[double] Math/abs [1.23 -3.14])))
  (is (= [(float 1.23)  (float 3.14)]
         (map ^[float] Math/abs [1.23 -3.14])))
  (is (= [1 2 3]
         (map ^[long] Math/abs [1 2 -3])))
  (is (= [#uuid "00000000-0000-0001-0000-000000000002"]
         (map ^[long long] UUID. [1] [2])))
  (is (= '("a" "12")
         (map ^[Object] .String/valueOf ["a" 12])))
  (is (= ["A" "B" "C"]
         (map ^[java.util.Locale] .String/toUpperCase ["a" "b" "c"] (repeat java.util.Locale/ENGLISH))))
  (is (thrown? ClassCastException
               (doall (map ^[long] .String/valueOf [12 "a"])))))

(def mt ^[_] Tuple/create)
(def mts {:fromString UUID/fromString})

(deftest method-thunks-in-structs
  (is (= #uuid "00000000-0000-0001-0000-000000000002"
         ((:fromString mts) "00000000-0000-0001-0000-000000000002")))
  (is (= [1] (mt 1))))

(deftest primitive-hinting
  (is (instance? clojure.lang.IFn$DO ^[double] .String/valueOf))
  (is (instance? clojure.lang.IFn$LL ^[long] Math/abs)))
