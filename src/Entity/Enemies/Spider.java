package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Spider extends Enemy{
	
	///buffered image array to hold sprites
	private BufferedImage[] sprites;

	public Spider(TileMap tm) {
		super(tm);
	
		width = 30; ///square in sheet is 30x30
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		
		///load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
		"/Resources/Sprites/Enemies/enemySpider.gif"));
			
			///load individual sprite
			sprites = new BufferedImage[1]; ///there are three different sprites in the sheet
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		}catch(Exception e) {
			
		}
		///set animation
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		///when start up, it will face the right direction
		right = true;
		facingRight = true;
	}
	private void getNextPosition() {
		///find next position of player via keyboard input
		// movement
				if(left) {
					dx -= moveSpeed;
					if(dx < -maxSpeed) {
						dx = -maxSpeed;
					}
				}
				else if(right) {
					dx += moveSpeed;
					if(dx > maxSpeed) {
						dx = maxSpeed;
					}
				}
				
				// falling
				if(falling) {
					dy += fallSpeed;
				}
				
			}
	public void update() {
		///update sprite position
		getNextPosition();
		///make sure it won't collide
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		///check flinching
		if(flinching) {
			///check how much time has went by 
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			///if greater than .4 seconds (400) then stop flinching
			if(elapsed > 400) {
				flinching = false;
			}
		}
		
		///if it hits a wall, go the other direction
		///anytime something hits a wall, dx is set to 0, so check dx and direction it is facing
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		///do this for the other direction as well
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		animation.update();
	}
         public boolean checkArachnikCollision(Player p){
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
