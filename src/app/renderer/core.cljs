(ns app.renderer.core
  (:require [reagent.core :as r]))

(defn web-view []
  [:webview {:src "http://github.com"
             :style {:display :inline-flex :width 640 :height 480}}])

(.log js/console "Renderer process in the browser.")
(r/render-component [web-view] (.-body js/document))
