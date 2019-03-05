(ns app.main.window
  (:require [electron :as electron]
            [app.main.cli :as cli]
            [app.main.config :as config]
            [app.main.keyboard :as kb]))

(def main-window (atom nil))
(def tabs (atom []))
(def current-tab (atom nil))
(def web-pref {:title "B42" :webPreferences {:nodeIntegration true}
               :icon "./icon.png"})

(defn add-tab [view]
  (reset! tabs (conj @tabs view)))

(defn set-current-tab [view]
  (reset! current-tab view))

(defn set-browser-view [view]
  (.setBrowserView @main-window view))

(defn create-browser-view
  ([pref url] (create-browser-view pref url 0 0))
  ([pref url x y]
   (let [view (new electron/BrowserView)
         width (pref "width")
         height (pref "height")]

     (doto view
       (set-browser-view)
       (.setBounds #js{:x x :y y :width width :height (- height 42)})
       (.webContents.loadURL url)))))

(defn switch-view [view]
  (set-browser-view view))

(defn create-tab [pref url]
  (let [view (create-browser-view pref url)]
    (switch-view view)
    (add-tab view)
    (set-current-tab view)))

(defn tab-switch [index]
  (let [view (@tabs index)]
    (switch-view view)))

(defn init-browser [size]
  (cli/start-msg)
  (let [web-pref (merge web-pref size)]
    (reset! main-window (new electron/BrowserWindow (clj->js web-pref)))
    (.loadURL @main-window "http://localhost:3742")

    (create-tab web-pref "https://yahoo.com")
    (create-tab web-pref "https://github.com")
    (create-tab web-pref "https://google.com")
    (tab-switch 1)

    (config/load)
    ;;Reload config with shortcut

    (kb/register-key-bindings (config/get-key-bindings))
    (.on @main-window "closed" #(reset! main-window nil))))

(defn start-default-window
  ([] (let[screen (.getPrimaryDisplay electron/screen)
           size (js->clj screen.workAreaSize)]
        (start-default-window size)))
  ([size] (init-browser size)))
