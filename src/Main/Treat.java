package Main;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

/**
 * class implementing treats, pacman's collectible
 */
public class Treat extends Entity {
    /** array of file paths of treat images */
    static final String[] TREAT_FILES = {"../treats/candy_1.png", "../treats/candy_2.png", "../treats/candy_3.png",
            "../treats/candy_4.png"};
    /** map object */
    GamePanel map;

    /**
     * constructor
     * @param map map object
     * @param position Point with positions in pixels
     */
    public Treat(GamePanel map, Point position) {
        this.map = map;
        this.position = new Point(position.x + GamePanel.PIXEL / 4, position.y + GamePanel.PIXEL / 4);
        this.getImage();
    }

    /**
     * loads random treat image
     */
    public void getImage() {
        try {
        int file_idx = new Random().ints(1, 0, 4).findFirst().getAsInt();
        this.frame1 = ImageIO.read(this.getClass().getResourceAsStream(TREAT_FILES[file_idx]));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    /**
     * draws treat in game window
     * @param element2d Graphics2D from java.awt.Graphics
     */
    public void draw(Graphics2D element2d) {
        element2d.drawImage(frame1, this.position.x, this.position.y, GamePanel.PIXEL / 2, GamePanel.PIXEL / 2, null);
    }

    /**
     * calculates center position of treat
     * @return position in pixels as Point
     */
    @Override
    public Point getCenter() { return new Point(position.x + GamePanel.PIXEL / 4, position.y + GamePanel.PIXEL / 4); }

    /**
     * calculates center position of treat
     * @return position in tiles as Point
     */
    @Override
    public Point getCenterTile() {
        return new Point((position.x + GamePanel.PIXEL / 4) / GamePanel.PIXEL, (position.y + GamePanel.PIXEL / 4) / GamePanel.PIXEL);
    }

}
