;; Copyright © 2013, JUXT LTD. All Rights Reserved.
;;
;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file epl-v10.html at the root of this distribution.
;;
;; By using this software in any fashion, you are agreeing to be bound by the
;; terms of this license.
;;
;; You must not remove this notice, or any other, from this software.

(ns sudoku.jig
  (:require
   jig
   [sudoku :refer (sudoku-routes)])
  (:import (jig Lifecycle)))

(defrecord Website [name description uri puzzle] 
  Lifecycle
  (init [this system]
    (assoc this :handler (sudoku-routes puzzle)))
  (start [this system] this)
  (stop [this system] this))
