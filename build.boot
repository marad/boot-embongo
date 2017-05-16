(def +version+ "0.0.2-SNAPSHOT")
(task-options!
  aot {:namespace '#{boot-embongo.core}}
  pom {:project 'io.github.marad/boot-embongo
       :version +version+})

(set-env!
  :source-paths #{"src"}
  :resource-paths #{"src"}
  :dependencies '[[boot/core "2.5.5" :scope "provided"]
                  [org.clojure/tools.logging "0.3.1"]
                  [de.flapdoodle.embed/de.flapdoodle.embed.mongo "1.46.4"]
                  [adzerk/bootlaces "0.1.13"]])

(require '[adzerk.bootlaces :refer :all])
(bootlaces! +version+)

(deftask publish-local []
  (comp (pom) (jar) (install)))

