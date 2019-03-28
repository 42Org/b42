(ns app.main.normal
  (:require [app.main.viewport :as viewport]
            [app.main.keyboard :as kb]))

(defn set-keybindings []
  (kb/bind-key "ctrl+p" viewport/prev-view)
  (kb/bind-key "ctrl+n" viewport/next-view))

;; FIXME: Debug code. Remove later.
(defn create-test-tabs [pref]
  (viewport/create pref "https://yahoo.com")
  (viewport/create pref "https://github.com")
  (viewport/create pref "https://google.com")
  (viewport/go-to 1))
