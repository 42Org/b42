(ns app.renderer.bootstrap
  (:require [app.common.bootstrap :as boot]
            [app.renderer.init :as init]
            [shadow.cljs.bootstrap.browser :as browser]))

;;Bootstrapping code for renderer process.

(defn eval-form [exp]
  (boot/eval-form exp browser/load))

(defn eval-string [exp]
  (boot/eval-string exp browser/load))

(defn init[]
  (boot/init browser/init "/bootstrap"
             (fn[]
               ;;Load browser UI init scripts from this point.
               (eval-form init/load-msg)
               (eval-form (:data (init/load-init-scripts))))))

;;Bootstrapping code ends here.
