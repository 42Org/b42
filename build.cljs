(require '[cljs.build.api :as b])


(defn dev-build []
 (b/build
  (b/inputs "src")
  {:main 'app.main.core
   :output-to "app/main.js"
   :optimizations :none
   :target :nodejs
   :infer-externs true})
  (println "Dev Build compiled.."))

(defn release []
  (b/build
   (b/inputs "src")
   {:main 'app.main.core
    :output-to "app/main.js"
    :optimizations :simple
    :target :nodejs
    :infer-externs true})
  (println "Release Build compiled"))

(defn main [cli-arg]
  (let [args cli-arg
        command (last args)]
    (println "Build Type: " command)
    (case command
      "release" (release)
      (dev-build))))

(main *command-line-args*)
