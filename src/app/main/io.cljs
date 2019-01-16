(ns app.main.io
  (:require [fs :as fs]
            [path :as file-path]))

(defn path-finder [path cb]
  (.realpath fs path cb))

(defn absolute-path [path]
  (if (= (first path) "~")
    (.join file-path js/process.env.HOME (subs path 1))
    path))

(defn ipc->send [event]
  (fn[err data]
    (let [msg (prn-str {:type "data" :data (str data)})]
      (set! event.returnValue msg))))

(defn read->send [event]
  (fn[err reslv-path]
    (.readFile fs reslv-path (ipc->send event))))

(defn read-file [event path]
  (let [abs-path (absolute-path path)]
    (if (.existsSync fs abs-path)
      (path-finder abs-path (read->send event))
      (set! event.returnValue
            (prn-str {:type "error" :data "FileNotFound" :file abs-path})))))
