(ns my_exercise.core
  (:require [reagent.core :as r]
            [my_exercise.home :as home]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This is the mounting point of the Reagent app,
;; currently served at the root. All this currently
;; does is bind to an almost-root of the document tree
;; and serve home/main (see home.cljs).

;; us_state.cljs lists state names for the form.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn mount-root []
  (r/render home/main (js/document.getElementById "app")))

(mount-root)
