//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.Point;

public class Entity {
    public enum Type { PACMAN, GHOST, TREAT }
    Point position = new Point();
    public int speed;
    public Rectangle hitbox;
    public boolean collisionDetected = false;
    public BufferedImage frame1;
    public BufferedImage frame2;
    int frameCounter = 0;
    int counter = 0;

    public Entity() {
    }

}
