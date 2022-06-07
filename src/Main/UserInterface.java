package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class UserInterface extends JPanel implements ActionListener {

    BufferedImage heart;
    JButton reset = new JButton();
    JButton quit = new JButton();
    Map map;
    Image game_over_gif;
    Font game_over_font;
    UserInterface(Map map){
        this.map = map;
        game_over_gif = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/src/assets/userInterface/game_over.gif");
        loadFont();
        try {
            heart = ImageIO.read(this.getClass().getResourceAsStream("../userInterface/heart.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    void loadFont () {
        try {
            // "../assets/fonts/android-insomnia/and_ins_reg.ttf" // "../assets/fonts/games/Games.ttf"
            game_over_font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("../fonts/games/Games.ttf"));
            game_over_font = game_over_font.deriveFont(72.0f);
        } catch (Exception var2) {
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
        super.paintComponent(element2d);
        element2d.drawImage(game_over_gif, Map.SCREEN_WIDTH / 4, Map.SCREEN_HEIGHT / 4, Map.SCREEN_WIDTH / 2,Map.SCREEN_HEIGHT / 2 ,null);
        element2d.setFont(map.font);
        element2d.drawString("YOUR SCORE: " + map.score,Map.SCREEN_WIDTH / 2 - 140,Map.SCREEN_HEIGHT / 2 + 230);
        element2d.setFont(game_over_font);
        element2d.drawString("GAME OVER",Map.SCREEN_WIDTH / 2 - 190,Map.SCREEN_HEIGHT / 2 + 190);

        reset.setBounds(200,100,100,50);
        reset.addActionListener(this);
        map.add(reset);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == reset)
        {

        }
    }
}
