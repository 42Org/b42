(ns app.main.window
  (:require [app.main.electro :as electro]
            [app.main.cli :as cli]
            [app.main.config :as config]
            [app.main.keyboard :as kb]))

(def tabs (atom []))
(def current-tab (atom nil))
(def web-pref {:title "B42" :webPreferences {:nodeIntegration true}
               :icon "./icon.png"})

(defn add-tab [view]
  (reset! tabs (conj @tabs view)))

(defn set-current-tab [view]
  (reset! current-tab view))

(defn switch-view [view]
  (electro/set-browser-view view))

(defn create-tab [pref url]
  (let [view (electro/create-browser-view pref url)]
    (switch-view view)
    (add-tab view)
    (set-current-tab view)))

(defn tab-switch [index]
  (let [view (@tabs index)]
    (switch-view view)))

(defn init-browser [size]
  (cli/start-msg)
  (let [web-pref (merge web-pref size)]
    (electro/create-main-window web-pref)
    (electro/load-url-in-window "http://localhost:3742")

    (create-tab web-pref "https://yahoo.com")
    (create-tab web-pref "https://github.com")
    (create-tab web-pref "https://google.com")
    (tab-switch 1)

    (config/load)
    ;;Reload config with shortcut

    (kb/register-key-bindings (config/get-key-bindings))
    (electro/reset-main-window-on-close)))

(defn start-default-window
  ([] (let[screen (electro/get-primary-display)
           size (js->clj screen.workAreaSize)]
        (start-default-window size)))
  ([size] (init-browser size)))
