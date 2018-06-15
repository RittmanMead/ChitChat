package com.RittmanMead;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DeployButtonListener implements ActionListener{
	private JTextField serverPort;
	private JTextField serverIP;
	private JTextField login;
	private JTextField password;
	private JTextField server;

	private JButton previousButton;
	private JButton nextButton;
	private JButton submitButton;
	private JTextArea response;
	private CardLayout cards;
	private JPanel parent;	
	
	private InstallationWindow boss;

	
	public DeployButtonListener(InstallationWindow boss, JTextField server_name, JTextField serverPort, JTextField login, JTextField password, JTextField address, CardLayout cards, JPanel parent, JTextArea response, JButton previousButton, JButton nextButton, JButton submitButton) {
		this.previousButton = previousButton;
		this.nextButton = nextButton;
		this.response = response;
		this.submitButton = submitButton;
		this.parent = parent;
		this.cards = cards;
		
		this.serverPort = serverPort;
		this.login = login;
		this.password = password;
		this.serverIP = address;
		
		this.server = server_name;
		this.boss = boss;

	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == previousButton) {
			cards.previous(parent);
		}
		else if (e.getSource() == nextButton) {
			JPanel constantsPanel = boss.buildApplicationConstantsWindow(parent);
			parent.add(constantsPanel);
			cards.next(parent);
			
		}
		else if (e.getSource() == submitButton) {
			try { 
				if ((serverPort.getText().length() > 0) && (login.getText().length() > 0) && (password.getText().length() > 0) && (serverIP.getText().length() > 0) && (server.getText().length() > 0)) {
					DeployBuilder.build(serverIP.getText(), serverPort.getText(), login.getText(), password.getText(), server.getText());
					response.setText("Success");
				}
				else {
					response.setText("All fields must contain values");
				}
			}
			catch (Exception e1){
				response.setText(e1.getMessage() + "\nCheck the log to view the entire error.");
			}
		}
		
		
	}

}
