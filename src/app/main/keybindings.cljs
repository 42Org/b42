(ns app.main.keybindings
  (:require [app.main.viewport :as viewport]
            [app.main.tools :as tools]))

(def all {
  "Ctrl+Shift+i" tools/web-inspector
  "CmdOrCtrl+p" viewport/prev-view
  "CmdOrCtrl+n" viewport/next-view })
