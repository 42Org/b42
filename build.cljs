(require '[lumo.build.api :as b])


(defn dev-build []
 (b/build
  (b/inputs "src")
  {:main 'app.main.core
   :output-to "app/main.js"
   :output-dir "out/"
   :optimizations :none
   :target :nodejs
   :infer-externs true})
  (.log js/console "Dev Build compiled.."))

(defn release []
  (b/build
   (b/inputs "src")
   {:main 'app.main.core
    :output-to "app/main.js"
    :output-dir "out/"
    :optimizations :simple
    :target :nodejs
    :infer-externs true})
  (.log js/console "Release Build compiled"))

(defn -main []
  (let [args js/process.argv
        command (last args)]
    (.log js/console "Exec script: " command)
    (case command
      "release" (release)
      (dev-build))))

(-main)
