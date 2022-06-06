//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.Point;

public class Entity {
    public Point position = new Point();
    public int speed;
    public Rectangle hitbox;
    public boolean collisionDetected = false;
    String direction;
    public BufferedImage frame1;
    public BufferedImage frame2;
    int frameCounter = 0;
    int counter = 0;

    public Entity() {
    }

    public Point getTile() { return new Point(position.x / Map.PIXEL, position.y / Map.PIXEL); }
    public Point getCenterTile() {
        return new Point((position.x + Map.PIXEL / 2) / Map.PIXEL, (position.y + Map.PIXEL / 2) / Map.PIXEL);
    }

}
