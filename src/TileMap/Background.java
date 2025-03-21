package TileMap;

import java.awt.*;
import java.awt.image.*;
import java.io.InputStream;

import javax.imageio.ImageIO;
import Main.GamePanel;

public class Background {

	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	// public Background(String s, double ms) {
	// 	try {
	// 		image = ImageIO.read(getClass().getResourceAsStream(s));
	// 		moveScale = ms;
	// 	}catch(Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }

	public Background(String s, double ms) {
    try {
        // Debug: Print the path being used

        // Check if the resource exists
        InputStream inputStream = getClass().getResourceAsStream(s);
        if (inputStream == null) {
        } else {
            image = ImageIO.read(inputStream);
            moveScale = ms;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
	
	public void setPosition(double x, double y) {
		///if it goes past the screen, reset
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null); 
		///to scroll when there's no image to the left
		if(x < 0) {
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		///when there's no image to the right, draw image to the right
		if(x > 0) {
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
		}
	}
}
