package Entity;
////this class displays player information

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {

	private Player player;
	
	private BufferedImage image;
	private Font font;
	
	public HUD(Player p) {
		player = p;
		try {
			///get the image for HUD
			image = ImageIO.read(getClass().getResourceAsStream(
					"/Resources/HUD/hud.gif"));
			///set font
			font = new Font("Arial", Font.PLAIN, 14);
		}catch(Exception e) {
		//	e.printStackTrace();
		}
	}
	
	///draw hud
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 10, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		//get health and fire, draw string
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 25);
		
		///get fire
		g.drawString(player.getFire() / 100 +"/"+player.getMaxFire() / 100, 30, 45);
	}
}
