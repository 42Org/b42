(ns app.renderer.ui)

(defn minibuffer []
  [:div {:id "minibuff-container"
         :style {:background-color "#000"
                 :color "#fff"
                 :width "auto" :height "20px"
                 :position "fixed" :bottom 0
                 :left 0 :right 0}}
    [:input {:id "minibuff" :type "text"
             :style {:background-color "#000"
                     :color "#fff"
                     :outline "none"
                     :border "none"
                     :width "100%"}}]])

(defn app-view []
  [:div {:style {:width "auto"}} [minibuffer]])
