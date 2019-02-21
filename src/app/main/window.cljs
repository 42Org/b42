(ns app.main.window
  (:require [electron :as electron]
            [app.main.cli :as cli]
            [app.main.config :as config]
            [app.main.keyboard :as kb]))

(def main-window (atom nil))

(defn init-browser-view []
  (let [web-pref #js{:title "B42" :webPreferences {:nodeIntegration true}
                     :icon "./icon.png"}]
    (new electron/BrowserView web-pref)))

(defn create-browser-view [size]
  (let [view (init-browser-view)]
    (.setBrowserView @main-window view)
    (.setBounds view #js{:x 0 :y 0 :width size.width :height (- size.height 70)})
    (.loadURL view.webContents "https://google.com")))

(defn init-browser [size]
  (cli/start-msg)
  (let [web-pref size]
    (reset! main-window (new electron/BrowserWindow (clj->js web-pref)))
    (.loadURL @main-window "http://localhost:3742")

    (create-browser-view size)
    (config/load)
    ;;Reload config with shortcut

    (kb/register-key-bindings (config/get-key-bindings))
    (.on @main-window "closed" #(reset! main-window nil))))

(defn start-default-window []
  (let[screen (.getPrimaryDisplay electron/screen)
       size screen.size]
    (init-browser size)))
