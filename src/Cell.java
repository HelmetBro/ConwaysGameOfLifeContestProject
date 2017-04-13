import java.awt.Color;

/**
 * Class Purpose: This class is designed to replicate properties of Cells.
 * Given properties such as the state of being alive, x and y coordinates,
 * and a color, Cell class acts like. Many instances of Cells will be
 * created to be displayed on a 2D board array to reenact growth patterns.
 */

class Cell {

    //indication of whether cell is considered "alive"
    private boolean isAlive;

    //location of cell within array
    private int xLoc;
    private int yLoc;

    //color assigned to all cells
    private Color color;

    //default constructor
    public Cell(){
        this(0, 0, false, new Color(15, 190, 15)); // Green
    }

    //standard constructor, DEFAULT color
    Cell(int xLoc, int yLoc, boolean isAlive){
        this(xLoc, yLoc, isAlive, new Color(15, 190, 15));
    }

    //standard constructor
    Cell(int xLoc, int yLoc, boolean isAlive, Color color){
        this.isAlive = isAlive;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.color = color;
    }

    //copy constructor
    public Cell(Cell cell){
        this.isAlive = cell.isAlive();
        this.xLoc = cell.getXLoc();
        this.yLoc = cell.getYLoc();
        this.color = cell.getColor();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        if (!(obj instanceof Cell))
            return false;
        Cell cell = (Cell) obj;
        return getXLoc() == cell.getXLoc() &&
               getYLoc() == cell.getYLoc() &&
               getColor().getRed() == cell.getColor().getRed() &&
               getColor().getGreen() == cell.getColor().getGreen() &&
               getColor().getBlue() == cell.getColor().getBlue();
    }

    @Override
    public String toString(){
        return "X index: " + getXLoc() +
               "\nY index: " + getYLoc() +
               "\nColor (rgb): " + getColor().getRed() +
               ", " + getColor().getGreen() +
               ", " + getColor().getBlue();
    }

    //accessor and mutator methods for instance variables
    public boolean isAlive() {  return isAlive;  }
    public void setAlive(boolean alive) {  isAlive = alive;  }

    public int getXLoc() {  return xLoc;  }
    public void setXLoc(int xLoc) {  this.xLoc = xLoc;  }

    public int getYLoc() {  return yLoc;  }
    public void setYLoc(int yLoc) {  this.yLoc = yLoc;  }

    public Color getColor() {  return color;  }
    public void setColor(Color color) {  this.color = color;  }

}//Cell