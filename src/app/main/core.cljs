(ns app.main.core
  (:require [cljs.pprint :refer [pprint]]))

(enable-console-print!)

(defonce electron (js/require "electron"))
(def main-window (atom nil))
(defonce app electron.app)

(defn init-browser []
  (let [screen (.getPrimaryDisplay electron.screen)
        size screen.size
        web-pref #js{:webPreferences #js{:nodeIntegration true}
                     :width size.width :height size.height}]

    (.log js/console electron.screen)
    (reset! main-window (electron.BrowserWindow. web-pref))
    (.loadURL @main-window "http://localhost:3742")
    (.on @main-window "closed" #(reset! main-window nil))))

(defn main []
  (.log js/console "Starting B42")
  (.log js/console electron)
  (.on app "ready" init-browser))
