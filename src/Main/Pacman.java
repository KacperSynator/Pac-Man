//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import Main.ControlPanel;
import Main.Map;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Pacman extends Entity {
    static final Point START_POSITION = new Point(12 * Map.PIXEL, 11 * Map.PIXEL);
    static final int SPEED = 4;
    Map map;
    ControlPanel keys;

    public Pacman(Map map, ControlPanel keys) {
        this.keys = keys;
        this.map = map;
        this.hitbox = new Rectangle(16, 32, 0, 0);
        this.setDefaultValues();
        this.getPacmanImage();
    }

    public void setDefaultValues() {
        this.position = START_POSITION;
        this.speed = SPEED;
    }

    public void getPacmanImage() {
        try {
            this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../assets/pacman1.png"));
            this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../assets/pacman3.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void update() {
        switch (this.keys.move_direction) {
            case UP    -> this.position.y -= this.speed;
            case DOWN  -> this.position.y += this.speed;
            case LEFT  -> this.position.x -= this.speed;
            case RIGHT -> this.position.x += this.speed;
        }
        ++this.counter;

        if (this.counter >= 6) {
            this.frameCounter = 1;
        }

        if (this.counter >= 12) {
            this.frameCounter = 0;
            this.counter = 0;
        }
    }

    public void draw(Graphics2D element2d) {
        BufferedImage image = null;

        switch (this.frameCounter) {
            case 0 -> image = this.frame1;
            case 1 -> image = this.frame2;
        }

        element2d.drawImage(image, this.position.x, this.position.y, Map.PIXEL, Map.PIXEL, null);
    }
}
