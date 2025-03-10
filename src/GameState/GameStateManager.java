package GameState;

//import java.util.ArrayList;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES = 5;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int GAMEOVERSTATE2 = 2;
	public static final int HIGHSCORESTATE = 3;
        public static final int HELPSTATE = 4;
	
	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES ];
		
		currentState = MENUSTATE;
		loadState(currentState);
	}
	
	///load the state
	private void loadState(int state) {
		if(state == MENUSTATE) {
			gameStates[state] = new MenuState(this);
		}
		if(state == LEVEL1STATE) {
			gameStates[state] = new Level1State(this);
		}
		if(state == GAMEOVERSTATE2) {
			gameStates[state] = new GameOverState(this); // Ensure this is GameOverState2 if needed
		}
		if(state == HIGHSCORESTATE) {
			gameStates[state] = new HighScoreState(this);
		}
		if(state == HELPSTATE) {
			gameStates[state] = new HelpState(this);
		}
	}
	
	///unload the state
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		
		if (gameStates[currentState] == null) {
			loadState(currentState);
		}
	
	}
	
	
	
	public void update() {
		try {
		gameStates[currentState].update();
		}catch(Exception e) {
			//e.printStackTrace();
		}
		
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
		gameStates[currentState].draw(g);
		}catch(Exception e) {
			//e.printStackTrace();
		}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	public void keyReleased(int keyCode) {
		if (currentState >= 0 && currentState < gameStates.length && gameStates[currentState] != null) {
			gameStates[currentState].keyReleased(keyCode);
		} else {
		}
	}
	
}
