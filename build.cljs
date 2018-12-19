(require '[lumo.build.api :as b])

(b/build
 (b/inputs "src")
 {:main 'app.main.core
  :output-to "app/main.js"
  :output-dir "out/"
  :optimizations :none
  :target :nodejs
  :infer-externs true})
