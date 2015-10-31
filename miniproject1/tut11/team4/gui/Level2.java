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
public class Level2 extends JPanel {
	private ColorProgressPanel progress = new ColorProgressPanel(10);
	GridBagConstraints c = new GridBagConstraints();
	private JTextArea messageArea = new JTextArea();
	private JLabel wordArea = new JLabel();
	private JButton click = new JButton("Click to Talk");
	private String word = "";
	private JPanel container = new JPanel();
	private JLabel currentScoreLabel = new JLabel("Total Score: ");
	private JLabel time = new JLabel("Time Passed:  " + 0 + " seconds");
	private int totalScore = 0;
	int cell = 0, trials, secs;

	public Level2(Recognizer recognizer, Microphone microphone) {
		container.setLayout(new BorderLayout());
		wordArea.setHorizontalAlignment(JLabel.CENTER);
		messageArea.setEditable(false);
		newWord("Hello" + cell);
		trials = 3;
		secs = 0;
		click.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cell == 10) {
                	newWord("CONGRATULATIONS! YOU HAVE FINISHED LEVEL 2!");
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
		                if (resultText.equals(word)) {
		                	trials = 3;
		                	changeWordScoreBy(10);
	                		newWord("Hello" + cell);
	                		progress.changeToColor(cell++);
	                		verdict = "Correct Word";
		                }
		                else {
		                	trials --;
		                	if (trials < 0) {
		                		changeWordScoreBy(-2);
		                	}
		                	verdict = "Wrong Word";
		                }
		                messageArea.setText(resultText + ": " + verdict);
		                if (cell == 10) {
                			newWord("CONGRATULATIONS!");
                        	click.setEnabled(false);
                		}
		            } else {
		            	messageArea.setText("I can't hear what you said, please try again.");
		            }
				}
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
		c.weightx = 3;
		c.weighty = 1;
		add(messageArea, c);

		c.gridy = 4;
		c.weighty = 1;
		add(wordArea, c);

		c.gridy = 5;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weightx = 1;
		c.weighty = 0.3;
		add(click, c);

		c.gridy = 6;
		c.weightx = 1;
		c.weighty = 0.01;
		add(container, c);
	}

	public void newWord(String s) {
		word = s;
		wordArea.setText(word);
		currentScoreLabel.setText("Total Score: " + totalScore);
	}

	public void changeWordScoreBy(int x) {
		totalScore += x;
		currentScoreLabel.setText("Total Score: " + totalScore);
	}
}
