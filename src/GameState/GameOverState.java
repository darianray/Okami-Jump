package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
//import java.util.HashMap;

//import Audio.AudioPlayer;

public class GameOverState extends GameState {

	private Background bg;
	// private Label l;
	private boolean gameOverShown = false;

	private int currentChoice = 0;
	private String[] options = {
			"Try Again",
			"Go Back To Menu"
	};

	private Font titleFont;
	//private HashMap<String, AudioPlayer> sfx;

	private Font font;

	public GameOverState(GameStateManager gsm) {

		this.gsm = gsm;
		// sfx = new HashMap<String, AudioPlayer>();
		// sfx.put("OkamiLevel1", new AudioPlayer("/Resources/Music/OkamiLevel1.wav"));
		
		// sfx.get("backgroundMusic").close();

		try {
			bg = new Background("/Resources/Backgrounds/homescreen.gif", 1);
			bg.setVector(-0.1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		titleFont = new Font("Century Gothic", Font.PLAIN, 46);
		font = new Font("Arial", Font.PLAIN, 12);
	}

	public void init() {
	}

	public void update() {
		if (!gameOverShown) {
			bg.update(); // Update background or anything needed.
			gameOverShown = true; // Mark the screen as shown, so no further updates occur.
		}
	}

	public void draw(Graphics2D g) {
		// draw background
		bg.draw(g);

		// draw title
		g.setColor(Color.yellow);
		g.setFont(titleFont);
		g.drawString("Game Over", 25, 70);

		// draw prompt
		g.setFont(font);
		g.setColor(Color.WHITE);

		// draw menu options
		int startY = 150; // Starting Y position for the options
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(Color.YELLOW); // Highlight the selected option
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 115, startY + i * 40); // Increase vertical spacing to 40
		}

	}

	private void select() {
		if (currentChoice == 0) {
			// Restart the level (or game)
			gsm.setState(GameStateManager.LEVEL1STATE);
		} else if (currentChoice == 1) {
			// Go back to the main menu

			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			select(); // Make a selection
		}
		if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}

	public void keyReleased(int k) {
	}

}
