(ns my-exercise.elections
  (:require [hiccup.page :refer [html5]]
            [clojure.string :as str]
            [clojure.edn :as edn]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This is currently accessed by "/search", as
;; coded in core.clj.

;; This is where most of the small amount of
;; backend business logic lives. This logic
;; could currently live on the front end,
;; but if an API token were needed then
;; something like this would be conventional.

;; All 3 functions besides "search" are
;; called by search and otherwise do not
;; call each other.

;; This currently takes a request, and
;; builds a turbovote url from the state
;; and city (if there is one). It then
;; slurps the url, does some perfunctory
;; response standardizing, and sends
;; the elections back down the wire.

;; With more time this search function could:
;; Sanitize and validate inputs better.
;; Avoid making needless calls to the turbovote api.
;; Handle retries, errors, and error messaging better.
;; Derive more OCD division from the address.
;; Do some basic caching/memoization (unless handled elsewhere).

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A Turbovote url looks like this:
;; https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:or,ocd-division/country:us/state:or/place:portland"

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn build-election [election]
  (select-keys election [:id :description :date :website]))

(defn build-response [params elections]
  {:params params
   :city (:city params)
   :state (:state params)
   :elections (map build-election (or elections []))
   :error (when (nil? elections)
            "The machines are refusing to cooperate. Make to submit a valid state.")})

(defn build-turbovote-url [{city :city state :state}]
  (when-not (empty? state)
    (let [site-pred "https://api.turbovote.org/elections/upcoming?district-divisions="
          state-pred "ocd-division/country:us/state:"
          state-str (str state-pred state)
          place-pred (str "," state-str "/place:")
          place-str (when-not (empty? city) (str place-pred city))
          site-str (str site-pred state-str place-str)]
      (-> site-str
          str/lower-case
          (str/replace " " "_")))))

(defn search [request]
  (let [params (:params request)
        url (build-turbovote-url params)
        elections (edn/read-string (when url (slurp url)))
        output (build-response params elections)]
    {:status 200
     :body (pr-str output)}))
