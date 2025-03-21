package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Explosion {   ////doesn't need to extend map object because it is just a graphic 

	///coordinates
	private int x;
	private int y;
	private int xmap;
	private int ymap;
	
	private int width;
	private int height;
	
	private Animation animation;
	private BufferedImage[] sprites;
	
	///boolean for whether explosion should be removed
	private boolean remove;
	
	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;
		
		width = 30;
		height = 30;
		
		try {
			///read in sprite sheet
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
					"/Resources/Sprites/Enemies/explosion.gif"));
			///split into different frames (6 images, 6 frames, array of size 6)
			sprites = new BufferedImage[6];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
						i * width, 0, width, height);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(70);
	}
	
	///update explosion
	public void update() {
		animation.update();
		if(animation.hasPlayedOnce()) {
			remove = true;
		}
	}
	public boolean shouldRemove() {return remove;}
	
	public void setMapPosition(int x, int y) {
		xmap = x;
		ymap = y;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);
	}
}
