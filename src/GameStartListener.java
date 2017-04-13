import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class Purpose: When activated, this class starts a thread
 * created in SimulationLogistics that runs the algorithm.
 * This event is called after the fade animation is displayed.
 * @see SimulationLogistics
 */

class GameStartListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        SimulationLogistics.gameOccurrences.start();
    }

}//GameStartListener