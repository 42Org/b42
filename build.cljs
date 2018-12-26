(require '[cljs.build.api :as b])

(defn build-renderer [opt]
  (b/build
   (b/inputs "src")
   {:main 'app.renderer.core
    :output-to "app/renderer.js"
    :output-dir "out/"
    :optimizations opt
    :target :nodejs
    :infer-externs true})
  (println "renderer compiled." "Optimizations: " opt))

(defn build-main [opt]
 (b/build
  (b/inputs "src")
  {:main 'app.main.core
   :output-to "app/main.js"
   :output-dir "out/"
   :optimizations opt
   :target :nodejs
   :infer-externs true})
  (println "main ns compiled." "Optimizations: " opt))

(defn build [opt]
  (build-main opt)
  (build-renderer opt))

(defn watch []
  (future (b/watch
           (b/inputs "src")
           {:main 'app.main.core
            :target :nodejs
            :verbose true
            :optimizations :simple
            :output-to "app/main.js"
            :output-dir "out/"}))
  (b/watch
           (b/inputs "src")
           {:main 'app.renderer.core
            :target :browser
            :verbose true
            :optimizations :simple
            :output-to "app/renderer.js"
            :output-dir "out/"}))

(defn main [cli-arg]
  (let [args cli-arg
        command (last args)]
    (println "Build Type: " command)
    (case command
      "release" (build :simple)
      "watch" (watch)
      (build :none))))

(main *command-line-args*)
