//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import javax.swing.JFrame;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(3);
        window.setResizable(false);
        window.setTitle("Test");
        window.setVisible(true);
        Map map = new Map();
        window.add(map);
        window.pack();
        map.startGameThread();
    }
}
