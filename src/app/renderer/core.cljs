(ns app.renderer.core
  (:require [reagent.core :as r]
            [cljs.tools.reader :refer [read-string]]
            [cljs.js :refer [empty-state eval eval-str js-eval]]
            [cljs.pprint :refer [pprint]]
            [shadow.cljs.bootstrap.browser :as boot]))

(enable-console-print!)

(def default-env (empty-state))

(defn- load-msg []
  "(println \"B42 CLJS/Runtime loaded...\")")

(defn eval-form [exp]
  (eval-str default-env
            (str exp)
            "[B42]"
            {:eval       js-eval
             :source-map true
             :load (partial boot/load default-env)}
            identity))

(defn eval-string [exp]
  (eval default-env
        (read-string exp)
        {:eval       js-eval
         :source-map true
         :load (partial boot/load default-env)}
        identity))

(defn webview []
  [:webview {:id "webview"
             :src "https://youtu.be/ITjYq18zlwA"
             :useragent "B42 Browser/0.0.1 Chrome/71.0.3578.98 Safari/537.36"
             :style {:display :inline-flex
                     :position "fixed"
                     :top 0 :left 0 :right 0
                     :width "auto"
                     :height "98%"}}])
(defn modeline []
  [:div {:id "modeline"
         :style {:background-color "#000"
                 :color "#fff"
                 :width "auto" :height "20px"
                 :position "fixed" :bottom 0
                 :left 0 :right 0}} :M-x])

(defn app-view []
  [:div {:style {:width "auto"}} [webview][modeline]])

(defn mount-root [] 
  (r/render [app-view] (.getElementById js/document "app")))

(defn init []
  (boot/init default-env {:path "/bootstrap"}
             (fn[]
               (do
                 (mount-root)
                 ;;Load browser UI init scripts from this point.
                 (eval-form (load-msg))))))

(defn start []
  (mount-root)
  (init))

(defn stop [])
