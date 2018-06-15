package com.RittmanMead;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ConstantsListener implements ActionListener{

	private JButton previousButton;
	private JButton nextButton;

	private CardLayout cards;
	private JPanel parent;	
	private InstallationWindow boss;
	
	public ConstantsListener(InstallationWindow boss, JPanel parent, CardLayout cards, JButton previousButton, JButton nextButton) {
		this.previousButton = previousButton;
		this.nextButton = nextButton;

		this.parent = parent;
		this.cards = cards;
		this.boss = boss;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nextButton) {
		}
		else if (e.getSource() == previousButton) {
			cards.previous(parent);
		}
		
	}

}
