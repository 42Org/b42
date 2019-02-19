(ns app.common.bootstrap
  (:require [cljs.env :as env]
            [cljs.tools.reader :refer [read-string]]
            [cljs.js :refer [eval eval-str js-eval compile-str]]))

(defonce default-env (env/default-compiler-env))

(defn eval-form [exp load]
  (eval-str default-env
            (str exp)
            "[B42]"
            {:eval       js-eval
             :source-map true
             :verbose false
             :load (partial load default-env)
             :ns 'app.main.core}
            identity))

(defn eval-string [exp load]
  (eval default-env
        (read-string exp)
        {:eval       js-eval
         :source-map true
         :verbose false
         :load (partial load default-env)
         :ns 'app.main.core}
        identity))

(defn compile-string [exp]
  (compile-str default-env
               exp
               "[B42]"
               {:eval js-eval}
               #(js-eval {:source (:value %)})))

(defn init [boot path cb]
  (boot default-env {:path path} cb))
