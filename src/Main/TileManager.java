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
        Objects.requireNonNull(map);
        Objects.requireNonNull(map);
        int var10001 = 960 / 64;
        Objects.requireNonNull(map);
        Objects.requireNonNull(map);
        this.mapLayout = new int[var10001][1600 / 64];
        this.getTileImage();
        this.loadMapLayout();
    }

    public void loadMapLayout() {
        try {
            InputStream stream = this.getClass().getResourceAsStream("../assets/pacman_mapa.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            int i = 0;

            while(true) {
                Objects.requireNonNull(this.map);
                Objects.requireNonNull(this.map);
                if (i >= 960 / 64) {
                    br.close();
                    break;
                }

                String line = br.readLine();
                String[] numbers = line.split("\t");
                int j = 0;

                while(true) {
                    Objects.requireNonNull(this.map);
                    Objects.requireNonNull(this.map);
                    if (j >= 1600 / 64) {
                        ++i;
                        break;
                    }

                    this.mapLayout[i][j] = Integer.parseInt(numbers[j]);
                    ++j;
                }
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public void getTileImage() {
        try {
            this.tile[0] = new Tile();
            this.tile[0].wall = ImageIO.read(this.getClass().getResourceAsStream("../assets/Wall2.png"));
            this.tile[1] = new Tile();
            this.tile[1].wall = ImageIO.read(this.getClass().getResourceAsStream("../assets/floor.png"));
            this.tile[2] = new Tile();
            this.tile[2].wall = ImageIO.read(this.getClass().getResourceAsStream("../assets/floor2.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void draw(Graphics2D element2d) {
        int i = 0;

        while(true) {
            Objects.requireNonNull(this.map);
            Objects.requireNonNull(this.map);
            if (i >= 960 / 64) {
                return;
            }

            int j = 0;

            while(true) {
                Objects.requireNonNull(this.map);
                Objects.requireNonNull(this.map);
                if (j >= 1600 / 64) {
                    ++i;
                    break;
                }

                BufferedImage var10001;
                int var10002;
                int var10003;
                if (this.mapLayout[i][j] == 1) {
                    var10001 = this.tile[0].wall;
                    var10002 = 64 * j;
                    var10003 = 64 * i;
                    Objects.requireNonNull(this.map);
                    Objects.requireNonNull(this.map);
                    element2d.drawImage(var10001, var10002, var10003, 64, 64, (ImageObserver)null);
                }

                if (this.mapLayout[i][j] == 0) {
                    var10001 = this.tile[2].wall;
                    var10002 = 64 * j;
                    var10003 = 64 * i;
                    Objects.requireNonNull(this.map);
                    Objects.requireNonNull(this.map);
                    element2d.drawImage(var10001, var10002, var10003, 64, 64, (ImageObserver)null);
                }

                if (this.mapLayout[i][j] == 2) {
                    var10001 = this.tile[1].wall;
                    var10002 = 64 * j;
                    var10003 = 64 * i;
                    Objects.requireNonNull(this.map);
                    Objects.requireNonNull(this.map);
                    element2d.drawImage(var10001, var10002, var10003, 64, 64, (ImageObserver)null);
                }

                ++j;
            }
        }
    }
}
