package com.RittmanMead;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LinkBuilder extends InstallParent{
	private static String customlink_whole_path = "./configs/customlink_whole.txt";
	private static String customlink_partial_path = "./configs/customlink_partial.txt";	

	private static final Logger l4j = LogManager.getLogger(LinkBuilder.class.getName());

	
	public static void build(String path) throws Exception{
		printlog("BEGIN: Link Build...", l4j);

		try {
			String currentContent = getCurrentContents(path);
			
			if (currentContent.length() < 5) {
				makeCustomLinks(path);
			}
			else {
				addCustomLink(path, currentContent);
			}
			
			printlog("DONE: Link Build...", l4j);
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Link Build...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
		
	}
	
	private static String getCurrentContents(String path) throws Exception{
		StringBuilder content = new StringBuilder();
		printlog("BEGIN: Get Current Contents...", l4j);

		try {
			content.append(readFile(path, true, false));
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e2) {
			printlog("WARN: Get Current Contents... Not found. Creating.", l4j);
			content.append("");
			
		}
		printlog("DONE: Get Current Contents...", l4j);

		
		return content.toString();
	}
	
	private static void makeCustomLinks(String path) throws Exception{
		try {
			printlog("BEGIN: Creating Custom Links...", l4j);

			String wholeContent = readFile(customlink_whole_path, true, false);
			createFile(path);
			writeToFile(path, wholeContent, false);
			printlog("DONE: Creating Custom Links...", l4j);
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Creating Custom Links...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
	}
	
	private static void addCustomLink(String path, String currentContent) throws Exception{
		StringBuilder newContent = new StringBuilder();
		try {
			printlog("BEGIN: Adding Custom Link...", l4j);
			 int indexStart = currentContent.indexOf("</customLinks>");
			 
			 if (indexStart < 0) {
				 //not in there
				printlog("WARN: Adding Custom Link... Adding new one.", l4j);
				 newContent.append(currentContent + "<customLinks>" + readFile(customlink_partial_path, false, false) + "</customLinks>");
			 }
			 else {
				printlog("WARN: Adding Custom Link... Appending.", l4j);
				 //means it should be in here -- put in everything else, then the new code, then the remaining old code
				 newContent.append(currentContent.substring(0, indexStart));
				 
				 newContent.append(readFile(customlink_partial_path, false, false));
				 newContent.append(currentContent.substring(indexStart, currentContent.length()));	
				 writeToFile(path, newContent.toString(), true);
			 }
			 
			printlog("DONE: Adding Custom Link...", l4j);

		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e){
			printlog("ERROR: Adding Custom Link...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
	}

}
