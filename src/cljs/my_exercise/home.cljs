(ns my-exercise.home
  (:require [reagent.core :as r]
            [ajax.core :as ajax]
            [clojure.string :as str]
            [my-exercise.us-state :as states]
            [cljs.reader :refer [read-string]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This is the heart of the application.
;; It is currently served at the root url via
;; core.clj -> home.clj -> core.cljs -> [here]

;; Skip the next paragraphs if you just want to
;; read the code.

;; The page consists of 2 parts (as seen in
;; the main function: the address-form and
;; the result-list, and 3 atoms, the
;; address, the search-results, and the
;; submitted results.

;; Editing the address-form edits the
;; address atom, which is then sent to the
;; server. The results of that are stored
;; in search-results, which are displayed via
;; the results-list. The submitted-search
;; is saved to be able to display a header
;; for the results-list, and to know that
;; the results-list is expected to be populated.

;; The graph of function calls (-->) to other
;; functions defined in this file:

;;            +-----------main--------+
;;            |                       |
;;            v                       v
;;  +------address-form            result-list
;;  |         |      |                |
;;  |         v      v                v
;;  | attr-input    send-search    render-result
;;  |         |
;;  |         v
;;  +-->build-input-fn

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defonce forgery-token
  (.-value (js/document.querySelector "#__anti-forgery-token")))

(defonce address
  (r/atom {:street nil
           :state nil
           :city nil
           :zip nil}))

(defonce search-results (r/atom []))
(defonce submitted-search (r/atom {}))

(defn send-search [address]
  (ajax/POST "/search"
             {:format :json
              :params address
              :headers {:X-CSRF-Token forgery-token}
              :handler #(do (reset! search-results (read-string %))
                            (reset! submitted-search address))}))

(defn render-result [res]
  (let [date (:date res)
        hour (.toLocaleTimeString date)
        day (.toDateString date)
        site (:website res)]
    [:div.res {:key (:id res)}
     [:h4 (:description res)]
     [:p (str hour ", " day)]
     [:a {:href site :target "_blank"} site]]))

(defn result-list [search results]
  (when (not-empty @search)
    (let [{city :city state :state} @search
          {elections :elections e :error} @results
          address-str (str/join ", " (remove empty? [city state]))]
      [:div.result-list
       (if (empty? elections)
         [:p.empty-msg (or e "No elections found.")]
         [:div
          [:h3 (str "Results for " address-str)]
          [:div (map render-result elections)]])])))

(defn build-input-fn [atom attr]
  (fn [x] (swap! atom assoc attr (-> x .-target .-value))))

(defn attr-input [atom attr]
  [:input {:type "text"
           :value (attr @atom)
           :on-change (build-input-fn atom attr)}])

(defn address-form [address]
  [:div.address-form
   [:h1 "Find my next election"]
   [:div.inner-form
    [:p "Enter the address where you are registered to vote"]
    [:div
     [:label "Street:"]
     [attr-input address :street]]
    [:div
     [:label "City:"]
     [attr-input address :city]
     [:label "State:"]
     [:select#state-field {:on-change (build-input-fn address :state)}
      [:option ""]
      (for [s states/postal-abbreviations]
        [:option {:value s :key s} s])]
     [:div.zip
      [:label "ZIP Code:"]
      [attr-input address :zip]]
     [:div.button
      [:button {:on-click #(send-search @address)}
       "Search"]]]]])

(defn main []
  [:div#main
   [address-form address]
   [result-list submitted-search search-results]])
