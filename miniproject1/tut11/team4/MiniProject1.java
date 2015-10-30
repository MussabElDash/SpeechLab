package edu.cmu.sphinx.demo.miniproject1.tut11.team4;

import javax.swing.JFrame;

import edu.cmu.sphinx.demo.miniproject1.tut11.team4.gui.Constants;

@SuppressWarnings("serial")
public class MiniProject1 extends JFrame {

	public static void main(String[] args) {
		Constants.mainWindow = new MiniProject1();
	}

	public MiniProject1() {
		setSize(600, 600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(Constants.mainPanel);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
