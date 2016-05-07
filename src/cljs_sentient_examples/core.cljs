(ns cljs-sentient-examples.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
;; TODO write a cljs library like honeysql for using sentient?
;; TODO if y doesn't need to be global, push it down as a let inside defn input
;; TODO include randomise button to insert all values.

(defonce app-state (atom {:text "Sentient Test"
                          :x ""
                          :y 4
                          :z 2}))

;(def sentient (swap! app-state assoc :z (+ (:x @app-state) (:y @app-state))))
(defn exp [x n]
  (reduce * (repeat n x)))

(defn sentient [] (swap! app-state assoc :z
                         (- (+ (+ (exp (js/parseInt (:y @app-state)) 3)
                            (exp (js/parseInt (:x @app-state)) 2))
                            (js/parseInt (:x @app-state)))
                            (js/parseInt (:y @app-state)))))

(defn input [value]
    [:input {:type "text"
             :placeholder (name value)
             :value (value @app-state)
             :class "style-1"
             :size 5
             :on-change #(do (swap! app-state assoc value (-> % .-target .-value))
                             (sentient))}])

(defn interface []
  [:div
   [:h1 (:text @app-state)]
   [:p "The value of Y is " (:y @app-state)]
   [:p (input :x) "^3 + " (input :y) "^2 + " (input :x) " - " (input :y) " = " (input :z)]])

(reagent/render-component [interface]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)