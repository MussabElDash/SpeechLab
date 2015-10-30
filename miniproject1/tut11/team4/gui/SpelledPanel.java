package edu.cmu.sphinx.demo.miniproject1.tut11.team4.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SpelledPanel extends JPanel {
	JTextField[] chars;

	public SpelledPanel(int size) {
		chars = new JTextField[size];
		setLayout(new GridLayout(1, size));
		for (int i = 0; i < size; i++) {
			chars[i] = new JTextField("");
			chars[i].setEditable(false);
			add(chars[i]);
		}
	}

	public void setCharAt(int i, char c) {
		chars[i].setText(c + "");
	}
}
