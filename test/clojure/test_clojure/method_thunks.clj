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
  (:import (clojure.lang Compiler)
           (java.util Arrays UUID Locale)))

(deftest properly-formed-method-descriptor
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "notAMethodThatExists")))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-2")))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-2-Locale")))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-noprim")))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-Instant")))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor Math "abs-1")))
  (is (thrown? IllegalArgumentException
               (Compiler/maybeProcessMethodDescriptor String "toUpperCase-UUID")))
  (is (thrown? UnsupportedOperationException
               (Compiler/maybeProcessMethodDescriptor Arrays "asList"))))

