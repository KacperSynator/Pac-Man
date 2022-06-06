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
    public MoveDirection next_move_direction;

    public ControlPanel() {
        move_direction = MoveDirection.LEFT;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> move_direction = MoveDirection.UP;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> move_direction = MoveDirection.DOWN;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> move_direction = MoveDirection.LEFT;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> move_direction = MoveDirection.RIGHT;
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
