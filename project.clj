(defproject my-exercise "0.1.0"
  :description "An anonymous Democracy Works coding exercise"
  :min-lein-version "2.7.1"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [ring/ring-json "0.4.0"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojurescript "1.9.946"]
                 [reagent "0.7.0"]
                 [reagent-utils "0.2.1"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.2.3"]
                 [cljs-ajax "0.7.3"]]

  :plugins [[lein-ring "0.12.1"]
            [lein-cljsbuild "1.1.7"]]

  :ring {:handler my-exercise.core/handler}

  :aliases {"submit" ["run" "-m" "my-exercise.submit"]}

  :clean-targets ^{:protect false} [:target-path
                                    [:cljsbuild :builds :app :compiler :output-dir]
                                    [:cljsbuild :builds :app :compiler :output-to]]

  :source-paths ["src/clj"]
  :resource-paths ["resources" "target/cljsbuild"]

  :cljsbuild {:builds {:dev {:source-paths ["src/cljs"]
                             :compiler {:output-to "target/cljsbuild/public/js/app.js"
                                        :output-dir "target/cljsbuild/public/js/out"
                                        :asset-path "js/out"
                                        :main "my-exercise.core"
                                        :source-map true
                                        :optimizations :none
                                        :pretty-print true}}
                       :prod {:source-paths ["src/cljs"]
                              :compiler {:output-to "target/cljsbuild/public/js/app.js"
                                         :asset-path "js/out"
                                         :main "my-exercise.core"
                                         :source-map false
                                         :optimizations :advanced
                                         :pretty-print true}}}})
