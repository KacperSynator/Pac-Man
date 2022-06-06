//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlPanel implements KeyListener {
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    private boolean toggle = false;

    public ControlPanel() {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int whatClicked = e.getKeyCode();
        if (whatClicked == 87 && !this.toggle) {
            this.up = true;
            this.toggle = true;
        }

        if (whatClicked == 83 && !this.toggle) {
            this.down = true;
            this.toggle = true;
        }

        if (whatClicked == 65 && !this.toggle) {
            this.left = true;
            this.toggle = true;
        }

        if (whatClicked == 68 && !this.toggle) {
            this.right = true;
            this.toggle = true;
        }

    }

    public void keyReleased(KeyEvent e) {
        int whatClicked = e.getKeyCode();
        if (whatClicked == 87) {
            this.up = false;
            this.toggle = false;
        }

        if (whatClicked == 83) {
            this.down = false;
            this.toggle = false;
        }

        if (whatClicked == 65) {
            this.left = false;
            this.toggle = false;
        }

        if (whatClicked == 68) {
            this.right = false;
            this.toggle = false;
        }

    }
}
