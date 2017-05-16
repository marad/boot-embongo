(ns boot-embongo.core
    (:require [boot.core :as core]
              [clojure.tools.logging :as log])
    (:import [de.flapdoodle.embed.mongo MongodStarter]
             [de.flapdoodle.embed.mongo.config MongodConfigBuilder RuntimeConfigBuilder]
             [de.flapdoodle.embed.mongo.distribution Version$Main]
             [de.flapdoodle.embed.mongo.config Net]
             [java.util.logging Logger]
             [java.util UUID]
             [de.flapdoodle.embed.process.config.io ProcessOutput]
             [de.flapdoodle.embed.mongo Command]))

(def ^:private embongo (atom nil))

(defn- random-folder-name []
  (str "embongo-" (UUID/randomUUID)))

(defn- start-mongo [version port data-dir]
  (log/info "Starting mongo on port" port)
  (let [logger (Logger/getLogger "embongo")
        starter (MongodStarter/getInstance (-> (RuntimeConfigBuilder.)
                                               (.defaultsWithLogger Command/MongoD logger)
                                               (.processOutput (ProcessOutput/getDefaultInstanceSilent))
                                               (.build)))
        mongo-config (-> (MongodConfigBuilder.)
                         (.version version)
                         (.net (Net. port true))
                         (.build))
        mongod-executable (.prepare starter mongo-config)
        mongo-process (.start mongod-executable)]
    (reset! embongo mongo-process)))


(defn- stop-mongo []
  (if-not (nil? @embongo)
          (do
            (.stopInternal @embongo)
            (reset! embongo nil))
          (log/info "Mongo is not currently running.")))


(core/deftask start-embongo
  "Starts embedded mongo"
  [p port VAL int "Port number"]
  (let [port (or (:port *opts*) 27017)]
    (core/with-pre-wrap fileset
      (start-mongo Version$Main/PRODUCTION port (str "/tmp/" (random-folder-name)))
      fileset)))

(core/deftask stop-embongo
  "Stops embedded mongo"
  []
  (core/with-pre-wrap fileset
    (stop-mongo)
    fileset))
