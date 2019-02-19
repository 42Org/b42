(ns app.main.core
  (:require [electron :as electron]
            [app.main.cli :as cli]
            [app.main.io :as io]
            [app.main.keyboard :as kb]
            [cljs.tools.reader :refer [read-string]]
            [app.main.bootstrap :as bootstrap]))

(enable-console-print!)

(def main-window (atom nil))
(defonce app electron/app)
(defonce argv (subvec (js->clj js/process.argv) 2))
(defonce ipc-main electron/ipcMain)
(def global-config (atom {:ui-config [] :key-bindings []}))

;; fetches key bindings pre loaded in global-config :key-binding atom
(defn get-key-bindings []
  (@global-config :key-bindings))

;;Load config from some ~/.b42/init.cljs
(defn load-global-config
  ([] (load-global-config "~/.b42/b42/init.cljs"))
  ([config-file] (io/load-file config-file)))

(defn load-config []
  ;;call to initialize bootstrapping compiler.
    (bootstrap/boot-init
     (str '(println "CLJS bootstrapped ..."))
     (load-global-config)))

(defn init-browser-view []
  (let [web-pref #js{:title "B42" :webPreferences {:nodeIntegration true}
                     :icon "./icon.png"}]
    (new electron/BrowserView web-pref)))

(defn init-browser [size]
  (cli/start-msg)
  (let [web-pref size view (init-browser-view)]
    (reset! main-window (new electron/BrowserWindow (clj->js web-pref)))
    (.loadURL @main-window "http://localhost:3742")

    (.setBrowserView @main-window view)
    (.setBounds view #js{:x 0 :y 0 :width size.width :height (- size.height 70)})
    (.loadURL view.webContents "https://google.com")

    (load-config)
    ;;Reload config with shortcut

    (kb/register-key-bindings (get-key-bindings))
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
