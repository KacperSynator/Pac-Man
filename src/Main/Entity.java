//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.Point;

public class Entity {
    Map map;
    public Point position = new Point();
    public BufferedImage frame1;
    public BufferedImage frame2;
    int frameCounter = 0;
    int counter = 0;

    public Entity() {
    }

    public Point getCenter() { return new Point(position.x + Map.PIXEL / 2, position.y + Map.PIXEL / 2); }
    public Point getTile() { return new Point(position.x / Map.PIXEL, position.y / Map.PIXEL); }
    public Point getCenterTile() {
        return new Point((position.x + Map.PIXEL / 2) / Map.PIXEL, (position.y + Map.PIXEL / 2) / Map.PIXEL);
    }
    public boolean isInCenter() {
        var center = getCenter();
        return (center.x % 32 == 0 && center.x % 64 != 0) && (center.y % 32 == 0 && center.y % 64 != 0);
    }
}
