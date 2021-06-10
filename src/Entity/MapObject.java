package Entity;

import TileMap.*;
import java.awt.Rectangle;

import Main.GamePanel;
import TileMap.Tile;

public abstract class MapObject {

	////since it is an abstract class, everything needs to be protected
	///so that subclasses can see variables
	///tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	///position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//dimensions
	protected int width;
	protected int height;
	
	///collision box
	protected int cwidth;
	protected int cheight;
	
	///collision 
	protected int currRow;
	protected int currCol;
	////destinations
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	///animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	///if sprite is not facing right, sprite needs to be flipped
	protected boolean facingRight;
	
	///movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	//movement characteristics
	///speed for acceleration
	protected double moveSpeed;
	///maximum speed that can be reached
	protected double maxSpeed;
	///decelleration speed (speed that your value decrements by)
	protected double stopSpeed;
	///spoeed at which you fall
	protected double fallSpeed;
	///terminal velocity
	protected double maxFallSpeed;
	///how high sprite can jump
	protected double jumpStart;
	///variable that determines how high you jump
	///based on how long key is pressed down
	protected double stopJumpSpeed;
	
	///constructor to get tile map and tile size
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	
	///map objects need to be able to check whether they
	// can collide with other objects
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	public Rectangle getRectangle() {
		return new Rectangle((int)x - cwidth,
				(int)y - cheight, cwidth, cheight);
	}
	
	public void calculateCorners(double x, double y) {
		///keeps character within bounds
        int leftTile = (int)(x - cwidth / 2) / tileSize;
        int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cheight / 2) / tileSize;
        int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
        if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
                topLeft = topRight = bottomLeft = bottomRight = false;
                return;
        }
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);
        ///if character tries to interact with blocked tile, it can't
        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
}
	///check whether sprite has run into a blocked tile or normal tile
	public void checkTileMapCollision() {
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		///destination positions
		xdest = x + dx;
		ydest = y + dy;
		///temp to keep track of original x and y
		xtemp = x;
		ytemp = y;
		
		///four cornered method for determining tile collision
		calculateCorners(x, ydest);
		///check top two corners
		if(dy < 0) {
			if(topLeft || topRight) {
				dy = 0;
				///set object below tile it just hit
				ytemp = currRow * tileSize + cheight / 2;
				
			}///else sprite is allowed to keep going
			else {
				ytemp += dy;
			}
		}///if going down
		if(dy > 0) {
			///check two corners
			if(bottomLeft || bottomRight) {
				//stop y direction, if sprite was falling, change it to false so sprite lands
				dy = 0; 
				falling = false;
				///set position to 1 tile above tile it hit
				ytemp = (currRow + 1) * tileSize - cheight / 2;
				
			}
			///if tile was not hit, allow sprite to keep falling
			else {
				ytemp += dy;
			}
		}
		///calculate corners for x direction
		calculateCorners(xdest, y);
		if(dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else { ////keep going in that direction 
				xtemp += dx;
			}
		}
		if(dx > 0) {
			///if corners are hit, sprite has hit a wall
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}///check whether sprite has fell off cliff
		if(!falling) {
			calculateCorners(x, ydest + 1); ///check one pixel below feet
			if(!bottomLeft && !bottomRight) {
				///no longer standing on solid ground
				falling = true;
			}
		}
	}
	
	///getters 
	public int getx() { return (int)x ;}
	public int gety() { return (int)y;}
	public int getWidth() { return width;}
	public int getHeight() { return height;}
	public int getCWidth() { return cwidth;}
	public int getCHeight() { return cheight;}
	
	///setters 
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	///every map has two positions, one is global positions (x and y)
	///other is local position ( x or y plus the tilemap position)
	///mapPosition tells you where to draw the sprite
	public void setMapPosition() {
		xmap = tileMap.getX();
		ymap = tileMap.getY();
	}
	
	public void setLeft(boolean b) { left = b;}
	public void setRight(boolean b) { right = b;}
	public void setUp(boolean b) { up = b;}
	public void setDown(boolean b) {down = b;}
	public void setJumping(boolean b) {jumping = b;}
	
	///boolean for whether object is on the screen
	public boolean notOnScreen() {
		return x + xmap + width < 0 || 
				x + xmap - width > GamePanel.WIDTH ||
				y + ymap + height < 0 ||
				y + ymap - height > GamePanel.HEIGHT;
	}
	public void draw(java.awt.Graphics2D g) {
		if(facingRight) {
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2),
					(int)(y + ymap - height / 2), null);
		}
		else {
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width),  ////x coordinates
					(int)(y + ymap - height / 2), -width, height, null);
		}
	}
}
