(ns app.renderer.bootstrap
  (:require [cljs.tools.reader :refer [read-string]]
            [cljs.js :refer [empty-state eval eval-str js-eval]]
            [shadow.cljs.bootstrap.browser :as boot]
            [app.renderer.init :as init]))

(def default-env (empty-state))

(defn eval-form [exp]
  (eval-str default-env
            (str exp)
            "[B42]"
            {:eval       js-eval
             :source-map true
             :verbose false
             :load (partial boot/load default-env)}
            identity))

(defn eval-string [exp]
  (eval default-env
        (read-string exp)
        {:eval       js-eval
         :source-map true
         :verbose false
         :load (partial boot/load default-env)}
        identity))

(defn init []
  (boot/init default-env {:path "/bootstrap"}
             (fn[]
               ;;Load browser UI init scripts from this point.
               (eval-form init/load-msg)
               (eval-form (:data (init/load-init-scripts))))))
