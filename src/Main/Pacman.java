//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * implementation of player controlled Pacman
 */
public class Pacman extends MovingEntity {
    /** user input reader */
    ControlPanel keys;
    public BufferedImage frame1up;
    public BufferedImage frame2up;
    public BufferedImage frame1down;
    public BufferedImage frame2down;
    public BufferedImage frame1right;
    public BufferedImage frame2right;
    public BufferedImage frame1left;
    public BufferedImage frame2left;

    /**
     * constructor
     * @param game_panel game_panel object
     * @param keys ControlPanel object
     */
    public Pacman(GamePanel game_panel, ControlPanel keys) {
        this.keys = keys;
        this.game_panel = game_panel;
        this.move_direction = ControlPanel.MoveDirection.LEFT;
        this.position = new Point(12 * GamePanel.PIXEL, 11 * GamePanel.PIXEL);
        this.getPacmanImage();
    }

    /**
     * loads pacman frames from files
     */
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

    /**
     * update of pacman in every game iteration, that changes frames and movement of pacman.
     */
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

    /**
     * draws pacman in game window
     * @param element2d Graphics2D from java.awt.Graphics
     */
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
        element2d.drawImage(image, this.position.x, this.position.y, GamePanel.PIXEL, GamePanel.PIXEL, null);
    }
}
