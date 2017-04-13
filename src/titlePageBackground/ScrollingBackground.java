package titlePageBackground;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class Purpose: ScrollingBackground does much like its name: it
 * scrolls a background. This contains all components that concern
 * painting the scrolling mechanics of the title screen's background,
 * and uploading images to a buffer.
 * @see BackgroundImage
 */

public class ScrollingBackground extends Canvas implements Runnable {

    /*
     * reason for two backgrounds due to scrolling mechanism must scroll past image one,
     * resulting in second need for one of other background coexisting on current panel.
     */
    private BackgroundImage backgroundOne;
    private BackgroundImage backgroundTwo;

    private BufferedImage bufferedBackground;

    private final long imageSpeed = 50;

    //standard constructor
    public ScrollingBackground() {
        backgroundOne = new BackgroundImage();
        backgroundTwo = new BackgroundImage(backgroundOne.getImageWidth(), backgroundOne.getYPos());
        setSize(backgroundOne.getImageWidth() * 2, backgroundOne.getImageHeight());
        new Thread(this).start();
        setVisible(true);
    }

    /**
     * Continuously repaints screen with updated x position of background image
     * and delay's 'imageSpeed' in milliseconds to simulate a scrolling background effect.
     * Invariant: image never stops moving until removed from panel.
     */
    @Override
    public void run() {
        try {

            //repeats until user remove ScrollingBackground object by event listener
            while (true) {

                //delays image updates for scrolling effect
                Thread.currentThread().sleep(imageSpeed);

                repaint();

            }//while
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//run

    /**
     * Makes a graphics object to paint image. Checks if buffered version
     * of background image is null (create default image if so), draws
     * two versions of background image then paints with 2DGraphics.
     * @param g gets image drawing tools from java.awt
     */
    @Override
    public void update(Graphics g) {

        Graphics2D graphics2D = (Graphics2D)g;

        if (bufferedBackground == null)
            bufferedBackground = (BufferedImage)(createImage(getWidth(), getHeight()));

        Graphics buffer = bufferedBackground.createGraphics();

        //loading images into the buffer for output
        backgroundOne.draw(buffer);
        backgroundTwo.draw(buffer);

        paint(graphics2D);

    }//update

    /**
     * Draws image at specified location.
     * @param graphics2D 2D graphics added for continuous updated of image
     */
    private void paint(Graphics2D graphics2D) {
        graphics2D.drawImage(bufferedBackground, 0, 0, null);
    }
}//ScrollingBackground