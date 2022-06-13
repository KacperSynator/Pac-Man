package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import javax.swing.JScrollPane;

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
    /** scoreboard displayed at game over screen */
    ScrollPane scoreboard = new ScrollPane();

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
        for(int i=0;i<game_panel.lives;i++) {
            element2d.drawImage(heart, 1260+i* GamePanel.PIXEL/2, 910, GamePanel.PIXEL/2, GamePanel.PIXEL/2, null);
        }
    }

    /**
     * adds score to text file
     */
    void addScoreToScoreboard() {
        try {
            FileWriter file_in;
            file_in = new FileWriter(System.getProperty("user.dir") + "/src/assets/userInterface/top_scores.txt",true);
            file_in.write(game_panel.score+"\n");
            file_in.close();
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    /**
     * loads scoreboard from file top_scores.txt
     */
    void loadScoreBoard() {
        File file_out = new File(System.getProperty("user.dir") + "/src/assets/userInterface/top_scores.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(file_out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        List<Integer> scores = new ArrayList<>();
        while (scanner.hasNext()) {
          scores.add(scanner.nextInt());
        }
        scores.sort(Collections.reverseOrder());
        JList<Integer> jlist = new JList<>(scores.toArray(new Integer[scores.size()]));
        jlist.setBackground(Color.BLACK);
        jlist.setForeground(Color.WHITE);
        jlist.setFont(buttons_font);
        scoreboard.add(jlist);
        game_panel.add(scoreboard);
    }

    /**
     * draw gif with black background rectangle
     * @param element2d Graphics2D from java.awt.Graphics
     * @param gif loaded to image
     */
    void gifWithBackground(Graphics2D element2d, Image gif) {
        element2d.setColor(Color.BLACK);
        element2d.fillRect(GamePanel.SCREEN_WIDTH / 4 - WINDOW_BORDER, GamePanel.SCREEN_HEIGHT / 4 - WINDOW_BORDER,
                GamePanel.SCREEN_WIDTH / 2 + 2 * WINDOW_BORDER, GamePanel.SCREEN_HEIGHT / 2 + 2 * WINDOW_BORDER);
        element2d.setColor(Color.WHITE);
        element2d.drawImage(gif, GamePanel.SCREEN_WIDTH / 4, GamePanel.SCREEN_HEIGHT / 4,
                GamePanel.SCREEN_WIDTH / 2, GamePanel.SCREEN_HEIGHT / 2 ,null);
    }
    /**
     * draws game over window
     * @param element2d Graphics2D from java.awt.Graphics
     */
    void gameOverScreen(Graphics2D element2d) {
        super.paintComponent(element2d);

        gifWithBackground(element2d, game_over_gif);

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

        scoreboardScreen(element2d);
    }

    /**
     * draws win window
     * @param element2d Graphics2D from java.awt.Graphics
     */
    void winScreen(Graphics2D element2d) {
        super.paintComponent(element2d);

        gifWithBackground(element2d, win_gif);

        element2d.setFont(game_over_font);
        element2d.drawString("LEVEL COMPLETED", GamePanel.SCREEN_WIDTH / 2 - 310, GamePanel.SCREEN_HEIGHT / 2 + 230);

        next.setBounds(GamePanel.SCREEN_WIDTH / 4 + WINDOW_BORDER, GamePanel.SCREEN_HEIGHT / 4 + GamePanel.SCREEN_HEIGHT / 2 + 2 * WINDOW_BORDER,
                GamePanel.SCREEN_WIDTH / 7, GamePanel.SCREEN_HEIGHT / 15);
        quit.setBounds(GamePanel.SCREEN_WIDTH / 4 + WINDOW_BORDER + 2 * GamePanel.SCREEN_WIDTH / 7, GamePanel.SCREEN_HEIGHT / 4 + GamePanel.SCREEN_HEIGHT / 2 + 2 * WINDOW_BORDER,
                GamePanel.SCREEN_WIDTH / 7, GamePanel.SCREEN_HEIGHT / 15);
        next.repaint();
        quit.repaint();
    }

    /**
     * draws scoreboards loaded from file next to game over screen
     * @param element2d Graphics2D from java.awt.Graphics
     */
    void scoreboardScreen(Graphics2D element2d) {
        super.paintComponent(element2d);

        scoreboard.setBounds(GamePanel.SCREEN_WIDTH / 2 + 500, GamePanel.SCREEN_HEIGHT / 2 - 200,
                GamePanel.SCREEN_WIDTH / 6, GamePanel.SCREEN_HEIGHT / 2);
        element2d.setFont(game_over_font);
        element2d.drawString("SCORES", GamePanel.SCREEN_WIDTH / 2 + 500, GamePanel.SCREEN_HEIGHT / 2 - 200);

        scoreboard.repaint();
    }
}
