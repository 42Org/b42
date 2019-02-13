(ns app.common.bootstrap
  (:require [cljs.tools.reader :refer [read-string]]
            [cljs.js :refer [eval eval-str js-eval]]))

(defn eval-form [env exp load]
  (eval-str env
            (str exp)
            "[B42]"
            {:eval       js-eval
             :source-map true
             :verbose false
             :load (partial load env)
             :ns 'b42.init}
            identity))

(defn eval-string [env exp load]
  (eval env
        (read-string exp)
        {:eval       js-eval
         :source-map true
         :verbose false
         :load (partial load env)
         :ns 'b42.init}
        identity))

(defn init [boot env path cb]
  (boot env {:path path} cb))
