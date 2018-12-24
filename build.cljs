(require '[cljs.build.api :as b])

(defn build-renderer [opt]
  (b/build
   (b/inputs "src")
   {:main 'app.renderer.core
    :output-to "app/renderer.js"
    :optimizations opt
    :target :nodejs
    :infer-externs true})
  (println "renderer compiled." "Optimisations: " opt))

(defn build-main [opt]
 (b/build
  (b/inputs "src")
  {:main 'app.main.core
   :output-to "app/main.js"
   :optimizations opt
   :target :nodejs
   :infer-externs true})
  (println "main ns compiled." "Optimisations: " opt))

(defn build [opt]
  (build-main opt)
  (build-renderer opt))

(defn main [cli-arg]
  (let [args cli-arg
        command (last args)]
    (println "Build Type: " command)
    (case command
      "release" (build :simple)
      (build :none))))

(main *command-line-args*)
