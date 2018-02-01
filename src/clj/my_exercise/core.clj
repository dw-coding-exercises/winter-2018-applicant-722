(ns my-exercise.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [my-exercise.home :as home]
            [my-exercise.elections :as elections]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This should be pretty self explitory, but
;; this the main entry point into our code
;; from "lein ring server".

;; Root routes to home/page serves the Reagent Cljs app.
;; Headers and CSS will be set up there.

;; The Cljs app makes requests to "/search" which
;; will hit a turbovote api and return the results.
;; That is all there is too it.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defroutes app
  (GET "/" [] home/page)
  (POST "/search" [] elections/search)
  (route/resources "/")
  (route/not-found "Not found"))

(def handler
  (-> app
      (wrap-defaults site-defaults)
      wrap-json-params
      wrap-reload))
