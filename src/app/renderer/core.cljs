(ns app.renderer.core
  (:require [reagent.core :as r]))

(defn web-view []
  [:webview {:src "https://google.com/"
             :style {:display :inline-flex
                     :width (.-innerWidth js/window)
                     :height (.-innerHeight js/window)}}])

(.log js/console "Please contribute to B42.")
(r/render [web-view] (.-body js/document))
