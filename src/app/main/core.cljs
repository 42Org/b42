(ns app.main.core
  (:require [cljs.js :refer [empty-state eval js-eval]]
            [cljs.tools.reader :refer [read-string]]
            [cljs.env :refer [*compiler*]]
            [cljs.pprint :refer [pprint]]))

(enable-console-print!)

(def electron (js/require "electron"))
(def main-window (atom nil))
(def app (aget electron "app"))


(defn eval-exp [exp]
  (eval (empty-state) (read-string exp)
        {:eval       js-eval
         :source-map true
         :context    :expr} #(%1)))

(defn init-browser []
  (let [screen (.getPrimaryDisplay electron.screen)
        size screen.size]
    (reset! main-window (electron.BrowserWindow. size))
    (.loadURL @main-window (str "file://" js/__dirname "/index.html"))
    (.on @main-window "closed" #(reset! main-window nil))))

(defn main []
  (.log js/console "Starting B42")
  (.log js/console electron)
  (.on app "ready" init-browser))

(main)
