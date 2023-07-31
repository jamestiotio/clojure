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

(deftest properly-formed-method-descriptor
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
         (take 3 (repeatedly Tuple/create-0))))
  (is (= '([1] [2] [3])
         (map Tuple/create-1 [1 2 3])))
  (is (= '([1 4] [2 5] [3 6])
         (map Tuple/create-2 [1 2 3] [4 5 6])))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-482"))))

(deftest method-signature-selection
  (is (= [1.23 3.14]
         (map Math/abs-double [1.23 -3.14])))
  (is (= [(float 1.23)  (float 3.14)]
         (map Math/abs-float [1.23 -3.14])))
  (is (= [1 2 3]
         (map Math/abs-long [1 2 -3])))
  (is (= [#uuid "00000000-0000-0001-0000-000000000002"]
         (map UUID/new-long-long [1] [2])))
  (is (= '("a" "12")
         (map String/valueOf-Object ["a" 12])))
  (is (= ["A" "B" "C"]
         (map String/toUpperCase-java.util.Locale ["a" "b" "c"] (repeat java.util.Locale/ENGLISH))))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor Math "abs-1")))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-long")))
  (is (thrown? ClassCastException
               (doall (map String/valueOf-long [12 "a"])))))

(def mt Tuple/create-1)
(def mts {:fromString UUID/fromString})

(deftest method-thunks-in-structs
  (is (= #uuid "00000000-0000-0001-0000-000000000002"
         ((:fromString mts) "00000000-0000-0001-0000-000000000002")))
  (is (= [1] (mt 1))))

(deftest primitive-hinting
  (is (instance? clojure.lang.IFn$DO String/valueOf-double))
  (is (instance? clojure.lang.IFn$LL Math/abs-long)))