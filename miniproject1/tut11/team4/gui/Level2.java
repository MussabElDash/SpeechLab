package edu.cmu.sphinx.demo.miniproject1.tut11.team4.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;

@SuppressWarnings("serial")
public class Level2 extends JPanel {
	private ColorProgressPanel progress = new ColorProgressPanel(20);
	GridBagConstraints c = new GridBagConstraints();
	private JTextArea messageArea = new JTextArea();
	private JLabel wordArea = new JLabel();
	private JButton click = new JButton("Click to Talk");
	private String word = "";
	private JPanel container = new JPanel();
	private JLabel currentScoreLabel = new JLabel();
	private int totalScore = 0;
	int cell = 0, trials = 3;

	public Level2(Recognizer recognizer, Microphone microphone) {
		container.setLayout(new BorderLayout());
		wordArea.setHorizontalAlignment(JLabel.CENTER);
		messageArea.setEditable(true);
		if (!microphone.startRecording()) {
            messageArea.setText("Cannot start microphone, please try again.");
            recognizer.deallocate();
        }
		click.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Result result = recognizer.recognize();
	            if (result != null) {
	                String resultText = result.getBestFinalResultNoFiller();
	                messageArea.setText(resultText);
	                if (resultText.equals(word)) {
	                	changeWordScoreBy(10);
	                	newWord("Hello" + cell);
	                	//newWord(generateWord());
    					progress.changeToColor(cell++);
    					trials = 3;
	                }
	                else {
	                	trials --;
	                	if (trials < 0) {
	                		changeWordScoreBy(-1);
	                	}
	                }
	            } else {
	            	messageArea.setText("I can't hear what you said, please try again.");
	            }
			}
		});

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		c.fill = GridBagConstraints.BOTH;

		c.weightx = 0.06;
		c.weighty = 0.1;
		add(new JLabel(), c);

		c.gridwidth = 3;
		c.weightx = 3;
		c.gridx = 1;
		add(progress, c);

		c.weightx = 0.06;
		c.gridx = 4;
		add(new JLabel(), c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.5;
		add(currentScoreLabel, c);

		c.gridy = 2;
		c.weightx = 3;
		c.weighty = 1;
		add(messageArea, c);

		c.gridy = 3;
		c.weighty = 1;
		add(wordArea, c);

		c.gridy = 4;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weightx = 1;
		c.weighty = 0.3;
		add(click, c);

		c.gridy = 5;
		c.weightx = 1;
		c.weighty = 0.01;
		add(container, c);
	}

	public void newWord(String s) {
		word = s;
		wordArea.setText(word);
	}

	public void changeWordScoreBy(int x) {
		totalScore += x;
		currentScoreLabel.setText("Total Score: " + totalScore);
	}
}
