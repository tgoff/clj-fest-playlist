(defproject clj-fest-playlist "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-spotify "0.1.10"]
                 ]
  :main ^:skip-aot clj-fest-playlist.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
