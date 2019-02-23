(ns app.main.config
  (:require [app.main.io :as io]
            [app.main.bootstrap :as bootstrap]))

(def global-config (atom {:ui-config [] :key-bindings []}))

;; fetches key bindings pre loaded in global-config :key-binding atom
(defn get-key-bindings []
  (@global-config :key-bindings))

;;Load config from some ~/.b42/init.cljs
(defn load-global-config
  ([] (load-global-config "~/.b42/b42/init.cljs"))
  ([config-file] (io/load-file config-file)))

(defn load []
  ;;call to initialize bootstrapping compiler.
  (bootstrap/boot-init
   (str '(println "CLJS bootstrapped ..."))
   (load-global-config)))
