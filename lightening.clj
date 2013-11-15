(require '[clojure.edn])
(require '[clojure.pprint :refer (pprint)])
(require '[fipp.edn :rename {pprint fipp}])


(def small (clojure.edn/read-string (slurp "hacker-news.edn")))
(def big (clojure.edn/read-string (slurp "comments.edn")))


(time (prn small))
(time (pprint small))
(time (fipp small))


(time (prn big))
(time (pprint big))
(time (fipp big))
