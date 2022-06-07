package Main;

import java.util.ArrayList;
import java.util.List;

public class MovingEntity extends Entity{
    public ControlPanel.MoveDirection move_direction;
    public int speed = 4;

    List<ControlPanel.MoveDirection> getPossibleMoveDirections(Point tile) {
        List<ControlPanel.MoveDirection> result = new ArrayList<>();
        if (map.getTileMap()[tile.y - 1][tile.x] == 0) result.add(ControlPanel.MoveDirection.UP);
        if (map.getTileMap()[tile.y + 1][tile.x] == 0) result.add(ControlPanel.MoveDirection.DOWN);
        if (map.getTileMap()[tile.y][tile.x - 1] == 0) result.add(ControlPanel.MoveDirection.LEFT);
        if (map.getTileMap()[tile.y][tile.x + 1] == 0) result.add(ControlPanel.MoveDirection.RIGHT);
        return result;
    }
}
