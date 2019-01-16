(ns app.renderer.init
  (:require [app.renderer.io :as io]
            [cljs.tools.reader :as reader]))

(def load-msg "(println \"B42 CLJS-Runtime loaded...\")")

(defn load-init-scripts []
  (let [user-init (io/load-file "~/.b42/init.cljs")]
    (reader/read-string user-init)))
