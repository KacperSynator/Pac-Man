//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int x;
    public int y;
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
