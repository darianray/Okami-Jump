package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
	
	private Background bg;
	//private Label l;
	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Controls",
		"High Scores",
		"Quit"
	};
	
	
	private Font titleFont;
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Resources/Backgrounds/homescreen.gif", 1);
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
		g.drawString("Okami", 87, 70);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.yellow);
			}
			else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 145, 140 + i * 20);
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.HELPSTATE);
		}
		if(currentChoice == 2) {
			gsm.setState(GameStateManager.HIGHSCORESTATE);
		}
		if(currentChoice == 3) {
			System.exit(0);
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










