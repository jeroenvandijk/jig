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

(ns jig.web.app
  (:require
   jig
   [clojure.pprint :refer (pprint)]
   #_[io.pedestal.service.interceptor :refer (definterceptorfn before around)]
   [clojure.tools.logging :refer :all])
  (:import (jig Lifecycle)))

(deftype Component [config]
  Lifecycle
  (init [_ system]
    (-> system
        (assoc-in
         [(:jig/id config) :jig.web/route-common]
          {:scheme (or (:jig/scheme config) :http)
           :host (:jig/hostname config)
           :app-name (:jig/id config)})
        (assoc-in [(:jig/id config) :jig.web/routes] [])
        (update-in [(:jig.web/server config) :app-names] conj (:jig/id config))))
  (start [_ system] system)
  (stop [_ system] system))

(comment
(definterceptorfn
  inject-component-config
  [config]
  (before
   (fn [context]
     (assoc context :component config))))

(definterceptorfn
  wrap-possible-context-classloader
  [cl]
  (around
   :wrap-possible-context-classloader
   (fn [context]
     (if cl
       (let [ocl (.getContextClassLoader (Thread/currentThread))]
         (.setContextClassLoader (Thread/currentThread) cl)
         (assoc context
           :old-context-loader ocl
           :context-loader cl))
       context))
   (fn [context]
     (when cl
       (.setContextClassLoader (Thread/currentThread) (get context :old-context-loader)))
     (dissoc context :context-loader :old-context-loader))))

(defn add-routes
  "A convenience function to contribute Pedestal routes to an
  application within the System. The app name must be specified in the
  config under the :jig.web/app-name key."
  [system config routes]
  (update-in system
             [(:jig.web/app-name config) :jig.web/routes]
             conj [(vec (concat
                         [(or (get-in config [:jig.web/context]) "/")
                          ^:interceptors [(inject-component-config config)
                                          (wrap-possible-context-classloader (some->> config :jig/project :classloader))]]
                         routes))]))
)