(ns app.main.tools
  (:require [app.main.viewport :as viewport]
            [app.main.browser :as browser]
            [app.main.keyboard :as kb]))

(defn web-inspector []
  (.openDevTools (.-webContents @browser/Window) #js{:mode "detach"}))

;; FIXME: Debug code. Remove later.
(defn create-test-tabs [pref]
  (viewport/create pref "https://yahoo.com")
  (viewport/create pref "https://github.com")
  (viewport/create pref "https://google.com")
  (viewport/go-to 1))

(defn open-url [_ url]
  (viewport/load-url-in-current url))
