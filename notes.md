This is going to be huge.
OK so the board accounts for positioning now. SO INPUTS ARE 1-8, NOT 0-7

im planning it

so getting a valid move set for a piece is probably a piece. oh and it grabs it from the board with set pos or returns 
null if nothing is there. we probably need to check whats valid here or not?

a move is valid if it is a piece move for the piece at the input location(so we grab a list of piece moves) and if making that move would not leave the teams king in danger of check.

makeMove checks if that move is allowed? so we read the move then use validmoves and see if its in there?
this builds off of valid move and it also checks if its not the other teams turn in here

isInCheck is checking if a teams king is in check where like they gon die
its a bool if the king can be captured by an opposing piece

isInCheckmate is extra where it checks if you move you're cooked
and then isinStalemate is a copy of that which checks if you are cooked if your king moves at all

so i would write a helper function that takes all the moves the other teams pieces can make and blots all those spots out on the board "danger spots". then we can use that to see whatup. my brains kinda like what if its recursive check but i dont think its that deep.



TODO
validMoves
a move is valid if it is a piece move for the piece at the input location(so we grab a list of piece moves) and if making that move would not leave the teams king in danger of check.

so we grab a list of all moves we can make this turn on our team
then we cycle through each one
for each we make said move on the dummy board
then check if on the dummy board if the king is in check
if true, we need to remove that one from the list
then we return the cleaned list

the dummyboard we match, then we run the move of the piece in question, then we calc on dummy and see if it lands on king pos. the king pos can also be a move so we need to make a find king on dummy for this and run that on each move.

OH NVM VALIDMOVES IS JUST FOR ONE PIECE LOL LETS GO

MY STUFF
get team unvalidated moves
gets a list of all the positions the other teams pieces could move into. 

okay we can now get all the unvalidated moves a team can make on the dummy board, we can match the dummy board and we need to be able to move pieces on the board

now with our dummy moves and unvalidated movelist and dummy, we can use this to check of a teams king is in check or checkmate
pretty easily.

rule for dummyboard -> every use of dummyboard should begin and end with the dummyboard being matched to regular again

to check if we are in checkmate
we collect all valid moves of our pieces (helper func for this to collect all?)
we check if in check and length of that collection is 0

