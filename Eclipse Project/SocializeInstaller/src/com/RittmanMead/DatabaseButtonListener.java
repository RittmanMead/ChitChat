package com.RittmanMead;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class DatabaseButtonListener implements ActionListener{
	private JTextField jdbcString;
	private JTextField login;
	private JTextField pw;
	
	private JTextArea response;
	private JButton nextButton;
	private JButton submitButton;
	private CardLayout cards;
	private JPanel parent;
	private InstallationWindow boss;
	
	private HashMap<String, String> map;
	
	public DatabaseButtonListener(InstallationWindow boss, HashMap<String, String> remember, CardLayout cards, JPanel parent, JTextField jdbcString, JTextField login, JTextField pw, JTextArea response, JButton nextButton, JButton submitButton) {
		this.jdbcString = jdbcString;
		this.login = login;
		this.pw = pw;
		this.response = response;
		this.nextButton = nextButton;
		this.submitButton = submitButton;
		this.cards = cards;
		this.parent = parent;
		this.boss = boss;
		map = remember;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			try {
				if ((jdbcString.getText().length() > 0) && (login.getText().length() > 0) && (pw.getText().length() > 0)) {
					DatabaseBuilder.build(jdbcString.getText(), login.getText(), pw.getText());
					response.setText("Success");
				}
				else {
					response.setText("All fields must contain values");
				}
				
			} catch (Exception e1) {
				response.setText(e1.getMessage() + "\nCheck the log to view the entire error.");
			}
		}
		else if (e.getSource() == nextButton) {
			JPanel jndiPanel = boss.buildJNDIWindow(parent);
			parent.add(jndiPanel);
			cards.next(parent);
		}
	}

}
