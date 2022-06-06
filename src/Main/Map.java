//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import Main.Pacman;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import Main.TileManager;

public class Map extends JPanel implements Runnable {
    public static final int pixel = 64;
    public static final int screen_height = 960;
    public static final int screen_width = 1600;
    ControlPanel keys = new ControlPanel();
    Thread gameThread;
    int playerX = 400;
    int playerY = 400;
    int playerSpeed = 1;
    TileManager tileManager = new TileManager(this);
    Pacman pacman;

    public Map() {
        this.pacman = new Pacman(this, this.keys);
        this.setPreferredSize(new Dimension(Map.screen_width, Map.screen_height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keys);
        this.setFocusable(true);
        this.requestFocusInWindow();
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
    }

    public void paintComponent(Graphics element) {
        super.paintComponent(element);
        Graphics2D element2d = (Graphics2D)element;
        this.tileManager.draw(element2d);
        this.pacman.draw(element2d);
        element2d.dispose();
    }
}
