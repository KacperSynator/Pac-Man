//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import javax.swing.*;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PacMan");
        window.setVisible(true);
        Map map = new Map();
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
