(ns app.main.cli
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]))

(def pkg-json (js->clj (js/require "../package.json")))
(defonce b42-version (pkg-json "version"))
(defn start-msg [] 
  (println (str "Starting B42 " b42-version)))

(def cli-options
  [["-v" "--version" "B42 Version" 
    :id :version
    :default-desc b42-version]
   ["-h" "--help"]])

(defn usage [opt-summary]
  (->> ["B42 Options"
        ""
        "Usage: b42 [options ...] action [url]"
        ""
        "Options:" opt-summary
        ""
        "Actions:"
        ".. add some actions here .."
        ""]
        (string/join \newline)))

(defn validate-args [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) {:exit-message (usage summary) :ok? true}
      (:version options) {:exit-message (str "B42 Browser " b42-version) :ok? true}
      :else {:exit-message (usage summary) :ok? true})))

(defn exit [status msg]
  (println msg)
  (.exit js/process status))

(defn parse-args [args]
  (let [{:keys [action options exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      "")))

