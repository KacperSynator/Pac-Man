//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Main;

import Main.Map;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.imageio.ImageIO;

public class TileManager {
    Map map;
    Tile[] tile;
    int[][] mapLayout;

    public TileManager(Map map) {
        this.map = map;
        this.tile = new Tile[3];
        this.mapLayout = new int[Map.screen_height / Map.pixel][Map.screen_width / Map.pixel];
        this.getTileImage();
        this.loadMapLayout();
    }

    public void loadMapLayout() {
        try {
            Objects.requireNonNull(this.map);
            BufferedReader br = new BufferedReader (
                                    new InputStreamReader (
                                            this.getClass().getResourceAsStream("../assets/pacman_mapa.txt")
                                    ));

            for (int i = 0; i < Map.screen_height / Map.pixel; ++i) {
                String[] numbers = br.readLine().split("\t");

                for (int j = 0; j < Map.screen_width / Map.pixel; ++j) {
                    this.mapLayout[i][j] = Integer.parseInt(numbers[j]);
                }
            }
            br.close();

        } catch (Exception var7) {
            var7.printStackTrace();
        }
    }

    public void getTileImage() {
        try {
            this.tile[0] = new Tile(ImageIO.read(this.getClass().getResourceAsStream("../assets/floor.png")));
            this.tile[1] = new Tile(ImageIO.read(this.getClass().getResourceAsStream("../assets/Wall2.png")));
            this.tile[2] = new Tile(ImageIO.read(this.getClass().getResourceAsStream("../assets/floor2.png")));
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void draw(Graphics2D element2d) {
        Objects.requireNonNull(this.map);
        for (int i = 0; i < Map.screen_height / Map.pixel; ++i) {
            for (int j = 0; j < Map.screen_width / Map.pixel; ++j) {
                switch (this.mapLayout[i][j]) {
                    case 0 -> element2d.drawImage(this.tile[0].img, Map.pixel * j, Map.pixel * i, Map.pixel,
                                                  Map.pixel, null);

                    case 1 -> element2d.drawImage(this.tile[1].img, Map.pixel * j, Map.pixel * i, Map.pixel,
                                                  Map.pixel, null);

                    case 2 -> element2d.drawImage(this.tile[2].img, Map.pixel * j, Map.pixel * i, Map.pixel,
                                                  Map.pixel, null);
                }
            }
        }
    }
}
