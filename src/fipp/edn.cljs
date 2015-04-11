(ns fipp.edn
  "Provides a pretty document serializer and pprint fn for Clojure/EDN forms.
  See fipp.clojure for pretty printing Clojure code."
  (:require [fipp.printer :as printer :refer (pprint-document)]))

;;TODO Figure out what belongs in clojure.clj instead of edn.clj

(defprotocol IPretty
  (-pretty [x ctx]))

(defn pretty [x ctx]
  (if-let [m (and (:print-meta ctx) (meta x))]
    [:align [:span "^" (-pretty m ctx)] :line (-pretty x ctx)]
    (-pretty x ctx)))


(extend-protocol IPretty

  nil
  (-pretty [x ctx]
    [:text "nil"])

  Object
  (-pretty [x ctx]
    [:text (pr-str x)])

  IVector
  (-pretty [v ctx]
    [:group "[" [:align (interpose :line (map #(pretty % ctx) v))] "]"])

  ISeq
  (-pretty [s ctx]
    [:group "(" [:align (interpose :line (map #(pretty % ctx) s))] ")"])

  IMap
  (-pretty [m ctx]
    (let [kvps (for [[k v] m]
                 [:span (-pretty k ctx) " " (pretty v ctx)])
          doc [:group "{" [:align (interpose [:span "," :line] kvps)]  "}"]]
      (if (record? m)
        [:span "#" (-> m class .getName) doc]
        doc)))

  ISet
  (-pretty [s ctx]
    [:group "#{" [:align (interpose :line (map #(pretty % ctx) s)) ] "}"])

  IAtom
  (-pretty [a ctx]
    [:span "#<Atom: " (pretty @a ctx) ">"])




  ;TODO clojure.lang.PersistentQueue, lots more stuff too

  )

(defn pprint
  ([x] (pprint x {}))
  ([x options]
   (let [ctx (merge {:print-meta *print-meta*} options)]
     (binding [*print-meta* false]
       (pprint-document (pretty x ctx) options)))))

(comment

  (defrecord Person [first-name last-name])

  (def fut (future 1))

  (binding [*print-meta* true]
  (->
    ;(list 1 2 3 4 [:a :b :c :d] 5 6 7 8 9)
    ;{:foo 1 :bar \c :baz "str"}
    {:small-value [1 2 3]
     :larger-value ^{:some "meta" :and "such"}
                   {:some-key "foo"
                    :some-other-key "bar"}}
    ;(Person. "Brandon" "Bloom")
    ;(atom (range 20))
    ;fut
    ;#{:foo :bar :baz}
    (pprint {:width 30})))

)
