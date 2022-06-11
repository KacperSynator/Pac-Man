package Main;

import java.util.ArrayList;
import java.util.List;


/**
 * class implementing movable entities
 */
public class MovingEntity extends Entity{
    /** direction of movement */
    public ControlPanel.MoveDirection move_direction;
    /** movement speed */
    public int speed = 4;

    /**
     * gets possible move directions depending on position of tile and map layout
     * @param tile tile idx
     * @return list of possible move directions
     */
    List<ControlPanel.MoveDirection> getPossibleMoveDirections(Point tile) {
        List<ControlPanel.MoveDirection> result = new ArrayList<>();
        if (game_panel.getTileMap()[tile.y - 1][tile.x] == 0) result.add(ControlPanel.MoveDirection.UP);
        if (game_panel.getTileMap()[tile.y + 1][tile.x] == 0) result.add(ControlPanel.MoveDirection.DOWN);
        if (game_panel.getTileMap()[tile.y][tile.x - 1] == 0) result.add(ControlPanel.MoveDirection.LEFT);
        if (game_panel.getTileMap()[tile.y][tile.x + 1] == 0) result.add(ControlPanel.MoveDirection.RIGHT);
        return result;
    }

    /**
     * removes backwards movement direction. If backward direction is the only one possible backwards direction is not
     * removed.
     * @param dirs list of possible move directions
     */
    void removeBackDir(List<ControlPanel.MoveDirection> dirs) {
        if (dirs.size() == 1) return;
        switch (move_direction) {
            case UP -> dirs.remove(ControlPanel.MoveDirection.DOWN);
            case DOWN -> dirs.remove(ControlPanel.MoveDirection.UP);
            case RIGHT -> dirs.remove(ControlPanel.MoveDirection.LEFT);
            case LEFT -> dirs.remove(ControlPanel.MoveDirection.RIGHT);
        }
    }
}
