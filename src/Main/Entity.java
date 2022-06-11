//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.image.BufferedImage;


/**
 * class implementing game entity
 */
public class Entity {
    /** game panel object */
    GamePanel game_panel;
    /** position of entity in pixels */
    public Point position = new Point();
    /** first frame image */
    public BufferedImage frame1;
    /** second frame image */
    public BufferedImage frame2;
    /** frame counter used to change animation frames */
    int frameCounter = 0;
    /** counter for counting frames */
    int counter = 0;

    /**
     * get center of entity
     * @return tile
     */
    public Point getCenter() { return new Point(position.x + GamePanel.PIXEL / 2, position.y + GamePanel.PIXEL / 2); }

    /**
     * get tile of top left point
     * @return tile
     */
    public Point getTile() { return new Point(position.x / GamePanel.PIXEL, position.y / GamePanel.PIXEL); }

    /**
     * get tile of the center of enitiy
     * @return tile
     */
    public Point getCenterTile() {
        return new Point((position.x + GamePanel.PIXEL / 2) / GamePanel.PIXEL, (position.y + GamePanel.PIXEL / 2) / GamePanel.PIXEL);
    }

    /**
     * checks if entity is in the middle of tile
     * @return true if is in the middle of tile otherwise false
     */
    public boolean isInCenter() {
        var center = getCenter();
        return (center.x % 32 == 0 && center.x % 64 != 0) && (center.y % 32 == 0 && center.y % 64 != 0);
    }
}
