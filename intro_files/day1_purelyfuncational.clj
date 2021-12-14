;;Welcome to the bakery!
;;
;;Available commands are: 
;;grab               Pick something up.
;;squeeze            Squeeze whatever you are holding.
;;release            Release whatever you are holding.
;;scoop              If you are holding the cup, fill it with an ingredient.
;;add-to-bowl        Add the ingredient you are holding to the mixing bowl.
;;mix                Mix the ingredients in the bowl.
;;pour-into-pan      Pour the contents of the bowl into the pan.
;;bake-pan           Put the pan in the oven for a certain number of minutes.
;;cool-pan           After baking, put the pan on the cooling racks to cool.
;;                   Returns the id of the cooling rack.
;;status             Print out the status of the bakery.
;;start-over         If you get lost, this command will reset the bakery.
;;go-to              Go to the given location.
;;                  Possible locations are :prep-area, :fridge, and :pantry.
;;load-up            Load an ingredient from storage to take to the prep area.
;;unload             Unload an ingredient in the prep area.
;;get-morning-orders Get a new list of baking orders.
;;delivery           Notify the delivery bot that something is ready to deliver.
;;
;;bakery-help      Print out this message.
;;[Rebel readline] Type :repl/help for online help info



;; Exercise 1
cd introduction-to-clojure
(status)
(start-over) ; starter forfra

(grab :cup)
(scoop :flour)
(add-to-bowl)
(scoop :flour)
(add-to-bowl)
(status) ; Tjek at der er to kopper mel i skålen

(release)
(status)

(grab :egg)
(squeeze)
(add-to-bowl)
(grab :egg)
(squeeze)
(add-to-bowl)

(grab :cup)
(scoop :milk)
(add-to-bowl)

(scoop :sugar)
(add-to-bowl)
(release)

(status)

(mix)

(pour-into-pan)
(bake-pan 25)
(cool-pan)

(status)


; Eksempel paa funktion:
; Hele funktionen returnerer det sidste i funktionen.
(dfrn my-function [a b c d]
    (first-step a)
    (second-step b c)
    (third-step a d))

; Higher level functions
(defn add-egg []
    (grab :egg)
    (squeeze)
    (add-to-bowl))

(defn add-flour []
    (grab :cup)
    (scoop :flour)
    (add-to-bowl)
    (release))

(defn add-milk []
    (grab :cup)
    (scoop :milk)
    (add-to-bowl)
    (release))  

(defn add-sugar []
    (grab :cup)
    (scoop :sugar)
    (add-to-bowl)
    (release)) 


(defn add-butter []
    (grab :butter)
    (add-to-bowl))

; Samme tilgang kan anvendes bare med funktioner i stedet for
(add-flour)
(add-flour)
(add-egg)
(add-egg)
(add-milk)
(add-butter)
(status)

(defn bake-cake [minutter]
    (add-flour)
    (add-flour)
    (add-egg)
    (add-egg)
    (add-milk)
    (add-sugar)
    (mix)
    (pour-into-pan)
    (bake-pan minutter)
    (cool-pan))

; Fuld kageopskrft hvor man anvender funktioner
(start-over)
(bake-cake 40)

; Conditions
; nil og false er falsey
; alt andet er truthy;
; nil er null... 
;
(cond 
    (= x 100)
    (println "hundrede")
    (> x 100)
    (println "stoerre end hundrede")
    :else
    (print "noget andet"))



(defn add [ingredient]
    (cond 
    (= ingredient :egg)
    (add-egg)
    (= ingredient :milk)
    (add-milk)
    (= ingredient :flour)
    (add-flour)
    (= ingredient :sugar)
    (add-sugar)
    (= ingredient :butter)
    (add-butter)
    :else
    (println "oh ohhhhhh unknown ingredient: " ingredient)))

; Bake-cake funktionen tilrettes med den nye add-funktion:

(defn bake-cake [minutter]
    (add :flour)
    (add :flour)
    (add :egg)
    (add :egg)
    (add :milk)
    (add :sugar)
    (mix)
    (pour-into-pan)
    (bake-pan minutter)
    (cool-pan))

;(bake-cake 10) tester....

(defn scooped? [ingredient]
    (cond
    (= ingredient :egg)
    false
    (= ingredient :flour)
    true
    (= ingredient :sugar)
    true
    :else
    false))

; (scooped? :milk) tester....

(defn squeezed? [ingredient]
    (cond
    (= ingredient :egg)
    true
    (= ingredient :flour)
    false
    (= ingredient :sugar)
    false
    :else
    false))

; (squeezed? :egg) tester....

(defn squeezed? [ingredient]
    (= ingredient :egg))
; (squeezed? :egg)
; (squeeezed? :butter)

(defn simple? [ingredient]
    (= ingredient :butter))
; (simple? :butter)


;; Brug if og do funktioner til at lave 'add-scopped', 
;; 'add-squeezed' og 'add-simple'. 
;; Hvis ingredienten opfylder kriterie skal ingredienten tilfoejes 
;; til skaalen som hvis man gjorde det manuelt... 
;; Hvis ingredienten ikke opfylder kriteriet for at kunne tilfoejes
;; paa den angivne maade skal den kaste en errror.
;; Det kunne eks vaere at den angivne ingredient ikke kan tilfoejes med eks. ske
;; Der er tre forskellige metoder til ingredient tilfoejelse. 
;; Man kan tilfoeje med scoop, squeeze og simple.......

(defn add-scooped [ingredient]
  (if (scooped? ingredient)
    (do
      (grab :cup)
      (scoop ingredient)
      (add-to-bowl)
      (release))
    (do
      (println "ohh noooo - du kan ikke scoppe" ingredient :error))))

(add-scooped :egg) ; forventet error
(add-scooped :flour) ; OK
(status)

(defn add-squeezed [ingredient]
  (if (squeezed? ingredient)
    (do
      (grab ingredient)
      (squeeze)
      (add-to-bowl))
    (do
      (println "ohhh noooo - du kan ikke squeeze" ingredient :error))))

(add-squeezed  :egg) ; OK
(add-squeezed  :flour) ; Forventet error
(status)

(defn add-simple [ingredient]
  (if (simple? ingredient)
    (do
      (grab ingredient)
      (add-to-bowl))
    (do
      (println "ohh noooo - du kan ikke tilfoeje simple" ingredient :error))))

(add-simple :egg) ; forventet error
(add-simple :butter) ; OK
(status)

; Full add function.....
(defn add [ingredient]
  (cond
    (squeezed? ingredient)
    (add-squeezed ingredient)
    (scooped? ingredient)
    (add-scooped ingredient)
    (simple? ingredient)
    (add-simple ingredient)
    :else 
    (do
      (println "ohhhhh neeeej - jeg kender ikke ingredienten" ingredient)
      :error)))

(add :egg)
(add :flour)
(add :butter)
(add :kakao)

;;Loops 
(dotimes [i 10]
  (println i))

(defn add-eggs [number]
  (dotimes [e number]
    (add-egg))
  :ok)


 (defn add-flour-cups [antal]
    (do
      (grab :cup)
      (dotimes [e antal]
        (scoop :flour)
        (add-to-bowl))
      (release))
      :ok)

(defn add-flour-cups2 [antal]
  (do
    (dotimes [e antal]
      (add-flour)))
      :ok)

 (defn add-sugar-cups [antal]
    (do
      (grab :cup)
      (dotimes [e antal]
        (scoop :sugar)
        (add-to-bowl))
      (release))
      :ok)

 (defn add-milk-cups [antal]
    (do
      (grab :cup)
      (dotimes [e antal]
        (scoop :milk)
        (add-to-bowl))
      (release))
      :ok)

 (defn add-butters [antal]
    (do
      (dotimes [e antal]
        (grab :butter)
        (add-to-bowl))
      (release))
      :ok)


