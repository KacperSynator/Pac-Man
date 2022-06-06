//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

public class CollisionPanel {
    Map map;
    public CollisionPanel(Map map) {
        this.map = map;
    }
    public void collisionChecker(Entity entity) {
        int left_border = entity.position.x + entity.hitbox.x;
        int right_border = entity.position.x + entity.hitbox.x + entity.hitbox.width;
        int up_border = entity.position.y + entity.hitbox.y;
        int down_border = entity.position.y + entity.hitbox.y + entity.hitbox.height ;

        int left_collision = left_border/ Map.PIXEL;
        int right_collision = right_border/ Map.PIXEL;
        int up_collision = up_border/Map.PIXEL;
        int down_collision = down_border/ Map.PIXEL;

        int tile1,tile2;

        switch(entity.direction) {
            case "up":
                up_collision = (up_border - entity.speed) / Map.PIXEL;
                tile1 = map.tileManager.mapLayout[up_collision][left_collision];
                tile2 = map.tileManager.mapLayout[up_collision][right_collision];
                System.out.println(right_collision + " " + up_collision);
                System.out.println(left_collision + " " + up_collision);
                if (tile1 == 1 || tile2 == 1) {
                    entity.collisionDetected = true;
                }
                break;
            case "down":
                down_collision = (down_border - entity.speed) / Map.PIXEL;
                tile1 = map.tileManager.mapLayout[down_collision][left_collision];
                tile2 = map.tileManager.mapLayout[down_collision][right_collision];
                System.out.println(left_collision + " " + down_collision);
                System.out.println(right_collision + " " + down_collision);
                if (tile1 == 1 || tile2 == 1) {
                    entity.collisionDetected = true;
                }
                break;
            case "right":
                right_collision = (right_border - entity.speed) / Map.PIXEL;
                tile1 = map.tileManager.mapLayout[up_collision][right_collision];
                tile2 = map.tileManager.mapLayout[down_collision][right_collision];
                System.out.println(right_collision + " " + up_collision);
                System.out.println(right_collision + " " + down_collision);
                if (tile1 == 1 || tile2 == 1) {
                    entity.collisionDetected = true;
                }
                break;
            case "left":
                left_collision = (left_border - entity.speed) / Map.PIXEL;
                tile1 = map.tileManager.mapLayout[up_collision][left_collision];
                tile2 = map.tileManager.mapLayout[down_collision][left_collision];
                System.out.println(left_collision + " " + up_collision);
                System.out.println(left_collision + " " + down_collision);
                if (tile1 == 1 || tile2 == 1) {
                    entity.collisionDetected = true;
                }
                break;
        }

    }
}
