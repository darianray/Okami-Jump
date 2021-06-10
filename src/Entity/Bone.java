package Entity;

import Entity.*;
import TileMap.TileMap;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Bone extends Enemy {

    ///buffered image array to hold sprites
    private BufferedImage[] sprites;
    public Bone(TileMap tm) {
        super(tm);

     
        width = 30; ///square in sheet is 30x30
        height = 30;
        cwidth = 20;
        cheight = 20;
//		
//		health = maxHealth = 2;
//		damage = 1;

        ///load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
                    "/Sprites/Enemies/bone.gif"));

            ///load individual sprite
            sprites = new BufferedImage[1]; ///there are three different sprites in the sheet
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }
        } catch (Exception e) {

        }
        ///set animation
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        ///when start up, it will face the right direction
        right = true;
        facingRight = true;
    }

    public void update() {
        ///update sprite position

        ///make sure it won't collide
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        animation.update();
    }
    public boolean checkBoneCollision(Player p){
        if(intersects(p)){
            return true;
        }else
            return false;
    }

    ///draw the sprite
    public void draw(Graphics2D g) {
        ///don't draw sprite if it's not on screen

        setMapPosition();
        super.draw(g);
    }
}
