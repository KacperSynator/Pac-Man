package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ghost extends MovingEntity implements Runnable {
    public enum Personality {BLINKY, INKY, CLYDE}
    public enum MovementMode {CHASE, SCATTER}
    static MovementMode movement_mode = MovementMode.SCATTER;
    static Personality mimic_personality = Personality.BLINKY;
    static final int movement_mode_delay = 5000;
    static final int mimic_mode_delay = 3000;
    Thread ghost_thread;
    Personality personality;
    static public Timer movement_mode_timer = new Timer(movement_mode_delay, e -> {
        switch (movement_mode) {
            case SCATTER -> movement_mode = MovementMode.CHASE;
            case CHASE -> movement_mode = MovementMode.SCATTER;
        }
    });

    static public Timer mimic_mode_timer = new Timer(mimic_mode_delay, e -> {
        switch (mimic_personality) {
            case BLINKY -> mimic_personality = Personality.INKY;
            case INKY -> mimic_personality = Personality.BLINKY;
        }
    });



    public Ghost(Map map, Personality personality) {
        this.map = map;
        this.personality = personality;
        this.move_direction = ControlPanel.MoveDirection.UP;
        this.getGhostImage();
        this.setPostion();
    }

    void setPostion() {
        switch (personality) {
            case BLINKY -> position = new Point(12 * Map.PIXEL, 6 * Map.PIXEL);
            case INKY -> position = new Point(13 * Map.PIXEL, 6 * Map.PIXEL);
            case CLYDE -> position = new Point(14 * Map.PIXEL, 6 * Map.PIXEL);
        }
    }

    void getGhostImage() {
        try {
            switch (personality) {
                case BLINKY -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/purple_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/purple_ghost_2.png"));
                }
                case INKY -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/blue_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/blue_ghost_2.png"));
                }
                case CLYDE -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/green_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/green_ghost_2.png"));
                }
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public void startGhostThread() {
        this.ghost_thread = new Thread(this);
        this.ghost_thread.start();
    }

    public void run() {
        double drawInterval = 1.6666666E7;  // set max 60 fps
        double delta = 0.0;
        long lastTime = System.nanoTime();

        while (this.ghost_thread != null) {
            long currentTime = System.nanoTime();
            delta += (double)(currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1.0) {
                this.update();
                --delta;
            }
        }
    }

    double calculateDistance(Point a, Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    void scatter() {
        switch (personality) {
            case INKY -> moveToTile(new Point(24, 0));
            case BLINKY -> moveToTile(new Point(0, 14));
            case CLYDE -> moveToTile(new Point(24, 14));
        }
    }

    void ambushPacman() {
        final int tiles_ahead = 2;
        var target_tile = map.pacman.getCenterTile();
        switch (map.pacman.move_direction) {
            case UP -> target_tile.y -= tiles_ahead;
            case DOWN -> target_tile.y += tiles_ahead;
            case RIGHT -> target_tile.x += tiles_ahead;
            case LEFT -> target_tile.x -= tiles_ahead;
        }
        moveToTile(target_tile);
    }

    void mimicOther() {
        switch (mimic_personality) {
            case INKY -> moveToTile(map.pacman.getCenterTile());
            case BLINKY -> ambushPacman();
        }
    }

    void chase() {
        switch (personality) {
            case INKY -> moveToTile(map.pacman.getCenterTile());
            case BLINKY -> ambushPacman();
            case CLYDE -> mimicOther();
        }
    }

    void updateMoveDirection() {
        switch (movement_mode) {
            case SCATTER -> scatter();
            case CHASE -> chase();
        }
    }

    void removeBackDir(List<ControlPanel.MoveDirection> dirs) {
        if (dirs.size() == 1) return;
        switch (move_direction) {
            case UP -> dirs.remove(ControlPanel.MoveDirection.DOWN);
            case DOWN -> dirs.remove(ControlPanel.MoveDirection.UP);
            case RIGHT -> dirs.remove(ControlPanel.MoveDirection.LEFT);
            case LEFT -> dirs.remove(ControlPanel.MoveDirection.RIGHT);
        }
    }

    void moveToTile(Point tile) {
        Point current_pos = getCenterTile();
        var dirs =  getPossibleMoveDirections(getCenterTile());
        removeBackDir(dirs);
        ControlPanel.MoveDirection best_dir = null;
        double min_dist = Double.MAX_VALUE;
        for (var dir : dirs) {
            Point next_tile = current_pos;
            switch (dir) {
                case UP -> next_tile.y -= 1;
                case DOWN -> next_tile.y += 1;
                case LEFT -> next_tile.x -= 1;
                case RIGHT -> next_tile.x += 1;
            }
            var dist = calculateDistance(tile, next_tile);
            if (min_dist >= dist) {
                min_dist = dist;
                best_dir = dir;
            }
        }
        if (best_dir != null)  move_direction = best_dir;
    }

    public void update() {
        if (isInCenter()) updateMoveDirection();

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
