package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;

/**
 * class implementing ghosts that chase pacman
 */
public class Ghost extends MovingEntity implements Runnable {
    /**
     * enum of ghost personality
     * BLINKY - always chase pacman <br>
     * PINKY - tries to ambush pacman <br>
     * INKY - whimsical strategy of chase <br>
     * CLYDE - randomly mimics other ghosts
     */
    public enum Personality {BLINKY, INKY, PINKY, CLYDE}
    /**
     * enum of movment modes of ghosts: <br>
     * CHASE -> chase pacman <br>
     * SCATTER -> move to corner <br>
     */
    public enum MovementMode {CHASE, SCATTER}

    /** movement mode of ghost */
    static MovementMode movement_mode = MovementMode.SCATTER;
    /** mimic personality of CLYDE */
    static Personality mimic_personality = Personality.BLINKY;
    /** time in ms between ghosts movement mode change */
    static final int MOVEMENT_MODE_DELAY = 5000;
    /** time in ms between CLYDE mimic personality change */
    static final int MIMIC_MODE_DELAY = 3000;
    /** thread of thoe ghost */
    Thread ghost_thread;
    /** personality of ghost */
    Personality personality;
    /** timer which changes movement mode after specified time interval */
    static public Timer movement_mode_timer = new Timer(MOVEMENT_MODE_DELAY, e -> {
        switch (movement_mode) {
            case SCATTER -> movement_mode = MovementMode.CHASE;
            case CHASE -> movement_mode = MovementMode.SCATTER;
        }
    });
    /** timer which changes CLYDE mimic personality after specified time interval */
    static public Timer mimic_mode_timer = new Timer(MIMIC_MODE_DELAY, e -> {
        switch (mimic_personality) {
            case BLINKY -> mimic_personality = Personality.INKY;
            case INKY -> mimic_personality = Personality.PINKY;
            case PINKY -> mimic_personality = Personality.BLINKY;
        }
    });

    /**
     * constructor that takes game_panel object and ghost personality
     * @param game_panel game_panel object
     * @param personality ghost's personality
     */
    public Ghost(GamePanel game_panel, Personality personality) {
        this.game_panel = game_panel;
        this.personality = personality;
        this.move_direction = ControlPanel.MoveDirection.UP;
        this.getGhostImage();
        this.setPostion();
    }

    /**
     * set starting position depending on personality
     */
    void setPostion() {
        switch (personality) {
            case BLINKY -> position = new Point(12 * GamePanel.PIXEL, 6 * GamePanel.PIXEL);
            case INKY -> position = new Point(13 * GamePanel.PIXEL, 6 * GamePanel.PIXEL);
            case CLYDE -> position = new Point(14 * GamePanel.PIXEL, 6 * GamePanel.PIXEL);
            case PINKY -> position = new Point(11 * GamePanel.PIXEL, 6 * GamePanel.PIXEL);
        }
    }

    /**
     * loads ghost images depending on personality
     */
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
                case PINKY -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/pink_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/pink_ghost_2.png"));
                }
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    /**
     * starts ghost thread
     */
    public void startGhostThread() {
        this.ghost_thread = new Thread(this);
        this.ghost_thread.start();
    }

    /**
     * stops ghost thread
     */
    public void stopGhostThread() {
        this.ghost_thread = null;
    }

    /**
     * thread run function, updates ghost status and limits frames to 60
     */
    public void run() {
        double drawInterval = 1.6666666E7;  // set max 60 fps
        double delta = 0.0;
        long lastTime = System.nanoTime();

        while (this.ghost_thread != null) {
            long currentTime = System.nanoTime();
            delta += (double)(currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1.0) {
                if (game_panel.update) this.update();
                --delta;
            }
        }
    }

    /**
     * scatter ghost movement mode depending on personality
     */
    void scatter() {
        switch (personality) {
            case INKY -> moveToTile(new Point(24, 0));
            case BLINKY -> moveToTile(new Point(0, 14));
            case CLYDE -> moveToTile(new Point(24, 14));
            case PINKY -> moveToTile(new Point(0, 0));
        }
    }

    /**
     * calculates ambush tile for PINKY chase mode
     * @return tile
     */
    Point calculateAmbushTile() {
        final int tiles_ahead = 2;
        var target_tile = game_panel.pacman.getCenterTile();
        switch (game_panel.pacman.move_direction) {
            case UP -> target_tile.y -= tiles_ahead;
            case DOWN -> target_tile.y += tiles_ahead;
            case RIGHT -> target_tile.x += tiles_ahead;
            case LEFT -> target_tile.x -= tiles_ahead;
        }
        return target_tile;
    }

    /**
     * chooses CLYDE mimic personality
     */
    void mimicOther() {
        switch (mimic_personality) {
            case BLINKY -> moveToTile(game_panel.pacman.getCenterTile());
            case PINKY -> moveToTile(calculateAmbushTile());
            case INKY -> whimsicalStrategy();
        }
    }

    /**
     * whimsical strategy for INKY chase mode
     */
    void whimsicalStrategy() {
        var ambush_tile = calculateAmbushTile();
        Point blinky_tile = null;
        for (var ghost : game_panel.ghosts)
            if (ghost.personality == Personality.BLINKY)
                blinky_tile = ghost.getCenterTile();

        Point target_tile = new Point(2 * (ambush_tile.x - blinky_tile.x), 2 * (ambush_tile.y - blinky_tile.y));
        moveToTile(target_tile);
    }

    /**
     * chooses chase strategy depending on personality
     */
    void chase() {
        switch (personality) {
            case BLINKY -> moveToTile(game_panel.pacman.getCenterTile());
            case PINKY -> moveToTile(calculateAmbushTile());
            case INKY -> whimsicalStrategy();
            case CLYDE -> mimicOther();
        }
    }

    /**
     * changes movement modes of ghosts
     */
    void updateMoveDirection() {
        switch (movement_mode) {
            case SCATTER -> scatter();
            case CHASE -> chase();
        }
    }

    /**
     * calculates next tile where ghost will be moved depending on target tile. Next move tile is chosen as minimal
     * euclidean distance between target tile and every possible next move tile.
     * @param tile target tile
     */
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
            var dist = Point.calculateDistance(tile, next_tile);
            if (min_dist >= dist) {
                min_dist = dist;
                best_dir = dir;
            }
        }
        if (best_dir != null)  move_direction = best_dir;
    }

    /**
     * updates ghost in every frame
     */
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

    /**
     * draws ghost in game window
     * @param element2d Graphics2D from java.awt.Graphics
     */
    public void draw(Graphics2D element2d) {
        BufferedImage image = null;

        switch (this.frameCounter) {
            case 0 -> image = this.frame1;
            case 1 -> image = this.frame2;
        }

        element2d.drawImage(image, this.position.x, this.position.y, GamePanel.PIXEL, GamePanel.PIXEL, null);
    }
}
