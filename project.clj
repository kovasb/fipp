(defproject kovasb/fipp "0.5.3-SNAPSHOT"
  :description "Fast Idiomatic Pretty Printer for Clojure"
  :url "https://github.com/kovasb/fipp"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 ;[org.clojure/data.finger-tree "0.0.2"]
                 [org.clojure/core.rrb-vector "0.0.11"]
                 [transduce "0.1.2-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "1.0.5"]]
  :cljsbuild {
              :builds [{
                        ; The path to the top-level ClojureScript source directory:
                        :source-paths ["src"]
                        ; The standard ClojureScript compiler options:
                        ; (See the ClojureScript compiler documentation for details.)
                        :compiler {

                                   :optimizations :whitespace
                                   :pretty-print true}}]})
