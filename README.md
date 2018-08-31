# Java-Examples
Request examples of my Java code here.

## MineSweeper.java and Boardbutton.java

**As noted in the placeholder file, please contact me at cwlong@okstate.edu or linkedin.com/in/chad-long-3b1543100 to see this code**

MineSweeper creates a simplified version of the classic MineSweeper game with the Boardbutton class as the buttons used in the game grid. A GUI will pop up after execution for you to play. If you left-click or right-click the exact middle of the buttons, you will start to see the functionality play out with the numbers, mines (referred to as bombs), and flags. If you mark all the bombs with a flag by right-clicking, you will see a "You won" message print out on your console. Please exit out and restart to play again.

To compile and run, just make sure that Boardbutton.java is in the same location as Minesweeper.java. Enjoy!

## Puzzlesolver.java

**As noted in the placeholder file, please contact me at cwlong@okstate.edu or linkedin.com/in/chad-long-3b1543100 to see this code**

This program solves a simple 4x4 puzzle using a basic recursive swapping algorithm. A solved puzzle looks like the following:
```
ABCD
EFGH
IJKL
MNO.
```

All letters are in alphabetical order with the "." character in the bottom right corner. An input file to my code would be a txt file with exactly 4 lines of 4 characters each that are from the puzzle seen above. As in, the characters from A to O and the "." character make up the allowable characters. The solution printed to console consists of the coordinates of which characters are swapped and in what order along with the total number of moves. 

For example, an input puzzle with the "O" and "." swapped would result in this printout for the solution:
```
(3,2) -> (3,3)
1 moves
```

Feel free to create your own txt input files as well as modifying the code to solve larger puzzles.
