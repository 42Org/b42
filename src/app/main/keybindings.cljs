(ns app.main.keybindings
  (:require [app.main.viewport :as viewport]
            [app.main.normal :as normal]))

(def all {
  "Ctrl+Shift+i" normal/web-inspector
  "CmdOrCtrl+p" viewport/prev-view
  "CmdOrCtrl+n" viewport/next-view })
