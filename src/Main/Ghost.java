package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Ghost extends Entity {
    public enum Personality {BLINKY, INKY, CLYDE}

    ControlPanel.MoveDirection move_direction = ControlPanel.MoveDirection.UP;
    Personality personality;
    int speed = 4;
    Map map;

    public Ghost(Map map, Personality personality) {
        this.map = map;
        this.personality = personality;
        this.getGhostImage();
        this.setPostion();
    }

    void setPostion() {
        switch (personality) {
            case BLINKY -> position = new Point(12 * Map.PIXEL, 6 * Map.PIXEL);
            case INKY -> position = new Point(13 * Map.PIXEL, 6 * Map.PIXEL);
            case CLYDE -> position = new Point(14 * Map.PIXEL, 6 * Map.PIXEL);
        }
    }

    void getGhostImage() {
        try {
            switch (personality) {
                case BLINKY -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/purple_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/purple_ghost_2.png"));
                }
                case INKY -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/blue_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/blue_ghost_2.png"));
                }
                case CLYDE -> {
                    this.frame1 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/green_ghost_1.png"));
                    this.frame2 = ImageIO.read(this.getClass().getResourceAsStream("../ghosts/green_ghost_2.png"));
                }
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    java.util.List<ControlPanel.MoveDirection> getPossibleMoveDirections(Point tile) {
        List<ControlPanel.MoveDirection> result = new ArrayList<>();
        if (map.getTileMap()[tile.y - 1][tile.x] == 0) result.add(ControlPanel.MoveDirection.UP);
        if (map.getTileMap()[tile.y + 1][tile.x] == 0) result.add(ControlPanel.MoveDirection.DOWN);
        if (map.getTileMap()[tile.y][tile.x - 1] == 0) result.add(ControlPanel.MoveDirection.LEFT);
        if (map.getTileMap()[tile.y][tile.x + 1] == 0) result.add(ControlPanel.MoveDirection.RIGHT);
        return result;
    }

    double calculateDistance(Point a, Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    void updateMoveDirection() {
        switch (personality) {
            case INKY -> moveToTile(map.pacman.getTile());
            case BLINKY -> moveToTile(new Point(1, 14));
            case CLYDE -> moveToTile(new Point(20, 14));
        }
    }

    void removeBackDir(List<ControlPanel.MoveDirection> dirs) {
        if (dirs.size() == 1) return;
        switch (move_direction) {
            case UP -> dirs.remove(ControlPanel.MoveDirection.DOWN);
            case DOWN -> dirs.remove(ControlPanel.MoveDirection.UP);
            case RIGHT -> dirs.remove(ControlPanel.MoveDirection.LEFT);
            case LEFT -> dirs.remove(ControlPanel.MoveDirection.RIGHT);
        }
    }

    void moveToTile(Point tile) {
        Point current_pos = getCenterTile();
        var dirs =  getPossibleMoveDirections(getCenterTile());
        removeBackDir(dirs);
        ControlPanel.MoveDirection best_dir = null;
        double min_dist = Double.MAX_VALUE;
        for (var dir : dirs) {
            Point next_tile = current_pos;
            switch (dir) {
                case UP -> next_tile.y -= 1;
                case DOWN -> next_tile.y += 1;
                case LEFT -> next_tile.x -= 1;
                case RIGHT -> next_tile.x += 1;
            }
            var dist = calculateDistance(tile, next_tile);
            if (min_dist >= dist) {
                min_dist = dist;
                best_dir = dir;
            }
        }
        if (best_dir != null)  move_direction = best_dir;
    }

    public void update() {
        if (isInCenter()) updateMoveDirection();

        switch (this.move_direction) {
            case UP -> this.position.y -= this.speed;
            case DOWN -> this.position.y += this.speed;
            case LEFT -> this.position.x -= this.speed;
            case RIGHT -> this.position.x += this.speed;
        }
        ++this.counter;

        if (this.counter >= 6) {
            this.frameCounter = 1;
        }

        if (this.counter >= 12) {
            this.frameCounter = 0;
            this.counter = 0;
        }
    }

    public void draw(Graphics2D element2d) {
        BufferedImage image = null;

        switch (this.frameCounter) {
            case 0 -> image = this.frame1;
            case 1 -> image = this.frame2;
        }

        element2d.drawImage(image, this.position.x, this.position.y, Map.PIXEL, Map.PIXEL, null);
    }
}
