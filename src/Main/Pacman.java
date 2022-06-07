//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Pacman extends MovingEntity {
    ControlPanel keys;
    public BufferedImage frame1up;
    public BufferedImage frame2up;
    public BufferedImage frame1down;
    public BufferedImage frame2down;
    public BufferedImage frame1right;
    public BufferedImage frame2right;
    public BufferedImage frame1left;
    public BufferedImage frame2left;

    public Pacman(Map map, ControlPanel keys) {
        this.keys = keys;
        this.map = map;
        this.move_direction = ControlPanel.MoveDirection.LEFT;
        this.position = new Point(12 * Map.PIXEL, 11 * Map.PIXEL);
        this.getPacmanImage();
    }

    public void getPacmanImage() {
        try {
            frame1up = ImageIO.read(this.getClass().getResourceAsStream("../pacman/pacman_up_1.png"));
            frame2up = ImageIO.read(this.getClass().getResourceAsStream("../pacman/pacman_up_2.png"));
            frame1down = ImageIO.read(this.getClass().getResourceAsStream("../pacman/pacman_down_1.png"));
            frame2down = ImageIO.read(this.getClass().getResourceAsStream("../pacman/pacman_down_2.png"));
            frame1right = ImageIO.read(this.getClass().getResourceAsStream("../pacman/pacman_right_1.png"));
            frame2right = ImageIO.read(this.getClass().getResourceAsStream("../pacman/pacman_right_2.png"));
            frame1left = ImageIO.read(this.getClass().getResourceAsStream("../pacman/pacman_left_1.png"));
            frame2left = ImageIO.read(this.getClass().getResourceAsStream("../pacman/pacman_left_2.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }


    public void update() {

        var dirs =  getPossibleMoveDirections(getCenterTile());
        if (this.move_direction != this.keys.move_direction  && dirs.size() > 2 || !dirs.contains(move_direction)) {
            if (isInCenter() && dirs.contains(this.keys.move_direction)) {
                this.move_direction = this.keys.move_direction;
            }
        }
        if (dirs.contains(move_direction) || !isInCenter()) {
            switch (move_direction) {
                case UP -> this.position.y -= this.speed;
                case DOWN -> this.position.y += this.speed;
                case LEFT -> this.position.x -= this.speed;
                case RIGHT -> this.position.x += this.speed;
            }
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

        switch (this.move_direction) {
            case UP -> {
                switch (this.frameCounter) {
                    case 0 -> image = this.frame1up;
                    case 1 -> image = this.frame2up;
                }
            }
            case DOWN -> {
                switch (this.frameCounter) {
                    case 0 -> image = this.frame1down;
                    case 1 -> image = this.frame2down;
                }
            }
            case LEFT -> {
                switch (this.frameCounter) {
                    case 0 -> image = this.frame1left;
                    case 1 -> image = this.frame2left;
                }
            }
            case RIGHT -> {
                switch (this.frameCounter) {
                    case 0 -> image = this.frame1right;
                    case 1 -> image = this.frame2right;
                }
            }
        }
        element2d.drawImage(image, this.position.x, this.position.y, Map.PIXEL, Map.PIXEL, null);
    }
}
