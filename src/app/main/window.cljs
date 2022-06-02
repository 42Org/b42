(ns app.main.window
  (:require [app.main.browser :as browser]
            [app.main.cli :as cli]
            [app.main.menu :as menu]
            [app.main.config :as config]
            [app.main.tools :as tools]
            ))

(def web-pref {:title "B42" :webPreferences {:nodeIntegration true}
               :icon "./icon.png"})

(defn init-browser [size]
  (cli/start-msg)
  (let [web-pref (merge web-pref size)]
    (browser/create-browser-window web-pref)
    ;; (.loadURL browser/browser-window "http://localhost:3742")
    (menu/build-and-append-keys)
    (tools/create-test-tabs web-pref)

    (config/load)
    ;;Reload config with shortcut

    (browser/reset-browser-window-on-close)
    ))

(defn start-default-window
  ([] (let[screen (browser/get-primary-display)
           size (js->clj (.-workAreaSize screen))]
        (start-default-window size)))
  ([size] (init-browser size)))
