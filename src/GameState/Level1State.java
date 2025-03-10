package GameState;

import Entity.Enemies.*;
import java.awt.*;
import Entity.*;
import TileMap.*;
import Main.GamePanel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Audio.AudioPlayer;

public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg;
    private ScoreState ss;
    private Player player;

    ///container to hold enemies
    private LinkedList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private ArrayList<PlayerData> play;
    private LinkedList<Bone> bones;
    private LinkedList<Spider> spiders;
    ///declare hud
    private HUD hud;
    private boolean gameOverTransitioned = false;  // Add this flag to prevent multiple transitions

    ///declare a score
    public int score = 0;

    ///declare audio player for music
	private HashMap<String, AudioPlayer> sfx;

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;

        init();
    }

    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Resources/Tilesets/grasstileset3.gif");
        tileMap.loadMap("/Resources/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        ///	tileMap.setTween(1);
        
        sfx = new HashMap<String, AudioPlayer>();

		sfx.put("OkamiLevel1", new AudioPlayer("/Resources/Music/OkamiLevel1.wav"));

        ///new background
        bg = new Background("/Resources/Backgrounds/background.png", 0.1);

        ///new player
        player = new Player(tileMap);
        player.setPosition(100, 100);

        ///use populate enemies method to put enemies in level
        populateEnemies();
        
        ///populate arachniks
        populateSpiders();

         ///populate bones
        populateBones();
        ///initialize explosions
        explosions = new ArrayList<Explosion>();
        
       

        ///create new hud
        hud = new HUD(player);

        ///initialize music for level 1
        if (sfx.get("OkamiLevel1") != null) {
            sfx.get("OkamiLevel1").play(true); // Play music on loop
        }


        // if (bgMusic == null) {
        //     bgMusic = new AudioPlayer("/Resources/Music/OkamiLevel1.wav");
        // }

        // Play music (looping)
    //    bgMusic.play(true); // Loop the background music during the level
        ///play the music
        // bgMusic.play();
    }

    public void populateEnemies() {
        ///create enemies
        enemies = new LinkedList<Enemy>();
        Spike s;
        s = new Spike(tileMap);
        Point[] points = new Point[]{
            new Point(200, 100),
            new Point(860, 200),
            new Point(1020, 200),
            new Point(1525, 200),
            new Point(1680, 200),
            new Point(1800, 200),
            new Point(3000, 200),
            new Point(2950, 200),
            new Point(2800, 200),
            new Point(2850, 200),
            new Point(2900, 200)
        };
        for (int i = 0; i < points.length; i++) {
            s = new Spike(tileMap);
            s.setPosition(points[i].x, points[i].y);
            enemies.add(s);
        }
    }
    
     public void populateSpiders() {
        ///create enemies
        spiders = new LinkedList<Spider>();
        Spider a;
        a = new Spider(tileMap);
        Point[] points = new Point[]{
            new Point(2450, 155),
            new Point(2860, 170),
            new Point(2890, 170),
            new Point(1505, 105),
            new Point(1650, 133),
            new Point(1840, 45)
        };
        for (int i = 0; i < points.length; i++) {
            a = new Spider(tileMap);
            a.setPosition(points[i].x, points[i].y);
            spiders.add(a);
        }
    }
    
    
     public void populateBones() {
        ///create bones
        bones = new LinkedList<Bone>();
        Bone b;
        b = new Bone(tileMap);
        Point[] points = new Point[]{
            new Point(375, 130),
            new Point(1011, 110),
            new Point(1250, 80),
            new Point(2410, 110),
            new Point(1895, 110)
        };
        for (int i = 0; i < points.length; i++) {
            b = new Bone(tileMap);
            b.setPosition(points[i].x, points[i].y);
            bones.add(b);
        }
       
    }

    @Override
    public void update() {
        ///update player
        player.update();
        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getx(),
                GamePanel.HEIGHT / 2 - player.gety());     ////follows the player

        ///set background to scroll with sprite
        bg.setPosition(tileMap.getX(), tileMap.getY());

        player.checkAttack(enemies);
        player.checkSpiderAttack(spiders);
        ///update the enemy 
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            ///check whether enemy is dead and remove if true
            if (enemies.get(i).isDead()) {
                enemies.remove(i);
                i--;

                //initialize explosion, give explosion the enemies coordinates
                explosions.add(new Explosion(e.getx(), e.gety()));

                ///increment score
                score += 20;
                setScore(score);
            }
        }
        
         ///update the spiders
        for (int i = 0; i < spiders.size(); i++) {
            Spider a = spiders.get(i);
            a.update();
            ///check whether enemy is dead and remove if true
            if (spiders.get(i).isDead()) {
                spiders.remove(i);
                i--;

                //initialize explosion, give explosion the enemies coordinates
                explosions.add(new Explosion(a.getx(), a.gety()));

                ///increment score
                score += 10;
                setScore(score);
            }
        }
         for (int i = 0; i < bones.size(); i++) {
            Bone b = bones.get(i);
            b.update();
            ///check whether enemy is dead and remove if true
            if (b.checkBoneCollision(player) == true) {
                bones.remove(i);
                i--;

                //initialize explosion, give explosion the enemies coordinates
                explosions.add(new Explosion(b.getx(), b.gety()));

                ///increment score
                score += 45;
                setScore(score);
            }
        }
       
       
         ///check whether player is dead
         /// Inside your update or collision check logic
         if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
            System.out.println("y " + player.gety() + " tileMapHeight " + tileMap.getHeight());
            
            if (!gameOverTransitioned) {
                int high = returnHigh();
                if (score > high) {
                    JFrame f = new JFrame();
                    String name = JOptionPane.showInputDialog(f, "Enter Name for Our LeaderBoard! :D");
                    ss.checkForHighScore(score, name);
                }
                
                // Stop music when transitioning to game over state
                System.out.println("Transitioning to game over state");
                sfx.get("OkamiLevel1").close();

                // Transition to game over state
                gsm.setState(GameStateManager.GAMEOVERSTATE2);
                gameOverTransitioned = true;  // Prevent further transitions

            }
        }
        
         

        

        ///update explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            ///remove explosion
            if (explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }
      

        ////check whether player is dead
    }

    public void draw(Graphics2D g) {
        ///draw background
        bg.draw(g);

        //draw tileMap
        tileMap.draw(g);

        ///draw player
        player.draw(g);

        ///draw enemies 
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        
         ///draw spiders 
        for (int i = 0; i < spiders.size(); i++) {
            spiders.get(i).draw(g);
        }
          ///draw bones
        for (int i = 0; i < bones.size(); i++) {
            bones.get(i).draw(g);
        }


        ///draw explosions
        for (int i = 0; i < explosions.size(); i++) {
            //get position of explosion
            explosions.get(i).setMapPosition((int) tileMap.getX(), (int) tileMap.getY());
            explosions.get(i).draw(g);

        }
        
       


        ///draw hud, draw last because it goes on top of everything
        hud.draw(g);

        ///draw score
        g.setColor(Color.BLACK);
        g.drawString("Score: " + String.valueOf(score), 250, 22);

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;

    }

    public int returnHigh() {

        ss = new ScoreState();
        play = ss.getPlayerData("player.csv");
        PlayerData hs1 = play.get(4);
        int scoreTest = hs1.getScore();
        return scoreTest;
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }
        if (k == KeyEvent.VK_UP) {
            player.setUp(true);
        }
        if (k == KeyEvent.VK_DOWN) {
            player.setDown(true);
        }
        if (k == KeyEvent.VK_SPACE) {
            player.setJumping(true);
        }
        if (k == KeyEvent.VK_E) {
            player.setGliding(true);
        }
        if (k == KeyEvent.VK_R) {
            player.setScratching();
        }
        if (k == KeyEvent.VK_F) {
            player.setFiring();
        }
    }

    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
        if (k == KeyEvent.VK_UP) {
            player.setUp(false);
        }
        if (k == KeyEvent.VK_DOWN) {
            player.setDown(false);
        }
        if (k == KeyEvent.VK_SPACE) {
            player.setJumping(false);
        }
        if (k == KeyEvent.VK_E) {
            player.setGliding(false);
        }
    }
}
