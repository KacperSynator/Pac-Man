//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import javax.swing.*;


/**
 * main class, that starts the game
 */
public class Main {
    /**
     * main function
     */
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PacMan");
        window.setVisible(true);
        GamePanel map = new GamePanel();
        window.add(map);
        window.pack();
        map.startGameThread();
        try {
            map.gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.dispose();
    }
}
