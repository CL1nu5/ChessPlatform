# Piece Movement
## Pawn
A pawn can move in a few different ways:

 - Moves if the field is free:
   - One step forward: pos(x=1, y=1) -> pos(x=1, y=2)
   - Two steps forward (can only be performed, if the space in between is free and if it is the first move of the pawn):
     pos(x=1, y=1) -> pos(x=1, y=3)

 - Moves to capture enemy's:
   - diagonal capture: pos(x=1, y=1) -> pos(x=2, y=2) - enemy needs to be on pos(x=2, y=2)
   - en passant: pos(x=1, y=4) -> pos(x=2, y=5) 
     - can only be performed if an enemy pawn moved the following the round before: pos(x=2, y=6) -> pos(x=2, y=4)