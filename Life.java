/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cao_life;

/**
 *
 * @author caoa5300
 */
public class Life implements LifeInterface {

    private int[][] grid;
    private final int[][] neighbours = {{-1, -1}, {-1, 0}, {-1, +1}, {0, -1}, {0, +1}, {+1, -1}, {+1, 0}, {+1, +1}};

    /**
     * Initializes an empty blank grid
     * pre: Life object is instantiated
     * post: Life object is created with a inputted grid
     * 
     * @param size sets grid dimensions
     */
    public Life(int x, int y) {
        grid = new int[x][y];
    }

    /**
     * Initializes the grid to a set pattern
     * pre: Life object is instantiated with parameters
     * post: Life object is created with set grid
     * @param g 
     */
    public Life(int[][] g) {
        grid = g;
    }

    /**
     * Set all grid cells to blank (0)
     * pre: none
     * post: all cells are set to 0
     */
    @Override
    public void killAllCells() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = 0;
            }
        }
    }

    /**
     * Loads a start pattern to the grid
     * pre: none 
     * post: pattern is set
     *
     * @param startGrid a int [][] loaded with 1's and 0's
     */
    @Override
    public void setPattern(int[][] startGrid) {
        grid = startGrid;
    }

    /**
     * Counts and returns how many adjacent cells are alive 
     * pre: none 
     * post: number of neighbours is returned
     *
     * @param cellRow = row address of test cell 0 < cellRow < gridSize - 1
     * @param cellCol = column address of test cell 0 < cellCol < gridSize - 1
     * @return int count of adjacent live cells
     */
    @Override
    public int countNeighbours(int cellRow, int cellCol) {
        int count = 0;
        for (int[] i : neighbours) {
            if (grid[cellRow + i[1]][cellCol + i[0]] == 1) {
                count++;
            }
        }
        return count;
    }

    /**
     * Applies the rules of game of life to a specific cell and returns the
     * state of the cell afterwards 
     * pre: none 
     * post: state is returned
     *
     * @param cellRow = row address of test cell * 0 < cellRow < gridSize - 1
     * @param cellCol = column address of test cell 0 < cellCol < gridSize - 1
     * @return int = state of cell, 1 for live, 0 for dead
     */
    @Override
    public int applyRules(int cellRow, int cellCol) {
        if (countNeighbours(cellRow, cellCol) > 3 || countNeighbours(cellRow, cellCol) < 2) {
            return 0;
        } else if (grid[cellRow][cellCol] == 0 && countNeighbours(cellRow, cellCol) == 3) {
            return 1;
        } else if (grid[cellRow][cellCol] == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Moves the game ahead one step by reading the previous grid, applying the
     * rules, and creating a new grid. 
     * pre: none 
     * post: game is advanced one step
     */
    @Override
    public void takeStep() {
        int[][] tempGrid = new int[grid.length][grid[0].length];

        for (int row = 1; row < tempGrid.length - 1; row++) {
            for (int column = 1; column < tempGrid[0].length - 1; column++) {
                tempGrid[row][column] = applyRules(row, column);
            }
        }
        grid = tempGrid;
    }

    /**
     * Returns the int value of a specific cell pre: none post: int value is
     * returned
     *
     * @param i
     * @param j
     * @return
     */
    public int getGrid(int i, int j) {
        return grid[i][j];
    }

    /**
     * Creates and returns a string representation of the grid 
     * pre: none 
     * post: String version of grid is returned
     *
     * @return String
     */
    @Override
    public String toString() {
        String temp = "";

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                temp += grid[row][column] + " ";
            }
            temp += "\n";
        }
        return temp;
    }
}
