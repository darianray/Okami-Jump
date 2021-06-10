package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverState extends GameState {
	
	private Background bg;
	private Label l;
	private int currentChoice = 0;
	private String[] options = {
		"Go Back To Menu"
	};
	
	
	private Font titleFont;
	
	private Font font;
	
	public GameOverState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Backgrounds/oohh.gif", 1);
			bg.setVector(-0.1, 0);
			
			
			titleFont = new Font(
					"Century Gothic",
					Font.PLAIN,
					46);
			
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
		g.drawString("Game Over", 25, 70);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.WHITE);
			}
                        g.drawString("Would You like to Try Again??", 80, 120);
			g.drawString(options[i], 115, 200);
                        
		}
		
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










