(ns app.main.core
  (:require ["electron" :as electron :refer [app BrowserWindow]]))

(def main-window (atom nil))
(def electron (js/require "electron"))

(defn init-browser []
  (def screen (.getPrimaryDisplay electron.screen))
  (reset! main-window (BrowserWindow. screen.size))

  
  ; Path is relative to the compiled js file (main.js in our case)
  ;; (.loadURL @main-window (str "file://" js/__dirname "/public/index.html"))
  (.on @main-window "closed" #(reset! main-window nil)))

(defn main []
  (.on app "window-all-closed" #(when-not (= js/process.platform "darwin")
                                  (.quit app)))
  (.on app "ready" init-browser))
