(ns my-exercise.home
  (:require [hiccup.page :refer [html5]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This is currently served as "/", as
;; coded in core.clj.

;; There is not much to see here, CSS is linked,
;; the forgery token field is set up,
;; and app.js (the location of which is specified
;; in project.clj) is loaded after the body,
;; which it latches onto and renders a Reagent app.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn header []
  [:head
   [:meta {:charset "UTF-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1.0, maximum-scale=1.0"}]
   [:title "Election Finder"]
   [:link {:rel "stylesheet" :href "default.css"}]
   (anti-forgery-field)])

(defn page [request]
  (html5
   (header)
   [:body [:div#app]]
   [:script {:src "js/app.js"}]))
