 package Entity;
 
 import TileMap.TileMap;
 import java.awt.image.BufferedImage;
 import javax.imageio.ImageIO;
 import java.awt.*;
 

public class FireBall extends MapObject {  ///all game objects extend mapObject
 
	private boolean hit; //whether fireball has hit something
	private boolean remove; ///whether fireball needs to be removed
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	public FireBall(TileMap tm, boolean right) {  //tells which way the fireball is facing
		
		super(tm);
		
		facingRight = right;
		///going right
		moveSpeed = 3.8;
		if(right) dx = moveSpeed;
		else dx -= moveSpeed;
		
		///width and height for tilemap
		width = 30;
		height = 30;
		///real width and height in relation to sprite
		cwidth = 14;
		cheight = 14;
		
		///load sprites for fireball
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
					"/Sprites/Player/fireball.gif"));
			///read in spritesheet for fireball via bufferedimage array and for loop
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
			
			///read in sprite sheet for hit sprites
			hitSprites = new BufferedImage[3];
			for(int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(i * width, height, width, height);
			}
			///set animation
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	////check whether fireball has hit something
	public void setHit() {
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}
	
	////check whether fireball should be taken out of game
	public boolean shouldRemove() {return remove;}
	
	public void update() {
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		///remove fireball
		if(dx == 0 && !hit) {
			setHit();
		}
		animation.update();
		
		///check whether fireball should be removed
		if(hit && animation.hasPlayedOnce()) {
			remove = true;
		}
	}
	///draw the fireball sprite
	public void draw(Graphics2D g) {
		setMapPosition();
		
		super.draw(g);
	}
		
	}

