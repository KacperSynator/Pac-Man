//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

import java.util.List;
import java.util.ArrayList;

/**
 * implements main game loop, handles all game events
 */
public class GamePanel extends JPanel implements Runnable {
    /** size of single tile in pixels */
    public static final int PIXEL = 64;
    /** screen height in pixels */
    public static final int SCREEN_HEIGHT = 960;
    /** screen height in pixels */
    public static final int SCREEN_WIDTH = 1600;
    /** score of eaten treat */
    static final int TREAT_SCORE = 10;
    /** time in ms of game start delay */
    static final int START_DELAY = 2000;
    /** flag that indicates if game objects should be updated in loop */
    public boolean update = false;
    /** flag that indicates game over state */
    public boolean game_over = false;
    /** user input handler */
    ControlPanel keys = new ControlPanel();
    /** thread object */
    Thread gameThread;
    /** game map */
    TileManager tileManager = new TileManager(this);
    /** map of treats */
    Treat[][] treat_map;
    /** list of ghosts */
    List<Ghost> ghosts;
    /** pacman object */
    Pacman pacman;
    /** current game score */
    int score = 0;
    /** number of existing treats */
    int treat_amount = 0;
    /** number of remaining lives */
    int lives = 3;
    /** user interface object */
    UserInterface user_interface = new UserInterface(this);
    /** start delay timer */
    public Timer start_delay_timer = new Timer(START_DELAY, e -> update = true);
    /** @return tile map layout */
    public int[][] getTileMap() { return tileManager.getMapLayout(); }

    /**
     * constructor
     */
    public GamePanel() {
        this.pacman = new Pacman(this, this.keys);
        this.spawnGhosts();
        this.spawnTreats();
        this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keys);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
     * creates ghosts
     */
    void spawnGhosts() {
        ghosts = new ArrayList<>();
        ghosts.add( new Ghost(this, Ghost.Personality.BLINKY));
        ghosts.add( new Ghost(this, Ghost.Personality.INKY));
        ghosts.add( new Ghost(this, Ghost.Personality.CLYDE));
        ghosts.add( new Ghost(this, Ghost.Personality.PINKY));
        Ghost.movement_mode_timer.start();
        Ghost.mimic_mode_timer.start();
        for (var ghost : ghosts) {
            ghost.startGhostThread();
        }
    }

    /**
     * creates treats on map
     */
    void spawnTreats() {
        var tile_map = tileManager.getMapLayout();
        treat_amount = 0;
        treat_map = new Treat[GamePanel.SCREEN_HEIGHT / GamePanel.PIXEL][GamePanel.SCREEN_WIDTH / GamePanel.PIXEL];
        for (int i = 0; i < GamePanel.SCREEN_HEIGHT / GamePanel.PIXEL; ++i) {
            for (int j = 0; j < GamePanel.SCREEN_WIDTH / GamePanel.PIXEL; ++j) {
                if (tile_map[i][j] == 0) {
                    treat_map[i][j] = new Treat(this, new Point(j * PIXEL, i * PIXEL));
                    treat_amount++;
                }
            }
        }
    }

    /**
     * starts game thread
     */
    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();

    }

    /**
     * thread main function that updates and paints game objects. Frame rate set to 60 fps.
     */
    public void run() {
        double drawInterval = 1.6666666E7;  // set max 60 fps
        double delta = 0.0;
        long lastTime = System.nanoTime();

        this.start_delay_timer.start();

        while(this.gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (double)(currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1.0) {
                if (update) this.update();
                this.repaint();
                this.requestFocusInWindow();
                --delta;
            }
        }
    }

    /**
     * updates objects, checks for collisions and checks win condition
     */
    public void update() {
        this.pacman.update();
        this.eatTreat();
        if (treat_amount == 0) System.out.println("Game Won");
        if (checkCollision())  resetMap();
    }

    /**
     * handles treats eating and score updating
     */
    void eatTreat() {
        var pacman_tile = pacman.getCenterTile();
        if (treat_map[pacman_tile.y][pacman_tile.x] != null) {
            treat_map[pacman_tile.y][pacman_tile.x] = null;
            --treat_amount;
            score += TREAT_SCORE;
        }
    }

    /**
     * checks for ghost pacman collisions
     * @return true if collision otherwise false
     */
    boolean checkCollision() {
        for (var ghost : ghosts) {
            if (ghost.getCenterTile().x == pacman.getCenterTile().x && ghost.getCenterTile().y == pacman.getCenterTile().y) {
                return true;
            }
        }
        return false;
    }

    /**
     * draws elements in game window
     * @param element the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics element) {
        super.paintComponent(element);
        Graphics2D element2d = (Graphics2D)element;
        this.tileManager.draw(element2d);
        this.paintTreats(element2d);
        this.pacman.draw(element2d);
        this.paintGhosts(element2d);
        this.user_interface.paintInterfaceInGame(element2d);
        if (game_over) user_interface.gameOverScreen(element2d);
        element2d.dispose();
    }

    /**
     * draws ghosts in game window
     * @param element2d Graphics2D from java.awt.Graphics
     */
    void paintGhosts(Graphics2D element2d) {
        for (var ghost : ghosts) {
            ghost.draw(element2d);
        }
    }

    /**
     * draws treats in game window
     * @param element2d Graphics2D from java.awt.Graphics
     */
    void paintTreats(Graphics2D element2d) {
        for (int i = 0; i < GamePanel.SCREEN_HEIGHT / GamePanel.PIXEL; ++i) {
            for (int j = 0; j < GamePanel.SCREEN_WIDTH / GamePanel.PIXEL; ++j) {
                if (treat_map[i][j] != null) {
                    treat_map[i][j].draw(element2d);
                }
            }
        }
    }

    /**
     * resets ghosts and pacman position, handles lives. When 0 lives remaining sets game over flag.
     */
    void resetMap() {
        start_delay_timer.stop();
        this.update = false;
        for (var ghost : ghosts) ghost.stopGhostThread();
        if (--lives <= 0) {
            game_over = true;
            return;
        }
        pacman = new Pacman(this,this.keys);
        ghosts.clear();
        spawnGhosts();
        start_delay_timer.start();
    }

    /**
     * resets ghosts, and pacman position, lives, score and treats
     */
    void resetGame() {
        start_delay_timer.stop();
        this.update = false;
        game_over = false;
        for (var ghost : ghosts) ghost.stopGhostThread();
        lives = 3;
        score = 0;
        pacman = new Pacman(this,this.keys);
        ghosts.clear();
        spawnGhosts();
        spawnTreats();
        start_delay_timer.start();
    }

    /**
     * quit the game
     */
    void quit() {
        for (var ghost : ghosts) ghost.stopGhostThread();
        this.gameThread = null;
    }
}
