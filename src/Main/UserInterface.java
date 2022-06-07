package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UserInterface {

    BufferedImage heart;
    BufferedImage game_over_screen;

    Map map;
    UserInterface(Map map){
        this.map = map;
        try {
            heart = ImageIO.read(this.getClass().getResourceAsStream("../assets/heart.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
        try {
            game_over_screen = ImageIO.read(this.getClass().getResourceAsStream("../assets/userInterface/game_over_screen.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }
    void paintInterfaceInGame(Graphics2D element2d)
    {
        element2d.setColor(Color.white);
        element2d.setFont(map.font);
        element2d.drawString("Score: " + map.score, 1370, 935);
        for(int i=0;i<map.lives;i++)
        {
            element2d.drawImage(heart, 1260+i*Map.PIXEL/2, 910, Map.PIXEL/2, Map.PIXEL/2, null);
        }
    }

    void gameOverScreen(Graphics2D element2d)
    {
        element2d.drawImage(game_over_screen, 0, 0, Map.SCREEN_WIDTH, Map.SCREEN_HEIGHT, null);
        element2d.setColor(Color.white);
        element2d.setFont(map.font);
        element2d.drawString("YOUR SCORE: " + map.score,Map.SCREEN_WIDTH/2-200,Map.SCREEN_HEIGHT/2);
    }
}
