package Main;

/**
 * implementation of 2D point
 */
public class Point {
    /** x coordinate */
    public int x = 0;
    /** y coordinate */
    public int y = 0;

    /**
     * parameterless constructor
     */
    public Point() {}

    /**
     * constructor from x, y coordinates
     * @param x position
     * @param y position
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * calculates euclidean distance between two points
     * @param a point
     * @param b point
     * @return euclidean distance
     */
    static double calculateDistance(Point a, Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }
}
