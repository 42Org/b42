(ns app.main.viewport
  (:require [app.main.browser :as browser]))

(def views (atom []))
(def current (atom nil))

(defn add [view]
  (reset! views (conj @views view)))

(defn set-current [view]
  (reset! current view))

(defn switch-view [view]
  (set-current view)
  (browser/set-browser-window-view view))

(defn create [pref url]
  (let [view (browser/create-browser-view pref url)]
    (switch-view view)
    (add view)
    (set-current view)))

(defn go-to [index]
  (let [view (@views index)]
    (switch-view view)))

(defn current-index [] (.indexOf @views @current))

(defn calc-next-index []
  (let [index (+ 1 (current-index))]
    (if (< index (count @views)) index 0)))

(defn next-view []
  (go-to (calc-next-index)))

(defn last-index []
  (- (count @views) 1))

(defn calc-prev-index []
  (let [index (- (current-index) 1)]
    (if (< index 0) (last-index) index)))

(defn prev-view []
  (go-to (calc-prev-index)))

(defn load-url-in-current [url]
  (browser/load-url-in-view @current url))
