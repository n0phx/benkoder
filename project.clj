(defproject benkoder "0.1.0"
  :description "Bencode encoding and decoding library."
  :url "https://github.com/integricho/benkoder"
  :scm {:name "git"
        :url "https://github.com/integricho/benkoder"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :pom-addition [:developers [:developer
                               [:name "Andrean Franc"]
                               [:url "https://integricho.github.io/"]
                               [:email "andrean.franc@gmail.com"]
                               [:timezone "+1"]]]
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles       {:1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
                   :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}
                   :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
                   :1.7 {:dependencies [[org.clojure/clojure "1.7.0-alpha4"]]}
                   :master {:dependencies [[org.clojure/clojure "1.7.0-master-SNAPSHOT"]]}}
  :aliases        {"all" ["with-profile" "1.4,1.5,1.6,1.7,master"]}
  :repositories {"sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"
                             :snapshots false
                             :releases {:checksum :fail :update :always}}
                 "sonatype-snapshots" {:url "http://oss.sonatype.org/content/repositories/snapshots"
                                       :snapshots true
                                       :releases {:checksum :fail :update :always}}})
