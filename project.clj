(defproject tramboard-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"

  :dependencies [[cljs-uuid "0.0.4"]
                 [clj-time "0.9.0"]
                 [compojure "1.3.4"]
                 [com.andrewmcveigh/cljs-time "0.3.0"]
                 [cheshire "5.4.0"]
                 [environ "1.0.0"]
                 [hiccup "1.0.0"]
                 [http-kit "2.1.18"]
                 [org.clojure/clojure "1.7.0-beta3"]
                 [org.clojure/clojurescript "0.0-3269"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.omcljs/om "0.8.8"]
                 [ring/ring-defaults "0.1.2"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [ring/ring-mock "0.2.0"]
                 [ring/ring-codec "1.0.0"]
                 [ring/ring-json "0.3.1"]
                 [bk/ring-gzip "0.1.1"]
                 [secretary "1.2.3"]
                 [org.clojars.jws/ring-etag-middleware "0.1.2-SNAPSHOT"]
                 [com.newrelic.agent.java/newrelic-api "3.13.0"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-shell "0.4.0"]]

  :uberjar-name "tramboard-clj.jar"
  :source-paths ["src/clj"]
  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]}}}

  :profiles {:dev {:source-paths ["env/dev/src/clj"]
                   :dependencies [[figwheel-sidecar "0.3.3"]
                                  [figwheel "0.3.3"]
                                  [midje "1.6.3"]]
                   :plugins [[lein-figwheel "0.3.3"]
                             [lein-ring "0.8.13"]
                             [lein-deps-tree "0.1.2"]
                             [lein-pdo "0.1.1"]
                             [lein-midje "3.1.3"]]
                   :aliases {"autotest" ["midje" ":autotest"]}
                   :figwheel {:css-dirs ["resources/public/css"]}
                   :ring {:handler tramboard-clj.core.handler/app}
                   :cljsbuild {:builds {:app {:source-paths ["env/dev/src/cljs"]
                                              :compiler {:output-to "resources/public/js/dev.js"
                                                         :output-dir "resources/public/out"
                                                         :optimizations :none
                                                         :pretty-print true
                                                         :source-map "resources/public/out.js.map"}}}}}

             :uberjar {:source-paths ["env/prod/src/clj"]
                       :omit-source true
                       :prep-tasks [["shell" "node_modules/.bin/gulp" "less"] "javac" "compile"]
                       :aot :all
                       :hooks [leiningen.cljsbuild]
                       :cljsbuild {:builds {:app {:source-paths ["env/prod/src/cljs"]
                                                  :compiler {:output-to "resources/public/js/main.js"
                                                             :optimizations :advanced
                                                             :pretty-print false}}}}}}
  :aliases {"develop"   ["pdo" "figwheel" ["gulp"]]
            "gulp" ["do" ["shell" "node_modules/.bin/gulp" "default"]]})
