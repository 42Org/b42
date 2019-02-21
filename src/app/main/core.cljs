(ns app.main.core
  (:require [electron :as electron]
            [app.main.cli :as cli]
            [app.main.io :as io]
            [app.main.window :as window]
            [app.main.bootstrap :as bootstrap]))

(enable-console-print!)

(defonce app electron/app)
(defonce argv (subvec (js->clj js/process.argv) 2))
(defonce ipc-main electron/ipcMain)

(defn start []
  (cond
    (empty? argv) (window/start-default-window)
    :else (cli/parse-args argv)))

(defn main []
  (.on app "ready" start)
  (.on ipc-main "load-file" (fn[event path] (io/read-file event path))))
