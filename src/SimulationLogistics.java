import java.util.ArrayList;
import java.util.Random;

/**
 * Class Purpose: This contains all of the logistics of the simple
 * algorithm used by this program. Everything that manipulates data
 * is held in this class. Algorithm's and patterns are found in
 * methods that change data in Cell and Board classes.
 */

final class SimulationLogistics {

    /**
     * Constants determining where a cell has a certain number of other Cells surrounding it
     * to reproduce, and a certain number of Cells surrounding it, to die.
     * NumToReproduce: higher numbers make it easier for cells to reproduce,
     * numbers 2 and 1 will create patterns where no further expansion is possible.
     * NumToDie: Lower numbers will kill Cell's easier.
     * Set numToDie to 9 or greater watch only cell reproduction.
     */
    private static final int numToReproduce = 5; //5 or less
    private static final int numToDie = 7; //7 or greater

    /*
     * Able to set custom delays between cycles of birth and death, as well
     * as delay's between completions of cycles themselves. Measure in milliseconds.
     */
    private static final long delayBetweenCycles = 500;
    private static final long delayBirthCycle = 5;
    private static final long delayDeathCycle = 5;
    private static final long delayPerEveryDeathCellAnalyzed = 0;
    private static final long delayPerEveryBirthCellAnalyzed = 0;

    //Thread initialized as instance variable to carry thread to different class
    static Thread gameOccurrences;

    //boolean to determine when game should stop running
    private static boolean keepGameRunning = true;

    /**
     * Creates a starting Cell on board[][] so therefore the algorithm has a Cell to start on.
     * @param x gets X size of board[][] to randomize position with those restrictions
     * @param y gets y size of board[][]
     * @return new Cell representing starting randomized Cell
     */
    static Cell starterCell(int x, int y){
        Random gen = new Random();
        int randomStartX = gen.nextInt(x);
        int randomStartY = gen.nextInt(y);
        return new Cell(randomStartX, randomStartY, true);
    }

    /**
     * The algorithm of this system- Creates a loop where death
     * and birth cycles are called with customized delays in between. Runs
     * on a separate thread called by ActionEvent when fade animation completes.
     * Precondition: starter cell is created to act up on
     * Invariant: birth and death cycles are called continuously
     * @param board used to pass through onto both cycles
     * @param UI passes through to death/birth cycles
     */
    static void runSimulation(Board board, UserInterface UI){
        gameOccurrences = new Thread(new Runnable() {
            public void run() {
                try{

                    //runs until value is false
                    while(SimulationLogistics.keepGameRunning) {

                        SimulationLogistics.birthCycle(board, UI);

                        Thread.sleep(delayBetweenCycles);

                        SimulationLogistics.deathCycle(board, UI);

                        Thread.sleep(delayBetweenCycles);

                    }

                }catch (InterruptedException exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

    }//runSimulation

    /**
     * While iterating through the board, gets number of living cells surrounding
     * Cell, if greater or equals to specified kill number, Cell is erased from
     * board and visual is updated. Thread delays are provided to simulate a
     * growing pattern.
     * Postcondition: Cells from board[][] are surrounded by less than overcrowding number
     * @param board finds each cell within param to determine death/life
     * @param UI calls visual update for each cell
     */
    static void deathCycle(Board board, UserInterface UI){
        for(int i = 0; i < board.getXBoardSize(); i++)
            for(int k = 0; k < board.getYBoardSize(); k++)
                try {

                    //gets number of living cells around current cell positing
                    int numOfLivingCells = board.numOfLivingCells(i, k);

                    //if greater/equal to overcrowding number, dies
                    if(numOfLivingCells >= numToDie){

                        //wipes Cell memory from board
                        board.killCell(i, k);

                        //updates visual of new change
                        UI.updateVisualDeathCell(i, k);

                        Thread.currentThread().sleep(delayDeathCycle);
                    }

                    Thread.currentThread().sleep(delayPerEveryDeathCellAnalyzed);

                } catch (InterruptedException exception) {
                    System.out.println(exception.getMessage());
                }
    }//deathCycle

    /**
     * Iterates through board[][] and finds active living cells in order. Creates
     * ArrayList to hold all coordinates, determines if number of cell's around
     * are less than/equal to reproduction number, randomly selects placement for
     * new born Cell. Updates board[][] and visual of the new change, with custom delay.
     * Invariant: board size remains constant- adds a single cell
     * PostCondition: board has been analyzed with updated data and visual
     * @param board finds each cell within param to determine death/life
     * @param UI calls visual update for each cell
     */
    static void birthCycle(Board board, UserInterface UI){
        for(int i = 0; i < board.getXBoardSize(); i++)
            for(int k = 0; k < board.getYBoardSize(); k++)
                try {

                    if(board.getCellArray()[i][k] != null) {

                        ArrayList<Integer[]> listOfCells = board.listOfAvailableCells(i, k);

                        int numCellsAround = listOfCells.size();

                        //determines if Cell at (i, k) has enough cells around to reproduce
                        if ((8 - numCellsAround) <= numToReproduce) {

                            Random gen = new Random();

                            //randomly generates index between 0 and number of cells surrounding Cell
                            int placement = gen.nextInt(numCellsAround);

                            //gets x and y point at randomized index and creates new cell
                            Cell cell = new Cell(listOfCells.get(placement)[0], listOfCells.get(placement)[1], true);

                            //board updates changes with new cell
                            board.addCell(cell);

                            //visual is updated
                            UI.updateVisualBirthedCell(cell);

                            Thread.currentThread().sleep(delayBirthCycle);
                        }

                        Thread.currentThread().sleep(delayPerEveryBirthCellAnalyzed);

                    }
                } catch (InterruptedException exception) {
                    System.out.println(exception.getMessage());
                }//catch
    }//birthCycle

}//SimulationLogistics
