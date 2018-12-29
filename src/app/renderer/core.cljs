(ns app.renderer.core
  (:require [reagent.core :as r]
            [cljs.tools.reader :refer [read-string]]
            [cljs.js :refer [empty-state eval-str js-eval]]
            [cljs.env :as env]
            [cljs.pprint :refer [pprint]]
            [shadow.cljs.bootstrap.browser :as boot]))

(enable-console-print!)

(defonce default-env (env/default-compiler-env))

(defn eval-exp [exp]
  (eval-str default-env
            "(println (+ 1 2 3))"
            "[B42]"
            {:eval       js-eval
             :source-map true
             :load (partial boot/load default-env)}
            identity))

(defn clj-code []
  [:p {:id "input"} "(+ 1 2 3)"])

(defn evaluate []
  (let [exp (.-innerHTML (.getElementById js/document "input"))
        result (eval-exp exp)]
    (with-out-str result)))

(defn mount-root [] 
  (r/render [clj-code] (.getElementById js/document "app")))

(defn init []
  (boot/init default-env {:path "/bootstrap"} eval-exp))

(defn start []
  (mount-root)
  (evaluate))

(defn stop [])
