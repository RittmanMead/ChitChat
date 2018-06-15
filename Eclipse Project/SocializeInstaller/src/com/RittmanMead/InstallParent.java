package com.RittmanMead;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class InstallParent {
	private static final Logger l4j = LogManager.getLogger(InstallParent.class.getName());
	
	protected static void printlog(String content, Logger l4j) {
		System.out.println(content);
		l4j.info(content);
	}
	
	protected static void printlog(String content, Logger l4j, Exception e) {
		System.out.println(content);
		System.out.println(e.getMessage());
		System.out.println("Read the log file to view the stacktrace.");
		l4j.error(content);
		l4j.error(e.getMessage());
		l4j.error(stackTraceToString(e));
	}
	
	private static String stackTraceToString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	//reads file string and returns it
	protected static String readFile(String path, boolean checking, boolean unformat) throws Exception {
		StringBuilder content = new StringBuilder();
		FileReader reader = null;
		File f = new File(path);
		
		try {
			printlog("BEGIN: Reading File - " + path, l4j);
			if ((!f.exists()) && (checking)){
				throw new RMException("The file does not exist: " + path);
			}
		
			reader = new FileReader(path);
			
			while (reader.ready()){
				char c = (char) reader.read();
					if ((c != '\n') && (c != '\0')) {
						content.append(c);
					}
				}
			
			printlog("DONE: Reading File - " + path, l4j);

			reader.close();
		}
		catch (Exception e) {
			printlog("ERROR: Reading from File...", l4j, e);
			throw new Exception("File not found");
		}

		return content.toString();
	}
	
	protected static void writeToFile(String path, String content, boolean overwrite) throws Exception {
		FileWriter writer = new FileWriter(path, (!overwrite));
		
		if (!new File(path).exists()){
			writer.close();
			printlog("File does not exists: " + path, l4j);
			throw new RMException("The file does not exist. I need it!: " + path );
		}
		
		try {
			printlog("BEGIN: Writing to File - " + path, l4j);
			
			if (overwrite) {
				writer.write(content);
			}
			else {
				writer.append(content);
			}
			writer.flush();
			writer.close();
			printlog("DONE: Writing to File - " + path, l4j);

		}
		catch (Exception e) {
			printlog("ERROR: Writing to File...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
		
	}
	
	protected static void createFile(String path) throws Exception{
		try {
			PrintWriter file = new PrintWriter(path);
			file.println(" ");
			file.flush();
			file.close();
		} catch (Exception e) {
			printlog("ERROR: Creating file...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
	}

}
