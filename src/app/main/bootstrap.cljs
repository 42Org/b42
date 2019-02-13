(ns app.main.bootstrap
  (:require [cljs.env :as env]
            [app.common.bootstrap :as boot]
            [shadow.cljs.bootstrap.node :as boot-node]))

;;Bootstrapping code for main process.

(defonce default-env (env/default-compiler-env))

(defn eval-form [exp]
  (boot/eval-form default-env exp boot-node/load))

(defn eval-string [exp]
  (boot/eval-string default-env exp boot-node/load))

(defn boot-init [& code]
  (boot/init boot-node/init default-env "app/bootstrap"
             #(doseq [txt code] (eval-form txt))))

;;Bootstrapping code ends here.




