(task-options!
  aot {:namespace '#{boot-embongo.core}}
  pom {:project 'io.github.marad/boot-embongo
       :version "0.0.2-SNAPSHOT"})

(set-env!
  :source-paths #{"src"}
  :resource-paths #{"src"}
  :dependencies '[[boot/core "2.5.5" :scope "provided"]
                  [org.clojure/tools.logging "0.3.1"]
                  [de.flapdoodle.embed/de.flapdoodle.embed.mongo "1.46.4"]])


(deftask publish-local []
  (comp (pom) (jar) (install)))

