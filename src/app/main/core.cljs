(ns app.main.core
  (:require ["electron" :refer [app BrowserWindow]]))

(def main-window (atom nil))

(defn init-browser []
  (reset! main-window (BrowserWindow.
                        (clj->js {:width 800
                                  :height 600})))
  ; Path is relative to the compiled js file (main.js in our case)
  (.loadURL @main-window (str "file://" js/__dirname "/public/index.html"))
  (.on @main-window "closed" #(reset! main-window nil)))

(defn main []
  (.on app "window-all-closed" #(when-not (= js/process.platform "darwin")
                                  (.quit app)))
  (.on app "ready" init-browser))
