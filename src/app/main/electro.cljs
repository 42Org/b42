(ns app.main.electro
  (:require [electron :as electron]))

(def browser-window (atom nil))

(defn set-browser-window-view [view]
  (.setBrowserView @browser-window view))

(defn get-primary-display []
  (.getPrimaryDisplay electron/screen))

(defn create-browser-window [pref]
  (reset! browser-window (new electron/BrowserWindow (clj->js pref))))

(defn reset-browser-window-on-close []
  (.on @browser-window "closed" #(reset! browser-window nil)))

(defn load-url-in-view [view url]
  (.webContents.loadURL view url))

(defn create-browser-view
  ([pref url] (create-browser-view pref url 0 0))
  ([pref url x y]
    (let [view (new electron/BrowserView)
          width (pref "width")
          height (pref "height")]

      (doto view
        (set-browser-window-view)
        (.setBounds #js{:x x :y y :width width :height (- height 70)})
        (load-url-in-view url)))))
