(ns app.main.browser
  (:require [electron :as electron]))

(def BrowserWindow (.-BrowserWindow electron))
(def ^:dynamic Window (atom nil))

(defn set-browser-window-view [view]
  (@Window.setBrowserView view))

(defn get-primary-display []
  (.getPrimaryDisplay (.-screen electron)))

(defn create-browser-window [pref]
  (reset! Window (BrowserWindow. (clj->js pref))))

(defn reset-browser-window-on-close []
  (.on ^js/electron.BrowserWindow @Window "closed" #(reset! Window nil)))

(defn load-url-in-view [view url]
  (view.webContents.loadURL url))

(defn load-url [url]
  (.loadURL ^js/electron.BrowserWindow @Window url))

(defn open-web-inspector [mode]
  (.openDevTools (.-webContents @browser/Window) mode))

(defn create-browser-view
  ([pref url] (create-browser-view pref url 0 0))
  ([pref url x y]
    (let [view electron/BrowserView.
          width (pref "width")
          height (pref "height")]
        (set-browser-window-view view)
        (view.setBounds #js{:x x :y y :width width :height (- height 90)})
        (load-url-in-view view url))))
