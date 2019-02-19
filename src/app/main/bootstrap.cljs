(ns app.main.bootstrap
  (:require [app.common.bootstrap :as boot]
            [shadow.cljs.bootstrap.node :as boot-node]))

;;Bootstrapping code for main process.


(defn eval-form [exp]
  (boot/eval-form exp boot-node/load))

(defn eval-string [exp]
  (boot/eval-string exp boot-node/load))

(defn compile-string [exp]
  (boot/compile-string exp))

(defn boot-init [& code]
  (boot/init boot-node/init "app/bootstrap"
             #(doseq [txt code]
                (compile-string txt))))


;;Bootstrapping code ends here.




