package Entity;

import TileMap.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

import Audio.AudioPlayer;
import Entity.Enemies.Spider;
public class Player extends MapObject{
	
	///player variables
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
     //   private boolean doubleJump;
	//private boolean alreadyDoubleJump;
        //private double doubleJumpStart;
	
	////fireball
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	
	///scratching
	///keyboard input for boolean
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;
	
	///gliding 
	private boolean gliding;
	
	///animations(indices of sprite animations)
	private ArrayList<BufferedImage[]> sprites;
	///number of frames in each animation
	private final int[] numFrames = {
		///ex: 8 walking frames, so 8 in array
			2, 8, 1, 2, 4, 2, 5
	};
	///animation actions  (one for every action)
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;
	
	///hashmap to hold string for name of music and audioplayer
	private HashMap<String, AudioPlayer> sfx;
	
	///set tileMap and tileSize
	public Player(TileMap tm) {
		///superclass
		super(tm);
		
		///for reading in sprite sheets
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
              //  doubleJumpStart = -4;
		
		facingRight = true;
		health = maxHealth = 5;
		fire = maxFire = 2500;
		
		fireCost = 200;
		fireBallDamage = 5;
		fireBalls = new ArrayList<FireBall>();
		
		scratchDamage = 8;
		scratchRange = 40;
		
		///load sprites
		try {
			///load sprite sheet
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
					"/Sprites/Player/playersprites1.gif")); 
			sprites = new ArrayList<BufferedImage[]>();
			for(int i= 0; i < 7; i++) {
				///7 array options
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					if(i != 6) {
					bi[j] = spritesheet.getSubimage(
							j * width,
							i * height,
							width, height);
				}else {
					bi[j] = spritesheet.getSubimage(
							j * width * 2,
							i * height,
							width * 2, height);
				}
				}
				sprites.add(bi);
			}
		}catch(Exception e) {
			//e.printStackTrace();
		}
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
		//sound effects
		sfx = new HashMap<String, AudioPlayer>();
		///audio for jump
		sfx.put("jump", new AudioPlayer("/SFX/jump.mp3"));
		sfx.put("scratch", new AudioPlayer("/SFX/scratch.mp3"));
	}
	
	///getters 
	public int getHealth() { return health;}
	public int getMaxHealth() { return maxHealth;}
	public int getFire() { return fire;}
	public int getMaxFire() { return maxFire;}
	
	///setters
//        public void setJumping(boolean b){
//		if(b && !jumping && falling && !alreadyDoubleJump) {
//			doubleJump = true;
//		}
//		jumping = b;
//	}
        
	public void setFiring() {
		firing = true;
	}
	
	public void setScratching() {
		scratching = true;
	}
	public void setGliding(boolean b) {
		gliding = b;
	}
	
        	///check whether spiders are being attacked by player
	public void checkSpiderAttack(LinkedList<Spider> spiders) {
		
		///loop through enemy array
		for(int i = 0; i < spiders.size(); i++) {
			///get enemy
			Spider e = spiders.get(i);
			
			///check scratch attack
			if(scratching) {
				//check whether it's facing right
				if(facingRight) {
					if(
					    ///check whether enemy is within range of attack / in front of player sprite
				     	///if the enemy is to the right and the enemy is less than the right of us plus 
						///the range of the attack	
						e.getx() > x &&
						e.getx() < x + scratchRange && 
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2 //if the enemies y position is on par with sprite
					) {
						e.hit(scratchDamage);
					}
				}
				else {
					///if facing left
					if(
						e.getx() < x &&
						e.getx() > x - scratchRange &&
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2
					) {
						e.hit(scratchDamage);
					}
				}
			}
			
			///fireball attack, loop through active fireballs
			for(int j = 0; j < fireBalls.size(); j++) {
				///check whther fireball and enemy have collided
				if(fireBalls.get(j).intersects(e)) {
					e.hit(fireBallDamage);
					fireBalls.get(j).setHit();
					break;
				}
			}
			
			// check enemy collision
			if(intersects(e)) {
				hit(e.getDamage());
			}
			
		}
		
	}
	///check whether enemies are being attacked by player
	public void checkAttack(LinkedList<Enemy> enemies) {
		
		///loop through enemy array
		for(int i = 0; i < enemies.size(); i++) {
			///get enemy
			Enemy e = enemies.get(i);
			
			///check scratch attack
			if(scratching) {
				//check whether it's facing right
				if(facingRight) {
					if(
					    ///check whether enemy is within range of attack / in front of player sprite
				     	///if the enemy is to the right and the enemy is less than the right of us plus 
						///the range of the attack	
						e.getx() > x &&
						e.getx() < x + scratchRange && 
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2 //if the enemies y position is on par with sprite
					) {
						e.hit(scratchDamage);
					}
				}
				else {
					///if facing left
					if(
						e.getx() < x &&
						e.getx() > x - scratchRange &&
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2
					) {
						e.hit(scratchDamage);
					}
				}
			}
			
			///fireball attack, loop through active fireballs
			for(int j = 0; j < fireBalls.size(); j++) {
				///check whther fireball and enemy have collided
				if(fireBalls.get(j).intersects(e)) {
					e.hit(fireBallDamage);
					fireBalls.get(j).setHit();
					break;
				}
			}
			
			// check enemy collision
			if(intersects(e)) {
				hit(e.getDamage());
			}
			
		}
		
	}
	///deal damage to the player if hit by enemy
	public void hit(int damage) {
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
       
	
	private void getNextPosition() {
		///find next position of player via keyboard input
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
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}///player has come to a full stop
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}/////cannot move while attacking, except while in air
		if((currentAction == SCRATCHING || currentAction == FIREBALL) && !(jumping || falling)){
			///x coordinate = 0, cannot move
			dx = 0;
		}
		///jumping 
		if(jumping && !falling) {
			///play jump sound effect
			sfx.get("jump").play();
			///player starts to jump
			dy = jumpStart;
			///player starts to fall
			falling = true;
		}
//                if(doubleJump) {
//			dy = doubleJumpStart;
//			alreadyDoubleJump = true;
//			doubleJump = false;
//			sfx.get("jump").play();
//		}
//		
//		if(!falling) alreadyDoubleJump = false;
		
		if(falling) {
			if(dy > 0 && gliding) {
				dy += fallSpeed * 0.1;  ///10% speed
			}else {
				///fall at regular speed
				dy += fallSpeed;
			}
			if(dy > 0) jumping = false;
			///the longer you hold the jump button, the higher you'll jump
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		///check that attack has stopped/has already played once
		if(currentAction == SCRATCHING) {
			if(animation.hasPlayedOnce()) scratching = false;
		}
		
		if(currentAction == FIREBALL) {
			if(animation.hasPlayedOnce()) firing = false;
		}
		
		///fireball animation
		///continuously shoot fire
		fire += 1;
		if(fire > maxFire) fire = maxFire;
		if(firing && currentAction != FIREBALL) {
			///make sure there's enough energy to fire fireball
			if(fire > fireCost) {
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight);
				///set to position of player 
				fb.setPosition(x, y);
				fireBalls.add(fb);
			}
		}
		
		///update fireballs animation
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}
		
		//check to see if sprite is done flinching
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			///if greater than 1 second (1000), flinching = false
			if(elapsed > 1000) {
				flinching = false;
			}
		}
		
		// set animation
		if(scratching) {
			if(currentAction != SCRATCHING) {
				///play scratching sound effect
				sfx.get("scratch").play();
				currentAction = SCRATCHING;
				///set the frames for scratching
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				width = 60;
			}
		}
		else if(firing) {
			if(currentAction != FIREBALL) {
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				width = 30;
			}
		}
		////two types of falling, falling when gliding, regular falling
		///this is falling when gliding
		else if(dy > 0) {
			if(gliding) {
				if(currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 30;
				}
			}
			else if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 30;
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 30;
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 30;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 30;
			}
		}
		
		animation.update();
		
		// set direction of sprite
		if(currentAction != SCRATCHING && currentAction != FIREBALL) {
			////don't let sprite move, have it face the right direction
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
                
		///check health
		if(health < 0) {
			dead = true;
		}
	}
	
	////this method is correct
	public void draw(Graphics2D g) {
		///set position of player on map
		setMapPosition();
		
		////draw fireballs
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).draw(g);
		}
		///draw player sprite
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				///don't move the player, they are not moving
				return;
			}
		}
		///draw player sprite after defeat
		if(dead = true) {
			
		}
		super.draw(g);
	}
}
