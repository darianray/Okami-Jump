package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

import Entity.PlayerData;
import Main.MyFile;
import TileMap.Background;

public class HighScoreState extends GameState {

   
    private Font titleFont;
    private Background bg;

    JLabel label = new JLabel();
    private Font font;

    private int currentChoice = 0;
    private String[] options = {
        "Go Back To Menu"
    };

    ArrayList<PlayerData> play1 = getPlayerData("player.csv");
    ///get player data for names of highscores
    String n1 = play1.get(0).getName();
    String n2 = play1.get(1).getName();
    String n3 = play1.get(2).getName();
    String n4 = play1.get(3).getName();
    String n5 = play1.get(4).getName();
    ////get scores of players
    int i1 = play1.get(0).getScore();
    int i2 = play1.get(1).getScore();
    int i3 = play1.get(2).getScore();
    int i4 = play1.get(3).getScore();
    int i5 = play1.get(4).getScore();

    //convert scores to strings
    String s1 = Integer.toString(i1);
    String s2 = Integer.toString(i2);
    String s3 = Integer.toString(i3);
    String s4 = Integer.toString(i4);
    String s5 = Integer.toString(i5);

    public HighScoreState(GameStateManager gsm) {

        this.gsm = gsm;

        try {

            bg = new Background("/Resources/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.1, 0);
            
            
            titleFont = new Font(
                    "Century Gothic",
                    Font.BOLD,
                    36);

            font = new Font("Arial", Font.PLAIN, 16);
            label.setText("This is a basic label");

        } catch (Exception e) {
            //	e.printStackTrace();
        }
    }

    public void labels() {

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(int k) {
        // TODO Auto-generated method stub
        if ((k == KeyEvent.VK_ENTER) || (k == KeyEvent.VK_ESCAPE)) {
            gsm.setState(GameStateManager.MENUSTATE);
        }

    }

    @Override
    public void keyReleased(int k) {
        // TODO Auto-generated method stub

    }

    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        // draw title
        g.setColor(Color.yellow);
        g.setFont(titleFont);
        g.drawString("High Scores ", 67, 33);

        // draw menu options
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], 95, 230);
        }
        g.setColor(Color.BLACK);
        ///draw score and name title
         
        //draw strings of names
        g.drawString(n1, 30, 110);
        g.drawString(n2, 30, 130);
        g.drawString(n3, 30, 150);
        g.drawString(n4, 30, 170);
        g.drawString(n5, 30, 190);

        ///draw string of ints
        g.drawString(s1, 240, 110);
        g.drawString(s2, 240, 130);
        g.drawString(s3, 240, 150);
        g.drawString(s4, 240, 170);
        g.drawString(s5, 240, 190);
        
        Font subtitleFont = new Font( "Century Gothic",
                    Font.PLAIN,
                    20);
        g.setFont(subtitleFont);
        g.drawString("Name: ", 30, 70);
        g.drawString("Score: ", 240, 70);

    }

    public ArrayList<PlayerData> getPlayerData(String fname) {
        ArrayList<String> playerList = MyFile.myFileReader(fname);
        ArrayList<PlayerData> playerInfo = new ArrayList<>();

        for (int i = 0; i < playerList.size(); i++) {
            String str = playerList.get(i);
            String fields[] = str.split(",");

            PlayerData pD = new PlayerData(fields[0].trim(), Integer.parseInt(fields[1].trim()));
            playerInfo.add(pD);
        }

        return playerInfo;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }
}
