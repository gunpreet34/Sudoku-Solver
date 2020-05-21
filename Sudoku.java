/**
*  Bruteforce approach for Solving Sudoku
**/

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Sudoku {
    
    public static final int DIMENSIONS_SIZE = 9*9;

    private int[][] given;
    private int[][] solved;

    public Sudoku(int[][] map) {
        given = map;
        solved = deepCloneArray(map);
    }

    public void setSudokuMap(int[][] map) {
        given = map;
        solved = deepCloneArray(map);
    }

    public int[][] getSudokuMap() {
        return given.clone();
    }

    public String convertSudokuToString() {
        return Arrays.deepToString(given);
    }

    public int[][] getSolvedSudoku() {
        return solved.clone();
    }
    
    public String getSolvedSudokuAsString() {
        return Arrays.deepToString(solved);
    }

    /**
     * Check if input for necessary constraints:
     * 
     * 1. Unique values in row and col.
     * 2. Unique values in 3x3 square. 
     * pos - Position of input
     * value - Value of input
     * return => boolean - true/false based on constrains applicable
     */

    private boolean isFollowingConstraints(Point pos, int value) {

        // Unique in row
        for (int i = 0; i < Math.sqrt(DIMENSIONS_SIZE); i++) {
            if (solved[i][pos.y] == value) {
                return false;
            }
        }

        // Unique in column
        for (int i = 0; i < Math.sqrt(DIMENSIONS_SIZE); i++) {
            if (solved[pos.x][i] == value) {
                return false;
            }
        }

        // Unique in square
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (solved[(pos.x / 3) * 3 + i][(pos.y / 3) * 3 + j] == value) {
                    return false;
                }
            }
        }

        // If all constraints are followed, return true
        return true;
    }

    /**
     * Create a clone of an array
     * 
     * array - The array to be cloned
     * return => deep clone of the array
     */
    public static int[][] deepCloneArray(int[][] array) {
        int rows = array.length;

        // shallow copy of array
        int[][] newArray = (int[][]) array.clone();
        // deep copy of array
        for (int row = 0; row < rows; row++) {
            newArray[row] = (int[]) array[row].clone();
        }

        return newArray;
    }

    /**
     * Solve the sudoku board
     * Wrapper function for recursive function. 
     * 
     * 
     * return => boolean
     */
    public boolean solveSudoku() {
        return solveSudoku(0);
    }

    /**
     * Recursive method for inserting values to sudoku board.
     * 
     * i - Should be initialized with 0. Used by recursion
     * return => boolean - iff value is inserted. 
     */
    private boolean solveSudoku(int i) {
        
        if (i >= DIMENSIONS_SIZE) {
            return true;
        }
        
        int y = i / (int) Math.sqrt(DIMENSIONS_SIZE); // Find y 
        int x = i - y*(int) Math.sqrt(DIMENSIONS_SIZE); // find x 
        
        // Check for pre-filled values
        if(solved[x][y] != 0) {
            return solveSudoku(i+1);
        }
        
        for (int value = 1; value <= Math.sqrt(DIMENSIONS_SIZE); ++value) {
            if (!isFollowingConstraints(new Point(x, y), value)) {
                continue;
            }
            
            solved[x][y] = value;
            
            if (solveSudoku(i+1)) {
                return true;
            }
        }
        
        // Could not insert value. Reset value.        
        solved[x][y] = 0;
        return false;
    }

    public boolean checkSudoku() {

        // Check for valid board. 
        for (int i = 0; i < Math.sqrt(DIMENSIONS_SIZE); i++) {
            List<Integer> exsistsRow = new ArrayList<Integer>();
            List<Integer> exsistsCol = new ArrayList<Integer>();
            List<Integer> exsistsSquare = new ArrayList<Integer>();

            for (int j = 0; j < Math.sqrt(DIMENSIONS_SIZE); j++) {

                if (solved[i][j] == 0 || solved[j][i] == 0) {
                    return false;
                }
                if (exsistsRow.contains(solved[i][j])) {
                    return false;
                }


                if (exsistsCol.contains(solved[j][i])) {
                    return false;
                }

                exsistsRow.add(solved[i][j]);
                exsistsCol.add(solved[j][i]);

                if (j < 3) {
                    for (int k = 0; k < 3; k++) {
                        int val = solved[j + 3 * (i % 3)][k + 3 * (i / 3)];
                        if (exsistsSquare.contains(val)) {
                            return false;
                        }
                        exsistsSquare.add(val);
                    }
                }
            }

        }

        return true;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int[][] inputSudoku = new int[9][9];
        for(int i=0; i<9;i++){            
            for(int j=0; j<9;j++){
                inputSudoku[i][j]=sc.nextInt();
            }
        }

        Sudoku solver = new Sudoku(inputSudoku);
        solver.solveSudoku();
        
        System.out.println("Given Sudoku is:\n" + solver.convertSudokuToString());
        boolean isSolvable = solver.checkSudoku();
        if(isSolvable){
            System.out.println("\nSolving....\n");
            System.out.println(solver.getSolvedSudokuAsString());
        }else{
             System.out.println("Not Solvable\n");
        }
    }
}