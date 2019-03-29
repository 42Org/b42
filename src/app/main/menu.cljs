(ns app.main.menu
  (:require [app.main.keybindings :as keybindings]
            [electron :as electron]))

(def menu-template [{:label "Menu"}])

(defn append [menu key-n-defn]
  (.append menu (new electron/MenuItem (clj->js {:accelerator (first key-n-defn)
                                                 :click (last key-n-defn)}))))

(defn append-keybindings [menu]
  (doseq [key-n-defn keybindings/all] (append menu key-n-defn)))

(defn setup []
  (let [menu (.buildFromTemplate electron/Menu (clj->js menu-template))]
    (append-keybindings menu)
    (.setApplicationMenu electron/Menu menu)))
