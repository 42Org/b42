(ns app.renderer.ui
  (:require [app.renderer.io :as io]))

(defn minibuffer-key-up [event]
  (if (= "Enter" (.-key event)) (io/minibuffer-exec (-> event .-target .-value)) 1))

(defn minibuffer-input []
  [:input
    {:id "minibuff" :type "text"
     :on-key-up minibuffer-key-up
     :style {:background-color "#000"
             :color "#fff"
             :outline "none"
             :border "none"
             :width "100%"}}])

(defn minibuffer []
  [:div {:id "minibuff-container"
         :style {:background-color "#000"
                 :color "#fff"
                 :width "auto" :height "20px"
                 :position "fixed" :bottom 0
                 :left 0 :right 0}}
    [minibuffer-input]])

(defn app-view []
  [:div {:style {:width "auto"}} [minibuffer]])
