package titlePageBackground;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Class Purpose: To create a scrolling background image, a class is
 * needed to hold one. This class contains properties of holding the
 * image, and mechanics of moving the background.
 */

class BackgroundImage {

    private BufferedImage image;
    private int xPos;
    private int yPos;

    //default constructor
    BackgroundImage() {
        this(0,0);
    }

    //standard constructor
    BackgroundImage(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;

        try {

            //grabs image from package and instantiates instance variable to image object
            String imageFilePath = System.getProperty("user.dir") + "\\src\\titlePageBackground\\pix.png";

            image = ImageIO.read(new File(imageFilePath));

        }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }

    /**
     * Draws image based on location and size, moves x position back one pixel to
     * simulate a moving background (delay is found in ScrollingBackground). If
     * 100% of the image is past the JFrame, image is moved to the right side to
     * repeat process.
     * Invariant: image x position never null, always moving
     * @param g graphics object used to image visuals on JPanel
     * @see ScrollingBackground
     */
    void draw(Graphics g) {
        g.drawImage(image, getXPos(), getYPos(), image.getWidth(), image.getHeight(), null);

        this.xPos -= 1;

        if (this.xPos <= -1 * image.getWidth())
            this.xPos = this.xPos + image.getWidth() * 2;
    }//draw

    @Override
    public String toString() {
        return "BackgroundImage: xPos = " + getXPos() + ", yPos = " + getYPos() +
                ", height = " + image.getHeight() + ", width = " + image.getWidth();
    }

    public int getXPos() {  return this.xPos;  }
    public void setXPos(int xPos) {  this.xPos = xPos;  }

    public int getYPos() {  return this.yPos;  }
    public void setYPos(int yPos){  this.yPos = yPos;  }

    public int getImageWidth() {  return image.getWidth();  }
    public int getImageHeight() {  return image.getHeight();  }

}//BackgroundImage