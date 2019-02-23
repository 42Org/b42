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

(defn add-tab [id]
  (reset! tabs (conj @tabs id)))

(defn set-current-tab [id]
  (reset! current-tab id))

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

(defn init-browser [size]
  (cli/start-msg)
  (let [web-pref (merge web-pref size)]
    (reset! main-window (new electron/BrowserWindow (clj->js web-pref)))
    (.loadURL @main-window "http://localhost:3742")

    (let [view (create-browser-view web-pref "https://yahoo.com")]
      (.log js/console view))
    (config/load)
    ;;Reload config with shortcut

    (kb/register-key-bindings (config/get-key-bindings))
    (.on @main-window "closed" #(reset! main-window nil))))

(defn start-default-window
  ([] (let[screen (.getPrimaryDisplay electron/screen)
           size (js->clj screen.workAreaSize)]
        (start-default-window size)))
  ([size] (init-browser size)))
