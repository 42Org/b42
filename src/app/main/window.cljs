(ns app.main.window
  (:require [app.main.electro :as electro]
            [app.main.cli :as cli]
            [app.main.menu :as menu]
            [app.main.config :as config]
            [app.main.normal :as normal]))

(def web-pref {:title "B42" :webPreferences {:nodeIntegration true}
               :icon "./icon.png"})

(defn init-browser [size]
  (cli/start-msg)
  (let [web-pref (merge web-pref size)]
    (electro/create-browser-window web-pref)
    (.loadURL @electro/browser-window "http://localhost:3742")
    (menu/build-and-append-keys)
    (normal/create-test-tabs web-pref)

    (config/load)
    ;;Reload config with shortcut

    (electro/reset-browser-window-on-close)))

(defn start-default-window
  ([] (let[screen (electro/get-primary-display)
           size (js->clj screen.workAreaSize)]
        (start-default-window size)))
  ([size] (init-browser size)))
