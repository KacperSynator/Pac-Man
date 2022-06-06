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
        this.x = 400;
        this.y = 400;
        this.speed = 5;
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
        if (this.keys.up) {
            this.y -= this.speed;
            ++this.counter;
        }

        if (this.keys.down) {
            this.y += this.speed;
            ++this.counter;
        }

        if (this.keys.left) {
            this.x -= this.speed;
            ++this.counter;
        }

        if (this.keys.right) {
            this.x += this.speed;
            ++this.counter;
        }

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
        if (this.frameCounter == 0) {
            image = this.frame1;
        }

        if (this.frameCounter == 1) {
            image = this.frame2;
        }

        element2d.drawImage(image, this.x, this.y, 64, 64, null);
    }
}
