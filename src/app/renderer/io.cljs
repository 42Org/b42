(ns app.renderer.io
  (:refer-clojure :exclude [load-file]))

(def electron (js/require "electron"))
(def ipc-renderer (.-ipcRenderer electron))

(defn load-file [path]
  (.sendSync ipc-renderer "load-file" path))
