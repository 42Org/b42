(ns app.main.electro
  (:require [electron :as electron]))

(def main-window (atom nil))

(defn set-browser-view [view]
  (.setBrowserView @main-window view))

(defn load-url-in-window [url] (.loadURL @main-window url))

(defn get-primary-display []
  (.getPrimaryDisplay electron/screen))

(defn create-main-window [pref]
  (reset! main-window (new electron/BrowserWindow (clj->js pref))))

(defn reset-main-window-on-close []
  (.on @main-window "closed" #(reset! main-window nil)))

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
