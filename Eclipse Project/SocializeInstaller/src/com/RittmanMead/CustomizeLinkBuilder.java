package com.RittmanMead;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomizeLinkBuilder extends InstallParent{
	
	private static String cssPlaceholderPath = "./configs/socialize_css_placeholder.txt";
	private static String cssPath = "./configs/socialize_css.txt";
	private static String placeholderText="rm_placeholder";
	private static String placeholderText2 = "rm_activeplaceholder";
	
	private static final Logger l4j = LogManager.getLogger(CustomizeLinkBuilder.class.getName());
	
	public static void build(String path, String color, String color2) throws Exception{
		try {
			printlog("BEGIN: Custom link Build...", l4j);
			replaceColor(color, color2);
			String currentContents = getCurrentContents(path);
			String newContents = addContent(currentContents);
			overwrite(path, newContents);
			
			printlog("Done: Custom link Build...", l4j);
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Custom link Build...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
	}
	
	private static void overwrite(String path, String newContents) throws Exception{
		StringBuilder content = new StringBuilder();
		printlog("BEGIN: Overwriting contents...", l4j);

		try {
			writeToFile(path, newContents, true);
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e2) {
			printlog("ERROR: Overwriting contents...", l4j, e2);
			throw new RMException("An unexpected error occurred: " + e2.getMessage());
		}
		printlog("DONE: Overwriting contents...", l4j);

	}
	
	private static String addContent(String oldContent) throws Exception{
		StringBuilder newContent = new StringBuilder();
		printlog("BEGIN: Appending content...", l4j);

		try {
			String additionalContent = readFile(cssPath, false, false);
			newContent.append(oldContent);
			newContent.append(additionalContent);
			
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Appending content...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
		
		printlog("DONE: Appending content...", l4j);
		
		return newContent.toString();
	}
	
	private static String getCurrentContents(String path) throws Exception {
		StringBuilder content = new StringBuilder();
		printlog("BEGIN: Get Current Contents...", l4j);

		try {
			content.append(readFile(path, true, false));
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e2) {
			printlog("ERROR: Get Current Contents...", l4j, e2);
			throw new RMException("An unexpected error occurred: " + e2.getMessage());
			
		}
		printlog("DONE: Get Current Contents...", l4j);

		
		return content.toString();
	}
	
	private static void replaceColor(String color, String color2) throws Exception{
		try {
			printlog("BEGIN: Replacing placeholder color...", l4j);
			String currentContent = InstallParent.readFile(cssPlaceholderPath, false, false);
			String newContent = currentContent.replace(placeholderText, color);
			
			String newContent2 = newContent.replace(placeholderText2, color2);

			
			InstallParent.writeToFile(cssPath, newContent2, true);
			
			printlog("DONE: Replacing placeholder color...", l4j);
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Replacing placeholder color...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
	}

}
