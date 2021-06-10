package Entity;

import java.awt.image.BufferedImage;

///this class handles sprite animation
public class Animation {

	///array to hold frames
	private BufferedImage[] frames;
	///keep track of current frame
	private int currentFrame;
	
	///timer between frames
	private long startTime;
	private long delay;
	
	///boolean to tell whether frame has already been played
	private boolean playedOnce;
	
	public Animation() {
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long d) { delay = d;}
	///manually set frame
	public void setFrame(int i) { currentFrame = i;}
	
	///method to update current frame
	public void update() {
		if(delay == -1 ) return; ///no animation
		
		///time since frame was put up
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			///move onto next frame
			currentFrame++;
			//reset timer
			startTime = System.nanoTime();
		}
		///make sure it doesn't go past max amount of frames/ out of bounds
		if(currentFrame == frames.length) {
			///reset loop
			currentFrame = 0;
			playedOnce = true;
		}
	}
	//getters
	///return current frame number
	public int getFrames() { return currentFrame;}
	///get image that needs to be drawn
	public BufferedImage getImage() { return frames[currentFrame];}
	///retunr whether image has played
	public boolean hasPlayedOnce() {return playedOnce;}
}
