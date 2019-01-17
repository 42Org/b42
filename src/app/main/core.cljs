(ns app.main.core
  (:require [electron :as electron]
            [app.main.cli :as cli]
            [app.main.io :as io]))

(enable-console-print!)

(def main-window (atom nil))
(defonce app electron/app)
(defonce argv (subvec (js->clj js/process.argv) 2))
(defonce ipc-main electron/ipcMain)

(defn init-browser [size]
  (cli/start-msg)
  (let [web-pref (merge
                  (js->clj size)
                  {:title "B42" :webPreferences {:nodeIntegration true}})]

    (reset! main-window (new electron/BrowserWindow (clj->js web-pref)))
    (.loadURL @main-window "http://localhost:3742")
    (.on @main-window "closed" #(reset! main-window nil))))

(defn start-default-window []
  (let[screen (.getPrimaryDisplay electron/screen)
       size screen.size]
    (init-browser size)))

(defn start []
  (cond
    (empty? argv) (start-default-window)
    :else (cli/parse-args argv)))

(defn main []
  (.on app "ready" start)
  (.on ipc-main "load-file" (fn[event path] (io/read-file event path))))
