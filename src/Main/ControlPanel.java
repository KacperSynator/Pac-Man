//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * class implementing keyboard input
 */
public class ControlPanel implements KeyListener {
    /**
     * enum of possible movement directions
     */
    public enum MoveDirection { UP, DOWN, LEFT, RIGHT }

    /** move direction depending on user input */
    public MoveDirection move_direction;

    /**
     * constructor
     */
    public ControlPanel() {
        move_direction = MoveDirection.LEFT;
    }

    public void keyTyped(KeyEvent e) {}

    /**
     * handles user input
     * @param e the event to be processed
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> move_direction = MoveDirection.UP;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> move_direction = MoveDirection.DOWN;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> move_direction = MoveDirection.LEFT;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> move_direction = MoveDirection.RIGHT;
        }
    }

    public void keyReleased(KeyEvent e) {}
}
