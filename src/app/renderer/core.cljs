(ns app.renderer.core)

(def ipc-renderer (js/require "electron"))
(.log js/console "Renderer process.")
