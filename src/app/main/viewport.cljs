(ns app.main.viewport
  (:require [app.main.electro :as electro]))

(def views (atom []))
(def current (atom nil))

(defn add [view]
  (reset! views (conj @views view)))

(defn set-current [view]
  (reset! current view))

(defn switch-view [view]
  (electro/set-browser-view view))

(defn create [pref url]
  (let [view (electro/create-browser-view pref url)]
    (switch-view view)
    (add view)
    (set-current view)))

(defn go-to [index]
  (let [view (@views index)]
    (switch-view view)))

(defn next-view []
  (go-to 2))
