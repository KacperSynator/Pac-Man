//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.List;
import java.util.ArrayList;

public class Map extends JPanel implements Runnable {
    public static final int PIXEL = 64;
    public static final int SCREEN_HEIGHT = 960;
    public static final int SCREEN_WIDTH = 1600;
    ControlPanel keys = new ControlPanel();
    public CollisionPanel collisionPanel = new CollisionPanel(this);
    Font font;
    Thread gameThread;
    TileManager tileManager = new TileManager(this);
    Treat[][] treat_map;
    List<Ghost> ghosts;
    Pacman pacman;
    int total_treat_amount = 0;
    int current_treat_amount =0;
    int lives = 3;
    BufferedImage heart;

    //JLabel score = new JLabel("Score: ");

    public int[][] getTileMap() { return tileManager.getMapLayout(); }

    public Map() {
        this.pacman = new Pacman(this, this.keys);
        this.spawnGhosts();
        this.spawnTreats();
        this.loadFont();
        this.setPreferredSize(new Dimension(Map.SCREEN_WIDTH, Map.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keys);
        this.setFocusable(true);
        this.requestFocusInWindow();
        try {
            heart = ImageIO.read(this.getClass().getResourceAsStream("../assets/heart.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    void loadFont() {
        try {
            // "../assets/fonts/android-insomnia/and_ins_reg.ttf" // "../assets/fonts/games/Games.ttf"
            font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("../fonts/games/Games.ttf"));
            font = font.deriveFont(36.0f);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    void spawnGhosts() {
        ghosts = new ArrayList<>();
        ghosts.add( new Ghost(this, Ghost.Personality.BLINKY));
        ghosts.add( new Ghost(this, Ghost.Personality.INKY));
        ghosts.add( new Ghost(this, Ghost.Personality.CLYDE));
        Ghost.movement_mode_timer.start();
        Ghost.mimic_mode_timer.start();
    }

    void spawnTreats() {
        var tile_map = tileManager.getMapLayout();
        treat_map = new Treat[Map.SCREEN_HEIGHT / Map.PIXEL][Map.SCREEN_WIDTH / Map.PIXEL];
        for (int i = 0; i < Map.SCREEN_HEIGHT / Map.PIXEL; ++i) {
            for (int j = 0; j < Map.SCREEN_WIDTH / Map.PIXEL; ++j) {
                if (tile_map[i][j] == 0) {
                    treat_map[i][j] = new Treat(this, new Point(j * PIXEL, i * PIXEL));
                    total_treat_amount++;
                    current_treat_amount++;
                }
            }
        }
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public void run() {
        double drawInterval = 1.6666666E7;  // set max 60 fps
        double delta = 0.0;
        long lastTime = System.nanoTime();

        while(this.gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (double)(currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1.0) {
                this.update();
                this.repaint();
                this.requestFocusInWindow();
                --delta;
            }
        }
    }

    public void update() {
        this.pacman.update();
        this.eatTreat();
        for (var ghost : ghosts) {
            ghost.update();
        }
        checkCollision();
    }

    void eatTreat() {
        var pacman_tile = pacman.getCenterTile();
        if (treat_map[pacman_tile.y][pacman_tile.x] != null) {
            treat_map[pacman_tile.y][pacman_tile.x] = null;
            current_treat_amount--;
        }
    }

    boolean checkCollision() {
        for (var ghost : ghosts) {
            if (ghost.getCenterTile().x == pacman.getCenterTile().x && ghost.getCenterTile().y == pacman.getCenterTile().y) {
                System.out.println("Game Over");
                return true;
            }
        }
        return false;
    }

    public void paintComponent(Graphics element) {
        super.paintComponent(element);
        Graphics2D element2d = (Graphics2D)element;
        this.tileManager.draw(element2d);
        this.paintTreats(element2d);
        this.pacman.draw(element2d);
        this.paintGhosts(element2d);
        element2d.setColor(Color.white);
        element2d.setFont(font);
        element2d.drawString("Score: " + 10 * (total_treat_amount-current_treat_amount),1370,935);
        for(int i=0;i<lives;i++)
        {
            element2d.drawImage(heart, 1260+i*PIXEL/2, 910, Map.PIXEL/2, Map.PIXEL/2, null);
        }
        element2d.dispose();
    }

    void paintGhosts(Graphics2D element2d) {
        for (var ghost : ghosts) {
            ghost.draw(element2d);
        }
    }


    void paintTreats(Graphics2D element2d) {
        for (int i = 0; i < Map.SCREEN_HEIGHT / Map.PIXEL; ++i) {
            for (int j = 0; j < Map.SCREEN_WIDTH / Map.PIXEL; ++j) {
                if (treat_map[i][j] != null) {
                    treat_map[i][j].draw(element2d);
                }
            }
        }
    }
    void printScore(){

    }
}
