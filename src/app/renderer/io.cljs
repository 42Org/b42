(ns app.renderer.io
  (:refer-clojure :exclude [load-file]))

(def electron (js/require "electron"))
(def ipc-renderer (.-ipcRenderer electron))

(defn minibuffer-exec [command]
  (.sendSync ipc-renderer "minibuffer-exec" command))

(defn load-file [path]
  (.sendSync ipc-renderer "load-file" path))
