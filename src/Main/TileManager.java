//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.imageio.ImageIO;


/**
 * class handling game map and tiles
 */
public class TileManager {
    /** map object */
    GamePanel map;
    /** array of tiles of different types*/
    Tile[] tile;
    /** matrix with layout of tile type */
    int[][] mapLayout;


    /**
     * constructor
     * @param map map object
     */
    public TileManager(GamePanel map) {
        this.map = map;
        this.tile = new Tile[3];
        this.mapLayout = new int[GamePanel.SCREEN_HEIGHT / GamePanel.PIXEL][GamePanel.SCREEN_WIDTH / GamePanel.PIXEL];
        this.getTileImage();
        this.loadMapLayout();
    }

    /**
     * map layout getter
     * @return mapLayout
     */
    public int[][] getMapLayout() { return mapLayout; }


    /**
     * reads map layout from file pacman_map.txt into mapLayout member
     */
    public void loadMapLayout() {
        try {
            Objects.requireNonNull(this.map);
            BufferedReader br = new BufferedReader (
                                    new InputStreamReader (
                                            this.getClass().getResourceAsStream("../map/pacman_mapa.txt")
                                    ));

            for (int i = 0; i < GamePanel.SCREEN_HEIGHT / GamePanel.PIXEL; ++i) {
                String[] numbers = br.readLine().split("\t");

                for (int j = 0; j < GamePanel.SCREEN_WIDTH / GamePanel.PIXEL; ++j) {
                    this.mapLayout[i][j] = Integer.parseInt(numbers[j]);
                }
            }
            br.close();

        } catch (Exception var7) {
            var7.printStackTrace();
        }
    }

    /**
     * reads tile images from files into tile array member
     */
    public void getTileImage() {
        try {
            this.tile[0] = new Tile(ImageIO.read(this.getClass().getResourceAsStream("../map/floor.png")));
            this.tile[1] = new Tile(ImageIO.read(this.getClass().getResourceAsStream("../map/wall.png")));
            this.tile[2] = new Tile(ImageIO.read(this.getClass().getResourceAsStream("../map/floor2.png")));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    /**
     * draws tiles in game window
     * @param element2d Graphics2D from java.awt.Graphics
     */
    public void draw(Graphics2D element2d) {
        Objects.requireNonNull(this.map);
        for (int i = 0; i < GamePanel.SCREEN_HEIGHT / GamePanel.PIXEL; ++i) {
            for (int j = 0; j < GamePanel.SCREEN_WIDTH / GamePanel.PIXEL; ++j) {
                switch (this.mapLayout[i][j]) {
                    case 0 -> element2d.drawImage(this.tile[0].img, GamePanel.PIXEL * j, GamePanel.PIXEL * i, GamePanel.PIXEL,
                                                  GamePanel.PIXEL, null);

                    case 1 -> element2d.drawImage(this.tile[1].img, GamePanel.PIXEL * j, GamePanel.PIXEL * i, GamePanel.PIXEL,
                                                  GamePanel.PIXEL, null);

                    case 2 -> element2d.drawImage(this.tile[2].img, GamePanel.PIXEL * j, GamePanel.PIXEL * i, GamePanel.PIXEL,
                                                  GamePanel.PIXEL, null);
                }
            }
        }
    }
}
