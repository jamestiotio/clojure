;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.java.stream
  "The lib stream includes functions for bridging the gap between Java streams
  and Clojure functions and collections."
  (:import [java.util.stream Stream BaseStream]))

(set! *warn-on-reflection* true)

(defn stream-seq!
  "Takes a java.util.stream.BaseStream instance s and returns a seq of its contents.
  This operation is a terminal operation for the stream given."
  [^BaseStream stream]
  (iterator-seq (.iterator stream)))

(defn stream-into!
  "Returns a new coll consisting of coll with all of the items of the
  java.util.stream.Stream stream conjoined. This operation is a terminal operation
  for the stream given."
  [coll ^Stream stream]
  (if (instance? clojure.lang.IEditableCollection coll)
    (with-meta (persistent! (reduce conj! (transient coll) stream)) (meta coll))
    (reduce conj coll stream)))
