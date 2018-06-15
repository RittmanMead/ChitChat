package com.RittmanMead;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JNDIButtonListener implements ActionListener{
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
	private HashMap<String, String> map;
	
	private JTextField jdbc;
	private JTextField dblogin;
	private JTextField dbpw;
	private InstallationWindow boss;
	
	public JNDIButtonListener(InstallationWindow boss, JTextField jdbc, JTextField dblogin, JTextField dbpw, HashMap<String, String> map, JTextField server_name, JTextField serverPort, JTextField login, JTextField password, JTextField address, CardLayout cards, JPanel parent, JTextArea response, JButton previousButton, JButton nextButton, JButton submitButton) {
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
		this.map = map;
		
		this.jdbc = jdbc;
		this.dblogin = dblogin;
		this.dbpw = dbpw;
		this.server = server_name;
		this.boss = boss;
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == previousButton) {
			cards.previous(parent);
		}
		else if (e.getSource() == nextButton) {
			JPanel deployPanel = boss.buildDeployWindow(parent);
			parent.add(deployPanel);
			cards.next(parent);
		}
		else if (e.getSource() == submitButton) {
			try { 
				if ((serverPort.getText().length() > 0) && (login.getText().length() > 0) && (password.getText().length() > 0) && (serverIP.getText().length() > 0) && (jdbc.getText().length() > 0) && (dblogin.getText().length() > 0) && (dbpw.getText().length() > 0)) {
					JNDIBuilder.build(serverIP.getText(), serverPort.getText(), server.getText(), login.getText(), password.getText(), jdbc.getText(), dblogin.getText(), dbpw.getText());
					map.put("serverIP", serverIP.getText());
					map.put("server", server.getText());
					map.put("login", login.getText());
					map.put("password", password.getText());
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
