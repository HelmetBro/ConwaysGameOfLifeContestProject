import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class Purpose: UserInterface create the JFrame holding all of the visual
 * Components for this program to work. It contains many methods that
 * manipulate the visual display for an interesting experience for the
 * user. When instantiated, a new game is created along with other visual
 * effects.
 */

class UserInterface extends JFrame{

    /**
     * UserInterface class uses many math formulas and constants with
     * absolute positioning. With experience using the standard java library
     * BoarderLayout, GridLayout, and FlowLayout, the amount of freedom given
     * by each is restricted by design. Often times, using those pre-made
     * Java layouts never allows for a certain Component to be placed in the exact
     * location you want. Using absolute positioning, this opens up the
     * to exact placement of an object, which can be easily manipulated by variables
     * and equations throughout this program.
     * @see UserInterface examples of absolute positioning
     */

    /*
    * because of modular programing, changing these values do not change
    * aspect ratios of buttons, cells, titles, or backgrounds.
    */
    private final int WINDOW_WIDTH = 1249;
    private final int WINDOW_HEIGHT = 750;

    /*
     * JFrame is encased in backgroundPanel, allows for simple addition
     * and removal of components, without excessive calls to the java api validate().
     */
    private JPanel backgroundPanel;
    private JLabel fadeShade;

    //basic colors of simulation board and line color
    private Color backgroundColor = new Color(100, 100, 100); // Light Gray
    private Color lineColor = new Color(65, 65, 65); // Dark Gray

    /*
     * three Containers for title screen, fade animation in between the
     * title screen and game board (activated by java's ActionListener),
     * and container for game screen to hold the cells.
     */
    private Container titleScreenContainer;
    private Container fadeAnimationContainer;
    private Container gameContainer = new Container();

    //simple constants allow for easy manipulation of simulation
    private final int cellSizeX = 17;
    private final int cellSizeY = 17;
    private final int lineSize = 1;

    //determines appropriate number of total cells based on window size
    private final int numOfCellsOnX = (WINDOW_WIDTH - lineSize) / (cellSizeX + lineSize);
    private final int numOfCellsOnY = (WINDOW_HEIGHT - lineSize) / (cellSizeY + lineSize);

    /**
     * In order to create a parameter that uses class UserInterface in class specific
     * methods, an instantiation of UserInterface must be created. Due to rules by
     * Java's scope, Object "this" cannot be passed through non-object dependant
     * static inner classes, but an instance variable may pass through, therefore
     * requiring the creation of UserInterface, 'this'.
     */
    private UserInterface UI;

    /**
     * Creates a JFrame (User Interface) with basic properties. Calls
     * createStartScreen() initializing stage for an aesthetic start screen.
     */
    UserInterface(){
        super("idk");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setResizable(false);
        setLocationRelativeTo (null);
        createStartScreen();
        UI = this;
        add(backgroundPanel);
        setVisible(true);
    }//UserInterface

    /**
     * Creates a start screen with a title (JLabel), JButton, and background by
     * making independent objects, adding them to a Container, and adding
     * the Container to JPanel. Adds action listener to JButton.
     * @see titlePageBackground.ScrollingBackground
     * @see UserInterface
     */
    private void createStartScreen(){

        //creating new background JPanel to encompass JFrame
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setName("backgroundPanel");
        backgroundPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        //creating Container to hold title label and begin button
        titleScreenContainer = new Container();
        titleScreenContainer.setName("titleScreenContainer");
        titleScreenContainer.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        //creating JLabel title
        JLabel titleLabel = new JLabel("idk");
        titleLabel.setName("idk");
        titleLabel.setBackground(new Color(20, 20, 20)); //colors
        titleLabel.setForeground(new Color(65, 65, 65));
        titleLabel.setOpaque(true);
        titleLabel.setFont(new Font("Consolas", Font.PLAIN, 250));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        titleLabel.setBounds(WINDOW_WIDTH / 4, WINDOW_HEIGHT / 20, (WINDOW_WIDTH / 4) * 2, WINDOW_WIDTH / 5);
        titleLabel.setName("titleLabel");

        //creating JButton begin
        int beginSizeX = (WINDOW_WIDTH / 25) * 7;
        int beginSizeY = WINDOW_HEIGHT / 15;
        JButton beginButton = new JButton("Begin");
        beginButton.setFont(new Font("Consolas", Font.PLAIN, 25));
        beginButton.setBounds((WINDOW_WIDTH / 2) - (beginSizeX / 2),
                (WINDOW_HEIGHT / 2) - (beginSizeY / 2), beginSizeX, beginSizeY);
        beginButton.setBackground(new Color(160, 160, 160));
        beginButton.setFocusable(false);
        beginButton.addActionListener(new BeginButtonListener());
        beginButton.setName("beginButton");

        //adding two basic Components to container
        titleScreenContainer.add(titleLabel);
        titleScreenContainer.add(beginButton);

        titlePageBackground.ScrollingBackground background = new titlePageBackground.ScrollingBackground();
        titleScreenContainer.add(background);  //adds background to Container

        //adds Container to background JPanel
        backgroundPanel.add(titleScreenContainer);

    }//startScreen

    /**
     * Private innerclass of UserInterface that activates on (begin) button ActionListener,
     * implements ActionListener overrides actionPerformed() to perform game staring process.
     */
    private class BeginButtonListener implements ActionListener {

        /**
         * Clears screen by removing debris (title screen) Container. Calls a fade
         * animation then precedes to setup game. Creates Board object to store cells
         * then adds a Cell of random placement to start algorithm. Once completed,
         * game is set to run on repeating algorithm.
         * Precondition: title screen is running, button just pressed. Board is clear
         * with no Cell object in array.
         * Postcondition: game screen is created with board and random starter cell,
         * algorithm in action acting on starter cell.
         * @see SimulationLogistics runs game analytics
         * @param e object where Java API calls next step in action
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            backgroundPanel.remove(titleScreenContainer);
            fadeToGameScreen();

            gameContainer.setName("gameContainer");
            gameContainer.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

            Board board = new Board(getNumOfCellsOnX(), getNumOfCellsOnY());

            //creating starter cell with random coordinates to kick-off algorithm
            Cell startCell = SimulationLogistics.starterCell(getNumOfCellsOnX(), getNumOfCellsOnY());
            board.addCell(startCell);

            //updates the JFrame of new Component to visualize board[][]
            updateVisualBirthedCell(startCell);

            //calls the algorithm
            SimulationLogistics.runSimulation(board, UI);
        }
    }//BeginButtonListener

    /**
     * Sets up fadeAnimationContainer instance variable to hold new JLabel.
     * JLabel is created for fading animation run by separate thread, consists
     * of changing text and colors.
     * Precondition: BeginButtonListener is called, titleScreenContainer is removed
     * Postcondition: fadeAnimationContainer is removed, thread is started
     * @see Thread starts separate thread to manipulate fadeShade JLabel
     */
    void fadeToGameScreen(){

        //creating container so hold animation
        fadeAnimationContainer = new Container();
        fadeAnimationContainer.setName("fadeAnimationContainer");
        fadeAnimationContainer.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        //JLabel with later manipulable properties acting like fade animation
        fadeShade = new JLabel("creating life", JLabel.CENTER);
        fadeShade.setOpaque(true);
        fadeShade.setName("fadeShade");
        fadeShade.setFont(new Font("Felix Titling", Font.BOLD, 26));
        fadeShade.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        /**
         * Starts the fade animation by changing color and text values of JLabel
         * fadeShade. Once completed, removes Container from JPanel background
         * and creates new ActionEvent to start logistics.
         * Precondition: background panel, container, and JLabel created
         * Postcondition: new ActionEven is created and called, debris removed
         * @throws InterruptedException catches when thread cannot sleep
         * @see GameStartListener throws new ActionEvent command to start game
         */
        Thread fadeThread = new Thread(new Runnable() {
            public void run() {

                //repeats 255 times, enough for color spectrum to change
                for (int i = 0; i < 255; i++) {
                    try {

                        //values change in accordance to i, simulating a 'fade' effect
                        fadeShade.setForeground(new Color(i, i, i));
                        fadeShade.setBackground(new Color(255 - i, 255 - i, 255 - i));

                        //adds a " ." to the text for a 'pending' animation
                        if(i == 70 || i == 140 || i == 210)
                            fadeShade.setText(fadeShade.getText() + " .");

                        //controls the 'fade' speed
                        Thread.currentThread().sleep(10);

                    } catch (InterruptedException exception) {
                        System.out.println(exception.getMessage());
                    }//catch
                }//for

                //removes fade screen and repaint to show panel background
                backgroundPanel.remove(fadeAnimationContainer);
                repaint();

                //set off GameStartListener
                GameStartListener gameStartListener = new GameStartListener();
                gameStartListener.actionPerformed(new ActionEvent(fadeAnimationContainer, 0, "Start Game"));

            }//run
        });

        fadeAnimationContainer.add(fadeShade);
        backgroundPanel.add(fadeAnimationContainer);

        //starting the fade animation
        fadeThread.start();

    }//fadeToGameScreen

    /**
     * When instantiated, create a game panel with default board cell
     * and line size with appropriate dimensions.
     * Precondition: JFrame object created
     * Postcondition: cell visualization background panel initialized
     * @see UserInterface calls BackgroundPanel in constructor
     */
    private class BackgroundPanel extends JPanel {

        /**
         * Whenever BackgroundPanel is instantiated, a board with
         * cell graphics is created for a backboard used by game.
         * @param g used to draw simple java.awt graphics
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            setBackground(backgroundColor);

            g.setColor(lineColor);

            //drawing vertical lines
            for (int lineNumVertical = 0; lineNumVertical <= numOfCellsOnX; lineNumVertical++)
                g.fillRect((cellSizeX + lineSize) * lineNumVertical, 0, lineSize, WINDOW_HEIGHT);

            //drawing horizontal lines
            for (int lineNumHorizontal = 0; lineNumHorizontal <= numOfCellsOnY; lineNumHorizontal++)
                g.fillRect(0, (cellSizeX + lineSize) * lineNumHorizontal, WINDOW_WIDTH, lineSize);

        }//paintComponent

    }//BackgroundPanel

    /**
     * Creates a new cell with location properties of x and y. Calls
     * overloaded method updateVisualDeathCell() that: Instantiates
     * Component at specific x and y values in gameContainer. Once
     * object is found, calls gameContainer to remove Component, which
     * then updates the interface by repaint().
     * @param x 'X' location of new cell
     * @param y 'Y' location of new cell
     */
    void updateVisualDeathCell(int x, int y){
        updateVisualDeathCell(new Cell(x, y, true));
    }

    void updateVisualDeathCell(Cell cell){

        Component componentToRemove = gameContainer.getComponentAt((cell.getXLoc() * (cellSizeX + lineSize)) + 1,
                (cell.getYLoc() * (cellSizeY + lineSize)) + 1);

        gameContainer.remove(componentToRemove);

        repaint();

    }//updateVisualDeathCell

    /**
     * Calls updateVisualBirthedCell() with param's x and y by creating
     * a new Cell. Creates a JLabel with properties of Cell, using
     * absolute positioning and simple math to appoint location to
     * specific coordinate on JFrame. Adds JLabel to Container, which
     * is then added to the background JPanel. Repaints to show updates.
     * The use of JLabel and not java.awt is because JLabel allows a wide
     * array of customization to the background such as painting Cell numbers,
     * giving Cell's borders, changing colors, etc. Using java.awt (as
     * simple at it is) doesn't allow for those manipulations.
     * @param x 'X' location of new cell
     * @param y 'Y' location of new cell
     */
    void updateVisualBirthedCell(int x, int y){
        updateVisualBirthedCell(new Cell(x, y, true));
    }

    void updateVisualBirthedCell(Cell cell){

        //creating JLabel to act as a cell
        JLabel UICell = new JLabel();
        UICell.setName(cell.toString());
        UICell.setOpaque(true);
        UICell.setBackground(cell.getColor());

        //bounds set in relation to passed parameter's coordinates
        UICell.setBounds((cell.getXLoc() * (cellSizeX + lineSize)) + 1,
                (cell.getYLoc() * (cellSizeY + lineSize)) + 1, cellSizeX, cellSizeY);

        //adding JLabel/JPanel and repainting JFrame
        gameContainer.add(UICell);
        backgroundPanel.add(gameContainer);
        repaint();
    }

    //only appropriate accessors for UserInterface class
    public int getNumOfCellsOnX(){  return this.numOfCellsOnX;  }

    public int getNumOfCellsOnY(){  return this.numOfCellsOnY;  }
}//UserInterface