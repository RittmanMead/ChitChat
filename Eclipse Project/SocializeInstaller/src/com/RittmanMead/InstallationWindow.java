package com.RittmanMead;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class InstallationWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3410682080102720492L;
	private static Dimension largeFieldSize = new Dimension(300, 30);
	private static Dimension areaSize = new Dimension(860, 250);
	private static Dimension smallFieldSize = new Dimension(50, 30);
	private static Dimension averageFieldSize = new Dimension(150, 30);
	private static HashMap<String, String> map = new HashMap<String, String>();
	private static Dimension windowSize = new Dimension(800, 500);
	private static Dimension stepsSize = new Dimension(200, 500);
	private static Dimension fieldsSize = new Dimension(850, 240);
	private static Dimension buttonsSize = new Dimension(850, 40);
	private static String[] steps = {"Welcome", "Database Setup", "JNDI Connection Setup", "WAR deployment", "Application Constants Setup"};


	public InstallationWindow() {
		JFrame mainFrame = new JFrame();
		
		mainFrame.setSize(windowSize);
		mainFrame.setTitle("Socialize Installer");
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);

		JPanel parentPanel = new JPanel();
		BoxLayout layout = new BoxLayout(parentPanel, BoxLayout.X_AXIS);
		parentPanel.setLayout(layout);
		
		parentPanel.add(stepsPanel(0));
		
		JPanel rightPanel = new JPanel();
		BoxLayout layout2 = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		rightPanel.setLayout(layout2);
		rightPanel.add(fieldsPanel(0));
		rightPanel.add(buttonsPanel());
		rightPanel.add(outputPanel());
		
		parentPanel.add(rightPanel);
		mainFrame.add(parentPanel);
		mainFrame.setVisible(true);
		
	}
	
	public JPanel buttonsPanel() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.X_AXIS);
		panel.setLayout(layout);
		
		panel.add(new JButton("Submit"));
		
		JPanel spacerPanel = new JPanel();
		spacerPanel.setMinimumSize(new Dimension(700, 40));
		spacerPanel.setMaximumSize(new Dimension(700, 40));
		panel.add(spacerPanel);
		
		panel.add(new JButton("Next"));
		panel.add(new JButton("Previous"));
		
		panel.setMinimumSize(buttonsSize);
		panel.setMaximumSize(buttonsSize);
		
		return panel;
	}
	
	public JPanel fieldsPanel(int indexCurrentStep) {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		
		panel.setMinimumSize(fieldsSize);
		panel.setMaximumSize(fieldsSize);
		
		return panel;
	}
	
	public JPanel outputPanel() {
		JPanel responsePanel = new JPanel();
		BoxLayout responseLayout = new BoxLayout(responsePanel, BoxLayout.X_AXIS);
		responsePanel.setLayout(responseLayout);
		JTextArea responseArea = new JTextArea();
		responseArea.setEditable(false);
		JScrollPane responseScroll = new JScrollPane(responseArea);
		responseScroll.setMinimumSize(areaSize);
		responseScroll.setMaximumSize(areaSize);
		responseScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		responseScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		responsePanel.add(responseScroll);
		
		return responsePanel;
	}
	
	public JPanel stepsPanel(int indexCurrentStep) {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.setBackground(Color.WHITE);
		
		for (int i = 0; i < steps.length; i++) {
			JPanel subPanel = new JPanel();
			JLabel subLabel = new JLabel(steps[i]);
			subPanel.setMaximumSize(new Dimension(200, 20));
			subPanel.add(subLabel);
			subPanel.setBackground(Color.WHITE);
			if (indexCurrentStep != i){
				subLabel.setForeground(Color.GRAY);
			}
			panel.add(subPanel);
		}
		
		panel.setMaximumSize(stepsSize);
		panel.setMinimumSize(stepsSize);
		
		return panel;
	}
	
	protected JPanel buildJNDIWindow(JPanel cards) {
		JPanel mainPanel = new JPanel();
		BoxLayout mainLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(mainLayout);
		mainPanel.add(new JLabel("Build JNDI"));

		
		JPanel serverInfoPanel1 = new JPanel();
		BoxLayout layout1 = new BoxLayout(serverInfoPanel1, BoxLayout.X_AXIS);
		serverInfoPanel1.setLayout(layout1);
		JLabel serverNameLabel = new JLabel("Server Name: ");
		JTextField serverNameField = new JTextField();
		serverNameField.setMaximumSize(averageFieldSize);
		serverNameField.setMinimumSize(largeFieldSize);
		serverNameField.setPreferredSize(largeFieldSize);
		serverInfoPanel1.add(serverNameLabel);
		serverInfoPanel1.add(serverNameField);
		mainPanel.add(serverInfoPanel1);

		
		JPanel serverInfoPanel2 = new JPanel();
		BoxLayout layout2 = new BoxLayout(serverInfoPanel2, BoxLayout.X_AXIS);
		serverInfoPanel2.setLayout(layout2);
		JLabel serverAddressLabel = new JLabel("Server Address: ");
		JTextField serverAddressField = new JTextField();
		serverAddressField.setMaximumSize(averageFieldSize);
		serverAddressField.setMinimumSize(largeFieldSize);
		serverAddressField.setPreferredSize(largeFieldSize);
		serverInfoPanel2.add(serverAddressLabel);
		serverInfoPanel2.add(serverAddressField);
		mainPanel.add(serverInfoPanel2);
		
		
		JPanel serverInfoPanel3 = new JPanel();
		BoxLayout layout3 = new BoxLayout(serverInfoPanel3, BoxLayout.X_AXIS);
		serverInfoPanel3.setLayout(layout3);
		JLabel serverPortLabel = new JLabel("Server Port: ");
		JTextField serverPortField = new JTextField();
		serverPortField.setMaximumSize(averageFieldSize);
		serverPortField.setMinimumSize(smallFieldSize);
		serverPortField.setPreferredSize(smallFieldSize);
		serverInfoPanel3.add(serverPortLabel);
		serverInfoPanel3.add(serverPortField);
		mainPanel.add(serverInfoPanel3);

		
		JPanel loginPanel1 = new JPanel();
		BoxLayout layout4 = new BoxLayout(loginPanel1, BoxLayout.X_AXIS);
		loginPanel1.setLayout(layout4);	
		JLabel serverLoginLabel = new JLabel("Login: ");
		JTextField serverLoginField = new JTextField();
		serverLoginField.setMaximumSize(averageFieldSize);
		serverLoginField.setMinimumSize(averageFieldSize);
		serverLoginField.setPreferredSize(averageFieldSize);
		loginPanel1.add(serverLoginLabel);
		loginPanel1.add(serverLoginField);
		mainPanel.add(loginPanel1);
		
		JPanel loginPanel2 = new JPanel();
		BoxLayout layout5 = new BoxLayout(loginPanel2, BoxLayout.X_AXIS);
		loginPanel2.setLayout(layout5);	
		JLabel serverPasswordLabel = new JLabel("Password: ");
		JPasswordField serverPasswordField = new JPasswordField();
		serverPasswordField.setMaximumSize(averageFieldSize);
		serverPasswordField.setMinimumSize(averageFieldSize);
		serverPasswordField.setPreferredSize(averageFieldSize);
		loginPanel2.add(serverPasswordLabel);
		loginPanel2.add(serverPasswordField);
		mainPanel.add(loginPanel2);
		
		JPanel dbPanel1 = new JPanel();
		BoxLayout jdbcLayout = new BoxLayout(dbPanel1, BoxLayout.X_AXIS);
		dbPanel1.setLayout(jdbcLayout);
		JLabel jdbcUrlLabel = new JLabel("JDBC Url: ");
		JTextField jdbcField = new JTextField(map.get("jdbcString"));
		jdbcField.setMaximumSize(largeFieldSize);
		jdbcField.setMinimumSize(largeFieldSize);
		jdbcField.setPreferredSize(largeFieldSize);
		dbPanel1.add(jdbcUrlLabel);
		dbPanel1.add(jdbcField);
		mainPanel.add(dbPanel1);
		
		JPanel dbPanel2 = new JPanel();
		BoxLayout jdbcLayout2 = new BoxLayout(dbPanel2, BoxLayout.X_AXIS);
		dbPanel2.setLayout(jdbcLayout2);
		JLabel dbLoginLabel = new JLabel("Database Login: ");
		JTextField dbLoginField = new JTextField(map.get("dblogin"));
		dbLoginField.setMaximumSize(averageFieldSize);
		dbLoginField.setMinimumSize(averageFieldSize);
		dbLoginField.setPreferredSize(averageFieldSize);
		dbPanel2.add(dbLoginLabel);
		dbPanel2.add(dbLoginField);
		mainPanel.add(dbPanel2);
		
		JPanel dbPanel3 = new JPanel();
		BoxLayout jdbcLayout3 = new BoxLayout(dbPanel3, BoxLayout.X_AXIS);
		dbPanel3.setLayout(jdbcLayout3);
		JLabel dbpwLabel = new JLabel("Database Password: ");
		JPasswordField dbpwField = new JPasswordField(map.get("dbpw"));
		dbpwField.setMaximumSize(averageFieldSize);
		dbpwField.setMinimumSize(averageFieldSize);
		dbpwField.setPreferredSize(averageFieldSize);
		dbPanel3.add(dbpwLabel);
		dbPanel3.add(dbpwField);
		mainPanel.add(dbPanel3);
		
		JButton submitButton = new JButton("Submit");
		mainPanel.add(submitButton);
		
		JPanel responsePanel = new JPanel();
		BoxLayout responseLayout = new BoxLayout(responsePanel, BoxLayout.X_AXIS);
		responsePanel.setLayout(responseLayout);
		JTextArea responseArea = new JTextArea();
		responseArea.setEditable(false);
		JScrollPane responseScroll = new JScrollPane(responseArea);
		responseScroll.setPreferredSize(areaSize);
		responseScroll.setMaximumSize(areaSize);
		responseScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		responseScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		responsePanel.add(responseScroll);
		mainPanel.add(responsePanel);
		
		JPanel buttonsPanel = new JPanel();
		BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.X_AXIS);
		buttonsPanel.setLayout(buttonsLayout);
		JButton previousButton = new JButton("Previous");
		JButton nextButton = new JButton("Next");
		buttonsPanel.add(previousButton);
		buttonsPanel.add(nextButton);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 500, 0, 0));
		mainPanel.add(buttonsPanel);
		
		JNDIButtonListener jndiListener = new JNDIButtonListener(this, jdbcField, dbLoginField, dbpwField, map, serverNameField, serverPortField, serverLoginField, serverPasswordField, serverAddressField, (CardLayout)cards.getLayout(), cards, responseArea, previousButton, nextButton, submitButton);
		submitButton.addActionListener(jndiListener);
		nextButton.addActionListener(jndiListener);
		previousButton.addActionListener(jndiListener);
		
		return mainPanel;
	}
	
	protected JPanel buildApplicationConstantsWindow(JPanel cards){
		JPanel mainPanel = new JPanel();
		BoxLayout mainLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(mainLayout);
		mainPanel.add(new JLabel("Set Application Constants by following the link below:\n(The deployment must be activated)"));
		
		StringBuilder linkText = new StringBuilder("https://");
		
		String address = map.get("server");
		if (address != null) {
			linkText.append(address + ":9704/Socialize/applicationConstant/list");
		}
		else {
			linkText = new StringBuilder("Unable to create URL");
		}
		
		JButton link = new JButton(linkText.toString());
		link.addActionListener(new OpenUrlAction(linkText.toString()));
		mainPanel.add(link);
		
		JPanel buttonsPanel = new JPanel();
		BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.X_AXIS);
		buttonsPanel.setLayout(buttonsLayout);
		JButton previousButton = new JButton("Previous");
		JButton nextButton = new JButton("Next");
		buttonsPanel.add(previousButton);
		buttonsPanel.add(nextButton);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 250, 0, 0));
		mainPanel.add(buttonsPanel);
		
		ConstantsListener constantListener = new ConstantsListener(this, cards, (CardLayout)cards.getLayout(), previousButton, nextButton);
		nextButton.addActionListener(constantListener);
		previousButton.addActionListener(constantListener);
		nextButton.setEnabled(false);
		
		return mainPanel;
	}
	
	protected JPanel buildDeployWindow(JPanel cards) {
		JPanel mainPanel = new JPanel();
		BoxLayout mainLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(mainLayout);
		mainPanel.add(new JLabel("Build Deployment"));

		
		JPanel serverInfoPanel1 = new JPanel();
		BoxLayout layout1 = new BoxLayout(serverInfoPanel1, BoxLayout.X_AXIS);
		serverInfoPanel1.setLayout(layout1);
		JLabel serverNameLabel = new JLabel("Server Name: ");
		JTextField serverNameField = new JTextField(map.get("server"));
		serverNameField.setMaximumSize(averageFieldSize);
		serverNameField.setMinimumSize(largeFieldSize);
		serverNameField.setPreferredSize(largeFieldSize);
		serverInfoPanel1.add(serverNameLabel);
		serverInfoPanel1.add(serverNameField);
		mainPanel.add(serverInfoPanel1);

		
		JPanel serverInfoPanel2 = new JPanel();
		BoxLayout layout2 = new BoxLayout(serverInfoPanel2, BoxLayout.X_AXIS);
		serverInfoPanel2.setLayout(layout2);
		JLabel serverAddressLabel = new JLabel("Server Address: ");
		JTextField serverAddressField = new JTextField(map.get("serverIP"));
		serverAddressField.setMaximumSize(averageFieldSize);
		serverAddressField.setMinimumSize(largeFieldSize);
		serverAddressField.setPreferredSize(largeFieldSize);
		serverInfoPanel2.add(serverAddressLabel);
		serverInfoPanel2.add(serverAddressField);
		mainPanel.add(serverInfoPanel2);
		
		
		JPanel serverInfoPanel3 = new JPanel();
		BoxLayout layout3 = new BoxLayout(serverInfoPanel3, BoxLayout.X_AXIS);
		serverInfoPanel3.setLayout(layout3);
		JLabel serverPortLabel = new JLabel("Server Port: ");
		JTextField serverPortField = new JTextField();
		serverPortField.setMaximumSize(averageFieldSize);
		serverPortField.setMinimumSize(smallFieldSize);
		serverPortField.setPreferredSize(smallFieldSize);
		serverInfoPanel3.add(serverPortLabel);
		serverInfoPanel3.add(serverPortField);
		mainPanel.add(serverInfoPanel3);

		
		JPanel loginPanel1 = new JPanel();
		BoxLayout layout4 = new BoxLayout(loginPanel1, BoxLayout.X_AXIS);
		loginPanel1.setLayout(layout4);	
		JLabel serverLoginLabel = new JLabel("Login: ");
		JTextField serverLoginField = new JTextField(map.get("login"));
		serverLoginField.setMaximumSize(averageFieldSize);
		serverLoginField.setMinimumSize(averageFieldSize);
		serverLoginField.setPreferredSize(averageFieldSize);
		loginPanel1.add(serverLoginLabel);
		loginPanel1.add(serverLoginField);
		mainPanel.add(loginPanel1);
		
		JPanel loginPanel2 = new JPanel();
		BoxLayout layout5 = new BoxLayout(loginPanel2, BoxLayout.X_AXIS);
		loginPanel2.setLayout(layout5);	
		JLabel serverPasswordLabel = new JLabel("Password: ");
		JPasswordField serverPasswordField = new JPasswordField(map.get("password"));
		serverPasswordField.setMaximumSize(averageFieldSize);
		serverPasswordField.setMinimumSize(averageFieldSize);
		serverPasswordField.setPreferredSize(averageFieldSize);
		loginPanel2.add(serverPasswordLabel);
		loginPanel2.add(serverPasswordField);
		mainPanel.add(loginPanel2);
		
		JButton submitButton = new JButton("Submit");
		mainPanel.add(submitButton);
				
		JPanel responsePanel = new JPanel();
		BoxLayout responseLayout = new BoxLayout(responsePanel, BoxLayout.X_AXIS);
		responsePanel.setLayout(responseLayout);
		JTextArea responseArea = new JTextArea();
		responseArea.setEditable(false);
		JScrollPane responseScroll = new JScrollPane(responseArea);
		responseScroll.setPreferredSize(areaSize);
		responseScroll.setMaximumSize(areaSize);
		responseScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		responseScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		responsePanel.add(responseScroll);
		mainPanel.add(responsePanel);
		
		JPanel buttonsPanel = new JPanel();
		BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.X_AXIS);
		buttonsPanel.setLayout(buttonsLayout);
		JButton previousButton = new JButton("Previous");
		JButton nextButton = new JButton("Next");
		buttonsPanel.add(previousButton);
		buttonsPanel.add(nextButton);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 500, 0, 0));
		mainPanel.add(buttonsPanel);
		
		DeployButtonListener deployListener = new DeployButtonListener(this, serverNameField, serverPortField, serverLoginField, serverPasswordField, serverAddressField, (CardLayout)cards.getLayout(), cards, responseArea, previousButton, nextButton, submitButton);
		submitButton.addActionListener(deployListener);
		nextButton.addActionListener(deployListener);
		previousButton.addActionListener(deployListener);
		
		return mainPanel;
	}
	
	
	private JPanel buildDatabaseWindow(JPanel cards){
		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);
		mainPanel.add(new JLabel("Build Database"));
		
		
		JPanel jdbcPanel = new JPanel();
		BoxLayout jdbcLayout = new BoxLayout(jdbcPanel, BoxLayout.X_AXIS);
		jdbcPanel.setLayout(jdbcLayout);
		JLabel jdbcLabel = new JLabel("JDBC String:");
		JTextField jdbcField = new JTextField();
		jdbcField.setPreferredSize(largeFieldSize);
		jdbcField.setMaximumSize(largeFieldSize);
		jdbcPanel.add(jdbcLabel);
		jdbcPanel.add(jdbcField);
		mainPanel.add(jdbcPanel);
		
		JPanel loginPanel = new JPanel();
		BoxLayout loginLayout = new BoxLayout(loginPanel, BoxLayout.X_AXIS);
		loginPanel.setLayout(loginLayout);
		JLabel loginLabel = new JLabel("Login:");
		JTextField loginField = new JTextField();
		loginField.setPreferredSize(averageFieldSize);
		loginField.setMaximumSize(averageFieldSize);
		loginPanel.add(loginLabel);
		loginPanel.add(loginField);
		mainPanel.add(loginPanel);
		
		JPanel passwordPanel = new JPanel();
		BoxLayout passwordLayout = new BoxLayout(passwordPanel, BoxLayout.X_AXIS);
		passwordPanel.setLayout(passwordLayout);
		JLabel passwordLabel = new JLabel("Password:");
		JPasswordField passwordField = new JPasswordField();
		passwordField.setPreferredSize(averageFieldSize);
		passwordField.setMaximumSize(averageFieldSize);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		mainPanel.add(passwordPanel);
		
		JPanel submitPanel = new JPanel();
		BoxLayout submitLayout = new BoxLayout(submitPanel, BoxLayout.Y_AXIS);
		submitPanel.setLayout(submitLayout);
		JButton submitButton = new JButton("Submit");
		submitButton.setName("Submit");
		submitPanel.add(submitButton);
		
		mainPanel.add(submitPanel);
		
		JPanel responsePanel = new JPanel();
		BoxLayout responseLayout = new BoxLayout(responsePanel, BoxLayout.X_AXIS);
		responsePanel.setLayout(responseLayout);
		JTextArea responseArea = new JTextArea();
		responseArea.setEditable(false);
		JScrollPane responseScroll = new JScrollPane(responseArea);
		responseScroll.setPreferredSize(areaSize);
		responseScroll.setMaximumSize(areaSize);
		responseScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		responseScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		responsePanel.add(responseScroll);
		mainPanel.add(responsePanel);
		
		JPanel buttonsPanel = new JPanel();
		BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.X_AXIS);
		buttonsPanel.setLayout(buttonsLayout);
		JButton previousButton = new JButton("Previous");
		JButton nextButton = new JButton("Next");
		buttonsPanel.add(previousButton);
		buttonsPanel.add(nextButton);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 500, 0, 0));
		mainPanel.add(buttonsPanel);
		
		DatabaseButtonListener databaseListener = new DatabaseButtonListener(this, map, (CardLayout)cards.getLayout(), cards, jdbcField, loginField, passwordField, responseArea, nextButton, submitButton);
		submitButton.addActionListener(databaseListener);
		previousButton.setEnabled(false);
		nextButton.addActionListener(databaseListener);
		
		return mainPanel;
	}

}
