(ns app.renderer.bootstrap
  (:require [cljs.env :as env]
            [app.common.bootstrap :as boot]
            [app.renderer.init :as init]
            [shadow.cljs.bootstrap.browser :as browser]))

;;Bootstrapping code for renderer process.

(defonce default-env (env/default-compiler-env))

(defn eval-form [exp]
  (boot/eval-form default-env exp browser/load))

(defn eval-string [exp]
  (boot/eval-string default-env exp browser/load))

(defn init[]
  (boot/init browser/init default-env "/bootstrap"
             (fn[]
               ;;Load browser UI init scripts from this point.
               (eval-form init/load-msg)
               (eval-form (:data (init/load-init-scripts))))))

;;Bootstrapping code ends here.
