package Main;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		///create jframe to hold game
		JFrame window = new JFrame("Okami");
		///set content panel into jframe
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}
