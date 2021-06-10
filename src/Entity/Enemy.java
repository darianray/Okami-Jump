package Entity;

import TileMap.TileMap;

public class Enemy extends MapObject{

	///enemy class variables
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage; ///damage done to player
	
	protected boolean flinching;
	protected long flinchTimer;
	
	public Enemy(TileMap tm) {
		super(tm);
	}
	
	///check whether enemy is dead
	public boolean isDead() { return dead;}
	public int getDamage() {return damage;}
	
		///return damage and whether isDead is true
	public void hit(int damage) {
		if(dead || flinching)return;
		health -= damage;
		if(health < 0 ) health = 0;  ///which means enemy is dead
		if(health == 0) dead = true;
		///otherwise, keep flinching 
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update() {
		
	}
}
