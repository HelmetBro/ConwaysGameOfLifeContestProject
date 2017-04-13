import java.util.ArrayList;

/**
 * Class Purpose: Board class hold all of the Cell objects created
 * inside a 2D array, simulating something like a chess board. Cells
 * could be considered the chess pieces, as they have separate
 * characteristics and take only one space. Methods providing
 * information concerning data in the Board class are held here.
 */

class Board {

    //width and height (size) of board array
    private int xBoardSize;
    private int yBoardSize;

    //2-Dimensional array where cells are held (considered, "board")
    private Cell[][] cellArray;

    //default constructor with default values of xBoardSize yBoardSize
    Board() {
        this(100, 100);
        setCellArray(new Cell[getXBoardSize() + 1][getYBoardSize() + 1]);
    }

    //standard constructor
    Board(int xBoardSize, int yBoardSize) {
        this.xBoardSize = xBoardSize;
        this. yBoardSize = yBoardSize; // delete space
        this.cellArray = new Cell[getXBoardSize() + 1][getYBoardSize() + 1];
    }

    //copy constructor
    Board(Board board) {
        this.xBoardSize = board.getXBoardSize();
        this.yBoardSize = board.getYBoardSize();
        this.cellArray = board.getCellArray();
    }

    /**
     * Inputs cell into specified location by cell x and y location
     * Precondition: param cell not equal to null
     * Postcondition: location in board array is filled with param cell
     * @param cell used for locating specific x and y coordinates
     */
    void addCell(Cell cell){
        getCellArray()[cell.getXLoc()][cell.getYLoc()] = cell;
    }

    /**
     * Iterates through array while checking for active cells,
     * as well keeping count of total number of living cells.
     * @return number of total cells in board array
     */
    int numOfTotalCells(){

        int countOfActives = 0;

        for (Cell[] a : getCellArray())
            for (Cell b : a)
                if(b != null)
                    countOfActives++;

        return countOfActives;

    }//numOfTotalCells

    /**
     * Sets index in array denoted by cell x and y locations within
     * array to null, clearing that space for future input.
     * Precondition: param cell not equal to null
     * Postcondition: location in board array is null
     * @param cell used for locating specific x and y coordinates
     */
    void killCell(Cell cell){
        killCell(cell.getXLoc(), cell.getYLoc());
    }//killCell

    void killCell(int x, int y){
        getCellArray()[x][y] = null;
    }//killCell
// remove killCell
    /**
     * Gets number of living cells around param cell input by finding
     * specific x and y locations within cell. Then precedes to initialize the
     * count of total number of living cells. To count surrounding cells,
     * values x and y are decreased by 1 to move the iterator to therefore
     * accompany all surrounding areas of cell. Iterations through all
     * spaces begin, adding one count per cell not equal to null.
     * Precondition: cell must not be null
     * Postcondition: total number of cells is >= 0
     * @param cell gets specific x and y values
     * @return total number of living surrounding cells
     */
    int numOfLivingCells(Cell cell){
        int x = cell.getYLoc();
        int y = cell.getYLoc();
        return numOfLivingCells(x, y);
    }
    int numOfLivingCells(int x, int y){

        //x and y are the starting positions of the coordinates

        x--;
        y--;

        int totalNumOfLivingCells = 0;

        //i and k are the iteration numbers related to the original cell's position- x and y

        //iterates through top, middle, and bottom 3 cells
        for(int i = x; i < (x + 3); i++)
            for (int k = y; k < (y + 3); k++){

                //ensures ArrayIndexOutOfBoundsException is not called
                if (!(i > -1) || !(k > -1))
                    continue;

                //checks if cell is active, if so, add to count
                if(getCellArray()[i][k] != null)
                    totalNumOfLivingCells++;

            }//nested for loop

        //one is subtracted to not include original cell
        return totalNumOfLivingCells - 1;

    }//numOfLivingCells

    /**
     * Gets an ArrayList of free spaces (spaces not taken by other cells)
     * surrounding the param cell input by iterating through all surrounding
     * spaces, locating spaces where iteration equals null, precedes to add
     * location into array with specified coordinates.
     * Precondition: cell is not equal to null
     * Postcondition: Array contains x and y locations of free spaces in array
     * @param cell used to specify coordinates of cell in board array
     * @return ArrayList of all available (free) in surrounding spaces of cell
     */
    ArrayList<Integer[]> listOfAvailableCells(Cell cell){
        int x = cell.getYLoc();
        int y = cell.getYLoc();
        return listOfAvailableCells(x, y);
    }
    ArrayList<Integer[]> listOfAvailableCells(int x, int y){

        ArrayList<Integer[]> freeSpaces = new ArrayList<>();

        //x and y are the starting positions of the coordinates

        //moves pointer to upper left coordinate of cell
        x--;
        y--;

        //i and k are the iteration numbers related to the original cell's position- x and y

        //iterated through all 8 surrounding cells
        for(int i = x; i < (x + 3); i++)
            for (int k = y; k < (y + 3); k++){

                //ensures ArrayIndexOutOfBoundsException is not called
                if (!(i > -1) || !(k > -1))
                    continue;

                //creates ArrayList to store coordinates of open spaces
                if(getCellArray()[i][k] == null) {
                    Integer[] coordinate = new Integer[2];
                    coordinate[0] = i;
                    coordinate[1] = k;
                    freeSpaces.add(coordinate);

                }
            }//nested for loop

        return freeSpaces;

    }//listOfAvailableCells

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Board board = (Board) obj;
        return getXBoardSize() == board.getXBoardSize() &&
               getYBoardSize() == board.getYBoardSize() &&
               getCellArray() == board.getCellArray();
    }

    @Override
    public String toString(){
        return "X array size: " + getXBoardSize() + "\nY array size: " + getYBoardSize();
    }

    //accessor and mutator methods for instance variables
    public int getXBoardSize(){  return this.xBoardSize;  }
    public void setXBoardSize(int xBoardSize) {  this.xBoardSize = xBoardSize;  }

    public int getYBoardSize(){  return this.yBoardSize;  }
    public void setYBoardSize(int yBoardSize) {  this.yBoardSize = yBoardSize;  }

    public Cell[][] getCellArray() {  return cellArray;  }
    public void setCellArray(Cell[][] cellArray) {  this.cellArray = cellArray;  }

}//Board
