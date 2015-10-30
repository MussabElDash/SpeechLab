package edu.cmu.sphinx.demo.miniproject1.tut11.team4.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.cmu.sphinx.demo.miniproject1.tut11.team4.MiniProject1;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.util.props.ConfigurationManager;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	public MainPanel() {
		setVisible(true);
		JButton level1 = new JButton("Level 1");
		JButton level2 = new JButton("Level 2");
		setLayout(new GridLayout(16, 5));

		ConfigurationManager configurationManager = new ConfigurationManager(MiniProject1.class.getResource("miniproject1.config.xml"));
		Recognizer recognizer = (Recognizer) configurationManager.lookup("recognizer");
	    recognizer.allocate();
	    Microphone microphone = (Microphone) configurationManager.lookup("microphone");

		for (int i = 0; i < 80; i++) {
			if (i == 37)
				add(level1);
			else if (i == 42)
				add(level2);
			else
				add(new JLabel());
		}

		level1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Level1 lvl1 = new Level1(recognizer, microphone);
				Constants.mainWindow.setContentPane(lvl1);
				Constants.mainWindow.repaint();
				Constants.mainWindow.revalidate();
			}
		});

		level2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Level2 lvl2 = new Level2(recognizer, microphone);
				Constants.mainWindow.setContentPane(lvl2);
				Constants.mainWindow.repaint();
				Constants.mainWindow.revalidate();
			}
		});

		repaint();
		revalidate();
	}
}
