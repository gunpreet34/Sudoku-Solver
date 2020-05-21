# Sudoku-Solver
Recursively solve Sudoku using Bruteforce, i.e, check if a position can be filled with the current given values of row and column. If not, revisit again.
Main function checks if Sudoku can be solved, if yes then prints the solved sudoku, otherwise, print Not Solvable

Constraints to be checked are:
* 1. Unique values in row and col.
* 2. Unique values in 3x3 square. 

If at a time a position in Sudoku can't be filled, fill it with 0.
