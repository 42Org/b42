(ns app.main.core)

(enable-console-print!)

(def electron (js/require "electron"))
(def main-window (atom nil))
(def app (aget electron "app"))

(defn init-browser []
  (let [screen (.getPrimaryDisplay electron.screen)]
    (reset! main-window (electron.BrowserWindow. screen.size))
    (.loadURL @main-window (str "file://" js/__dirname "/index.html"))
    (.on @main-window "closed" #(reset! main-window nil))))

(defn main []
  (.log js/console "Starting b42")
  (.on app "ready" init-browser)
  (.log js/console electron))

(main)
