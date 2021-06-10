package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HelpState extends GameState {
	
	private Background bg;
	private Label l;
	private int currentChoice = 0;
	private String[] options = {
		"Go Back To Menu"
	};
	
	
	private Font titleFont;
	
	private Font font;
	
	public HelpState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
			
			titleFont = new Font(
					"Century Gothic",
					Font.PLAIN,
					38);
			
			font = new Font("Arial", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
		//	e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(Color.yellow);
		g.setFont(titleFont);
		g.drawString("Controls", 87, 40);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 95, 200);
		}
                g.setFont(font);
                g.setColor(Color.BLACK);
                g.drawString("Use the R/L arrow buttons to Move", 30, 80);
		g.drawString("To Jump, press Space", 30, 100);
                g.drawString("To Glide, press E", 30, 120);
                g.drawString("To shoot FireBalls, press F", 30, 140);
                g.drawString("To Scratch, press R", 30, 160);
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.MENUSTATE);
                }
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}
	
}










