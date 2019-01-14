(ns app.main.core
  (:require [electron :as electron]
            [app.main.cli :as cli]))

(enable-console-print!)

(def main-window (atom nil))
(defonce app electron/app)
(defonce argv (subvec (js->clj js/process.argv) 2))
(defonce ipc-main electron/ipcMain)

(defn init-browser [size]
  (cli/start-msg)
  (let [web-pref #js{:webPreferences #js{:nodeIntegration true}
                     :width size.width :height size.height}]

    (reset! main-window (new electron/BrowserWindow web-pref))
    (.loadURL @main-window "http://localhost:3742")
    ;;Use doto foe .on invocation on main window
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
  ;; (.log js/console electron)
  (.on app "ready" start))
