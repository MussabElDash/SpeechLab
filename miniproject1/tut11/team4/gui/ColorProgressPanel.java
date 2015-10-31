package edu.cmu.sphinx.demo.miniproject1.tut11.team4.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorProgressPanel extends JPanel {
	ColoredLabel[] buttons;

	public ColorProgressPanel(int size) {
		setLayout(new GridLayout(1, size));
		buttons = new ColoredLabel[size];
		for (int i = 0; i < buttons.length; i++) {
			switch (i / 5) {
			case 0:
				buttons[i] = new ColoredLabel(Color.RED);
				break;
			case 1:
				buttons[i] = new ColoredLabel(Color.ORANGE);
				break;
			case 2:
				buttons[i] = new ColoredLabel(Color.YELLOW);
				break;
			case 3:
				buttons[i] = new ColoredLabel(Color.GREEN);
				break;
			default:
				break;
			}
			add(buttons[i]);
		}
	}

	public void returnToDefault(int i) {
		if (i > buttons.length - 1)
			return;
		buttons[i].defaultColor();
		repaint();
		revalidate();
	}

	public void changeToColor(int i) {
		if (i > buttons.length - 1)
			return;
		buttons[i].changeColor();
		repaint();
		revalidate();
	}

}
