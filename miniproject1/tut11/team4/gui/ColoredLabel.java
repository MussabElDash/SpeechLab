package edu.cmu.sphinx.demo.miniproject1.tut11.team4.gui;

import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ColoredLabel extends JLabel {
	private Color defaultColor = getBackground();
	private Color color;

	public ColoredLabel(Color color) {
		this.color = color;
	}

	public void defaultColor() {
		setOpaque(false);
		setBackground(defaultColor);
	}

	public void changeColor() {
		setOpaque(true);
		setBackground(color);
	}
}
