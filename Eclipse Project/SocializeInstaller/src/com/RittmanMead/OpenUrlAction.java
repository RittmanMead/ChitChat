package com.RittmanMead;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenUrlAction implements ActionListener{
	private URI text;
	
	public OpenUrlAction(String text) {
		try {
			this.text = new URI(text);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		open(text);
		
	}
	
	public void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
		      try {
		        Desktop.getDesktop().browse(uri);
		      } catch (Exception e) { 
					System.out.println(e.getMessage());
		    	  }
		} else {
		   
		}
	}

}
