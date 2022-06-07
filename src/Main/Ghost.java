package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ghost extends Entity {
    public enum Personality {BLINKY, INKY, CLYDE}

    ControlPanel.MoveDirection move_direction = ControlPanel.MoveDirection.UP;
    Personality personality;
    int speed = 4;
    Map map;

    public Ghost(Map map, Personality personality) {
        this.map = map;
        this.personality = personality;
        this.getGhostImage();
        this.setPostion();
    }

    void setPostion() {
        switch (personality) {
            case BLINKY -> position = new Point(12 * Map.PIXEL, 7 * Map.PIXEL);
            case INKY -> position = new Point(13 * Map.PIXEL, 7 * Map.PIXEL);
            case CLYDE -> position = new Point(14 * Map.PIXEL, 7 * Map.PIXEL);
        }
    }

    void getGhostImage() {
        try {
            switch (personality) {
                case BLINKY -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../assets/purple_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../assets/purple_ghost_2.png"));
                }
                case INKY -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../assets/blue_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../assets/blue_ghost_2.png"));
                }
                case CLYDE -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../assets/green_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../assets/green_ghost_2.png"));
                }
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public void update() {
        switch (this.move_direction) {
            case UP -> this.position.y -= this.speed;
            case DOWN -> this.position.y += this.speed;
            case LEFT -> this.position.x -= this.speed;
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
