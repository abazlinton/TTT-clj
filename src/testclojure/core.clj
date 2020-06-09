(ns testclojure.core)

(def win-lines
  '([0, 1, 2],
   [3, 4, 5],
   [6, 7, 8],
   [0, 3, 6],
   [1, 4, 7],
   [2, 5, 8],
   [0, 4, 8],
   [2, 4, 6])
  )

(defn grid-has-winning-line?
  [grid win-line player]
  (= (get grid (get win-line 0)) 
     (get grid (get win-line 1)) 
     (get grid (get win-line 2)) 
     player))

(defn game-is-won?
  [grid, player]
  (some true? (for [win-line win-lines]
    (grid-has-winning-line? grid win-line player))))

(defn get-grid
  [grid]
  (for [row (range 0 9 3)]
    (for [col (range 3)]
      (get grid (+ col row)))))

(defn print-grid
  [grid]
  (doseq [row (get-grid grid)]
    (println row)))

(defn make-grid
  []
  (vec (take 9 (range))))

(defn swap-player
  [current-player]
  (if (= "X" current-player)
    "O"
    "X"))

(defn take-square
  [state square]
  (let [grid (:grid state) player (:player state)]
    (if (int? (get grid square))
     {:player (swap-player player) :grid (assoc grid square player)}
      state)))

(defn run-game
  [grid, player]
  (let [last-player (swap-player player)]
  (if (not (game-is-won? grid last-player))
    (do
      (print-grid grid)
      (println (str player " to move what square?"))
      (flush)
      (let [square (read-line)]
        (let [state (take-square {:grid grid :player player} (Integer/parseInt square))]
          (run-game (:grid state) (:player state))))
      )
    (do 
      (println (str last-player " won!"))
      (print-grid grid)))))

