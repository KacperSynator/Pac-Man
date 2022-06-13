package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * class implementing user interface, extends JPanel
 */
public class UserInterface extends JPanel {
    /** border of game over window */
    final int WINDOW_BORDER = 50;
    /** image of heart */
    BufferedImage heart;
    /** button for resetting the game */
    JButton reset = new JButton("RESET");
    /** button for quiting the game */
    JButton quit = new JButton("QUIT");
    /** button for starting next game level */
    JButton next = new JButton("NEXT");
    /** game_panel object */
    GamePanel game_panel;
    /** gif displayed in game over window */
    Image game_over_gif;
    /** gif displayed in win window */
    Image win_gif;
    /** font for "GAME OVER" text */
    Font game_over_font;
    /** font for buttons' text */
    Font buttons_font;

    /**
     * constructor, loads gif and fonts from file
     * @param game_panel game_panel object
     */
    UserInterface(GamePanel game_panel) {
        this.game_panel = game_panel;
        game_over_gif = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/src/assets/userInterface/game_over.gif");
        win_gif = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "/src/assets/userInterface/win_screen.gif");
        loadFont();
        createButtons();
        try {
            heart = ImageIO.read(this.getClass().getResourceAsStream("../userInterface/heart.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    /**
     * loads fonts from file and creates Font objects
     */
    void loadFont () {
        try {
            // "../assets/fonts/android-insomnia/and_ins_reg.ttf" // "../assets/fonts/games/Games.ttf"
            game_over_font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("../fonts/games/Games.ttf"));
            game_over_font = game_over_font.deriveFont(72.0f);
            buttons_font = game_over_font.deriveFont(36.0f);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    /**
     * creates and configures buttons
     */
    void createButtons() {
        reset.setFont(buttons_font);
        reset.setBackground(Color.BLACK);
        reset.setForeground(Color.WHITE);
        reset.addActionListener(e -> game_panel.resetGame());
        game_panel.add(reset);

        next.setFont(buttons_font);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(e -> game_panel.nextLevel());
        game_panel.add(next);

        quit.setFont(buttons_font);
        quit.setBackground(Color.BLACK);
        quit.setForeground(Color.WHITE);
        quit.addActionListener(e -> game_panel.quit());
        game_panel.add(quit);
    }

    /**
     * draws score and remaining lives
     * @param element2d Graphics2D from java.awt.Graphics
     */
    void paintInterfaceInGame(Graphics2D element2d) {
        element2d.setColor(Color.white);
        element2d.setFont(buttons_font);
        element2d.drawString("Score: " + game_panel.score, 1370, 935);
        for(int i=0;i<game_panel.lives;i++)
        {
            element2d.drawImage(heart, 1260+i* GamePanel.PIXEL/2, 910, GamePanel.PIXEL/2, GamePanel.PIXEL/2, null);
        }
    }

    /**
     * draws game over window
     * @param element2d Graphics2D from java.awt.Graphics
     */
    void gameOverScreen(Graphics2D element2d) {
        super.paintComponent(element2d);
        element2d.setColor(Color.BLACK);
        element2d.fillRect(GamePanel.SCREEN_WIDTH / 4 - WINDOW_BORDER, GamePanel.SCREEN_HEIGHT / 4 - WINDOW_BORDER,
                        GamePanel.SCREEN_WIDTH / 2 + 2 * WINDOW_BORDER, GamePanel.SCREEN_HEIGHT / 2 + 2 * WINDOW_BORDER);
        element2d.setColor(Color.WHITE);
        element2d.drawImage(game_over_gif, GamePanel.SCREEN_WIDTH / 4, GamePanel.SCREEN_HEIGHT / 4,
                      GamePanel.SCREEN_WIDTH / 2, GamePanel.SCREEN_HEIGHT / 2 ,null);
        element2d.setFont(buttons_font);
        element2d.drawString("YOUR SCORE: " + game_panel.score, GamePanel.SCREEN_WIDTH / 2 - 140, GamePanel.SCREEN_HEIGHT / 2 + 230);
        element2d.setFont(game_over_font);
        element2d.drawString("GAME OVER", GamePanel.SCREEN_WIDTH / 2 - 190, GamePanel.SCREEN_HEIGHT / 2 + 190);

        reset.setBounds(GamePanel.SCREEN_WIDTH / 4 + WINDOW_BORDER, GamePanel.SCREEN_HEIGHT / 4 + GamePanel.SCREEN_HEIGHT / 2 + 2 * WINDOW_BORDER,
                     GamePanel.SCREEN_WIDTH / 7, GamePanel.SCREEN_HEIGHT / 15);
        quit.setBounds(GamePanel.SCREEN_WIDTH / 4 + WINDOW_BORDER + 2 * GamePanel.SCREEN_WIDTH / 7, GamePanel.SCREEN_HEIGHT / 4 + GamePanel.SCREEN_HEIGHT / 2 + 2 * WINDOW_BORDER,
                    GamePanel.SCREEN_WIDTH / 7, GamePanel.SCREEN_HEIGHT / 15);
        reset.repaint();
        quit.repaint();
    }

    /**
     * draws win window
     * @param element2d Graphics2D from java.awt.Graphics
     */
    void winScreen(Graphics2D element2d) {
        super.paintComponent(element2d);
        element2d.setColor(Color.BLACK);
        element2d.fillRect(GamePanel.SCREEN_WIDTH / 4 - WINDOW_BORDER, GamePanel.SCREEN_HEIGHT / 4 - WINDOW_BORDER,
                GamePanel.SCREEN_WIDTH / 2 + 2 * WINDOW_BORDER, GamePanel.SCREEN_HEIGHT / 2 + 2 * WINDOW_BORDER);
        element2d.setColor(Color.WHITE);
        element2d.drawImage(win_gif, GamePanel.SCREEN_WIDTH / 4, GamePanel.SCREEN_HEIGHT / 4,
                GamePanel.SCREEN_WIDTH / 2, GamePanel.SCREEN_HEIGHT / 2 ,null);
        element2d.setFont(game_over_font);
        element2d.drawString("LEVEL COMPLETED", GamePanel.SCREEN_WIDTH / 2 - 310, GamePanel.SCREEN_HEIGHT / 2 + 230);

        next.setBounds(GamePanel.SCREEN_WIDTH / 4 + WINDOW_BORDER, GamePanel.SCREEN_HEIGHT / 4 + GamePanel.SCREEN_HEIGHT / 2 + 2 * WINDOW_BORDER,
                GamePanel.SCREEN_WIDTH / 7, GamePanel.SCREEN_HEIGHT / 15);
        quit.setBounds(GamePanel.SCREEN_WIDTH / 4 + WINDOW_BORDER + 2 * GamePanel.SCREEN_WIDTH / 7, GamePanel.SCREEN_HEIGHT / 4 + GamePanel.SCREEN_HEIGHT / 2 + 2 * WINDOW_BORDER,
                GamePanel.SCREEN_WIDTH / 7, GamePanel.SCREEN_HEIGHT / 15);
        next.repaint();
        quit.repaint();

    }
}
