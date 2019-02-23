(ns app.renderer.bootstrap
  (:require [app.common.bootstrap :as boot]
            [app.renderer.init :as init]
            [shadow.cljs.bootstrap.browser :as browser]))

;;Bootstrapping code for renderer process.

(defn eval-form [exp]
  (boot/eval-form exp browser/load))

(defn eval-string [exp]
  (boot/eval-string exp browser/load))

(defn init [& code]
  (boot/init browser/init "/bootstrap"
             (fn[]
               ;;Load browser UI init scripts from this point.
               (eval-string init/load-msg)
               (eval-string (:data (init/load-init-scripts))))))

;;Bootstrapping code ends here.
