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
import javax.swing.Timer;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;

@SuppressWarnings("serial")
public class Level1 extends JPanel {
	private ColorProgressPanel progress = new ColorProgressPanel(20);
	GridBagConstraints c = new GridBagConstraints();
	private JTextArea messageArea = new JTextArea();
	private JLabel wordArea = new JLabel();
	private JButton click = new JButton("Click to Talk");
	private SpelledPanel spelling;
	private String word = "";
	private JPanel container = new JPanel();
	private JLabel currentWordScoreLabel = new JLabel("Current Word Score: ");
	private JLabel currentScoreLabel = new JLabel("Total Score: ");
	private JLabel time = new JLabel("Time Passed:  " + 0 + " seconds");
	private JButton level2 = new JButton("Level 2");
	private int totalScore = 0, wordScore = 0;
	int cell = 0, currentWordIdx, trials, secs;

	public Level1(Recognizer recognizer, Microphone microphone) {
		container.setLayout(new BorderLayout());
		wordArea.setHorizontalAlignment(JLabel.CENTER);
		messageArea.setEditable(false);
		newWord("Hello" + cell);
		currentWordIdx = 0;
		trials = 3;
		secs = 0;
		click.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cell == 20) {
                	newWord("CONGRATULATIONS! YOU HAVE FINISHED LEVEL 1!");
                	level2.setEnabled(true);
                	click.setEnabled(false);
                }
				else {
					microphone.clear();
					microphone.startRecording();
					wordArea.setText("");
					Result result = recognizer.recognize();
					microphone.stopRecording();
		            if (result != null) {
		                String resultText = result.getBestFinalResultNoFiller();
		                String verdict = "";
		                if (resultText.length() > 0 &&
		                		resultText.charAt(0) == word.charAt(currentWordIdx)) {
		                	trials = 3;
		                	changeCharAt(currentWordIdx, resultText.charAt(0));
		                	currentWordIdx ++;
		                	verdict = "Correct Letter";
		                }
		                else {
		                	trials --;
		                	if (trials < 0) {
		                		changeWordScoreBy(-2);
		                	}
		                	verdict = "Wrong Letter";
		                }
		                messageArea.setText(resultText + ": " + verdict);
		                if (currentWordIdx == word.length()) {
		                	changeWordScoreBy(10);
	                		newWord("Hello" + cell);
	                		progress.changeToColor(cell++);
		                }
		                if (cell == 20) {
                			newWord("CONGRATULATIONS!");
                        	level2.setEnabled(true);
                        	click.setEnabled(false);
                		}
		            } else {
		            	messageArea.setText("I can't hear what you said, please try again.");
		            }
				}
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
		ActionListener actionListener = new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	            time.setText("Time Passed: " + (++ secs) + " seconds");
	        }
	    };
	    Timer timer = new Timer(1000, actionListener);
	    timer.start();

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
		add(time, c);

		c.gridy = 2;
		add(currentScoreLabel, c);

		c.gridy = 3;
		add(currentWordScoreLabel, c);

		c.gridy = 4;
		c.weightx = 3;
		c.weighty = 1;
		add(messageArea, c);

		c.gridy = 5;
		c.weighty = 1;
		add(wordArea, c);

		level2.setEnabled(false);
		c.gridy = 6;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weightx = 1;
		c.weighty = 0.3;
		add(level2, c);

		c.gridy = 7;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weightx = 1;
		c.weighty = 0.3;
		add(click, c);

		c.gridy = 8;
		c.weightx = 1;
		c.weighty = 0.01;
		add(container, c);
	}

	public void newWord(String s) {
		currentScoreLabel.setText("Total Score: " + totalScore);
		currentWordScoreLabel.setText("Current Word Score: " + wordScore);
		wordScore = 0;
		if (spelling != null)
			container.remove(spelling);
		word = s;
		wordArea.setText(word);
		container.add((spelling = new SpelledPanel(word.length())));
	}

	public void changeCharAt(int i, char c) {
		spelling.setCharAt(i, c);
	}

	public void changeWordScoreBy(int x) {
		totalScore += x;
		wordScore += x;
		currentScoreLabel.setText("Total Score: " + totalScore);
		currentWordScoreLabel.setText("Current Word Score: " + wordScore);
	}
}
