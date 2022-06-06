//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlPanel implements KeyListener {
    public enum MoveDirection { UP, DOWN, LEFT, RIGHT }

    public MoveDirection move_direction;
    private boolean toggle = false;

    public ControlPanel() {
        move_direction = MoveDirection.LEFT;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 87 -> move_direction = MoveDirection.UP;
            case 83 -> move_direction = MoveDirection.DOWN;
            case 65 -> move_direction = MoveDirection.LEFT;
            case 68 -> move_direction = MoveDirection.RIGHT;
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
