package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

public class Treat extends Entity {
    static final String[] TREAT_FILES = {"../treats/candy_1.png", "../treats/candy_2.png", "../treats/candy_3.png",
            "../treats/candy_4.png"};
    Map map;

    public Treat(Map map, Point position) {
        this.map = map;
        this.hitbox = new Rectangle(16, 32, 0, 0);
        this.position = new Point(position.x + Map.PIXEL / 4, position.y + Map.PIXEL / 4);
        this.getImage();
    }

    public void getImage() {
        try {
        int file_idx = new Random().ints(1, 0, 4).findFirst().getAsInt();
        this.frame1 = ImageIO.read(this.getClass().getResourceAsStream(TREAT_FILES[file_idx]));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public void draw(Graphics2D element2d) {
        element2d.drawImage(frame1, this.position.x, this.position.y, Map.PIXEL / 2, Map.PIXEL / 2, null);
    }
}
