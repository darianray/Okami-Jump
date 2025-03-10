package Entity;

import GameState.Level1State;

public class PlayerData {
private String name;
private int score;

private Level1State l1;

public PlayerData(String name, int score) {
	this.name = name;
	this.score = score;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public int getScore() {
    return score;
}

public void setScore(int score) {
    this.score = score;
}
}
