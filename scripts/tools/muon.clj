(ns tools.muon
  (:require [clojure.java.shell :refer (sh)]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [progress.file :as progress]))

(def url 
  (str "https://api.github.com/repos/brave/muon/releases/latest"))


(defn arch-name []
  (let [os (System/getProperty "os.name")]
    (case os
      "Mac OS X" #"darwin"
      "Linux" #"linux"
      "Windows" #"win32")))

(defn find-download-url [assets]
    (loop [down nil size nil lst assets]
      (if down
        [down size]
        (do
          (let [url (get (first lst) "browser_download_url")
                size (get (first lst) "size")]
            (if (and (re-find (arch-name) url) (not (re-find #"symbols" url)))
              (recur url size (rest lst))
              (recur nil size (rest lst))))))))

(defn fetch []
  (let [json-req (slurp url)
        assets (get (json/read-str json-req) "assets")
        zip-url (find-download-url assets)]
    (let [file (io/file "vendor/muon.zip")]
      (progress/with-file-progress file :filesize (last zip-url)
        (with-open [input (io/input-stream (first zip-url))]
         (io/copy input file))))))
