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

(load-file (str (when-let [pwd (System/getProperty "leiningen.original.pwd")]
                  (str pwd "/"))
                "project-header.clj"))

(def pedestal-version "0.2.2")

(defproject jig (get-version)
  :description "A jig for developing systems using component composition. Based on Stuart Sierra's 'reloaded' workflow."
  :url "https://juxt.pro/jig"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 #_[org.clojure/clojurescript "0.0-2030"
                  :exclusions
                  ;; Conflicts with Riemanns protobuf dep
                  [com.google.protobuf/protobuf-java]]
                 ;; core.async
                 #_[org.clojure/core.async "0.1.256.0-1bf8cf-alpha"]
                 ;; Leiningen
                 [leiningen-core "2.3.2" :exclusions [org.clojure/tools.nrepl]]
                 ;; Tracing
                 [org.clojure/tools.trace "0.7.5"]
                 ;; Logging
                 [org.clojure/tools.logging "0.2.6"]
                 [ch.qos.logback/logback-classic "1.0.7" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.2"]
                 [org.slf4j/jcl-over-slf4j "1.7.2"]
                 [org.slf4j/log4j-over-slf4j "1.7.2"]
                 ;; Graph algorithms for dependency graphs
                 [jkkramer/loom "0.2.0"]
                 #_[io.pedestal/pedestal.service ~pedestal-version]
                 ;; (with jetty)
                 #_[io.pedestal/pedestal.jetty "0.1.10"]
                 ;; JMX
                 #_[org.clojure/java.jmx "0.2.0"]
                 ;; nREPL
                 #_[org.clojure/tools.nrepl "0.2.3"]
                 ;; Tools namespace
                 [org.clojure/tools.namespace "0.2.4"]
                 ;; Back, by popular demand, Ring!
                 #_[ring "1.2.0"]
                 #_[compojure "1.1.5"]
                 ;; MQTT for messaging
                 #_[clojurewerkz/machine_head "1.0.0-beta4"]
                 ;; Pedestal integration needs java.classpath to find ^:shared namespaces
                 #_[io.pedestal/pedestal.app-tools ~pedestal-version]
                 [org.clojure/java.classpath "0.2.0"]
                 ]

  :profiles {:dev {:resource-paths ["config"]}}

  :repl-options {:prompt (fn [ns] (str "Jig " ns "> "))
                 :welcome (user/welcome)
                 ;:nrepl-middleware [jig.nrepl/wrap-jig-loader]
                 }
  )
