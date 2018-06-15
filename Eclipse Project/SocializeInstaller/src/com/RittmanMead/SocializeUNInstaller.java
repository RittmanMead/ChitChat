package com.RittmanMead;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.status.ProgressObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.tools.SessionHelper;

public class SocializeUNInstaller extends InstallParent{
	
	private static String dropAllUsers = "DROP user RMREP CASCADE";
	private static String dropAllTables = "DROP tablespace RMREP_TS INCLUDING CONTENTS AND DATAFILES";
	private static final Logger l4j = LogManager.getLogger(SocializeUNInstaller.class.getName());
	
	private static String protocol = "t3";
	private static String appName = "Socialize";
	
	//drop db and tables
	public static void start(String jdbcString, String dbLogin, String dbPassword) throws Exception{
		try {
			dropTables(jdbcString, dbLogin, dbPassword);	
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
	}
	
	//undeploy
	public static void start(String hostName, String portString, String adminUser, String adminPassword) throws Exception{
		try {
			undeploy(hostName, portString, adminUser, adminPassword);
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
	}
	
	//remove custom links
	public static void start(String path, boolean decor) throws Exception{
		try {
			if (decor) {
				removeLinkDecoration(path);
			}
			else {
				removeLink(path);
			}
			
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
	}
	
	private static void removeLinkDecoration(String path) throws Exception{
		try {
			
			printlog("BEGIN: Removing link decoration...", l4j);
			//get current contents
			String currentContent = readFile(path, false, false);
			
			//gets begin and end index of our content
			int beginIndex = currentContent.indexOf("/* B - SOCIALIZE NEEDS THIS - RM */");
			int endIndex = currentContent.indexOf("/* E - SOCIALIZE NEEDS THIS - RM */");
			
			String newContent = currentContent.substring(0, beginIndex) + currentContent.substring(endIndex + 34, currentContent.length());
			
			writeToFile(path, newContent, true);
			
			printlog("END: Removing Link decoration...", l4j);
			
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Removing Link decoration...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
	}
	
	
	private static void removeLink(String path) throws Exception{
		try {
			
			printlog("BEGIN: Removing Link...", l4j);
			//get current contents
			String currentContent = readFile(path, false, false);
			
			//gets begin and end index of our content
			int beginIndex = currentContent.indexOf("<!-- DO NOT CHANGE THESE LINES - RM -->");
			String substring = currentContent.substring(beginIndex, currentContent.length());

			int endIndex = substring.indexOf("</link>");
			
			String newContent = currentContent.substring(0, beginIndex) + substring.substring(endIndex + 7, substring.length());
			
			System.out.println(newContent);

			writeToFile(path, newContent, true);
			
			printlog("END: Removing Link...", l4j);
			
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Removing Link...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
	}
	
	//remove the database
	private static void dropTables(String jdbcString, String login, String password) throws Exception {
		try {
			printlog("BEGIN: Dropping Database...", l4j);

			System.out.println("Please ensure no active connections to database. Press enter to continue.");
			Scanner scanf = new Scanner(System.in);
			scanf.nextLine();
			
			Class.forName("oracle.jdbc.OracleDriver");
			Connection connection = DriverManager.getConnection(jdbcString, login, password);
			Statement dropStatement = connection.createStatement();
			
			dropStatement.executeUpdate(dropAllUsers);
			dropStatement.executeUpdate(dropAllTables);
			
			connection.commit();
			dropStatement.close();
			connection.close();
			
			printlog("DONE: Dropping Database...", l4j);
		}
		catch (Exception e) {
			printlog("ERROR: Dropping Database...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}

	}
	

	private static void undeploy(String hostName, String portString, String adminUser, String adminPassword) throws Exception{
		try {
			
			printlog("BEGIN: Undeploy...", l4j);

			
			DeploymentOptions options = new DeploymentOptions();
			options.setUseNonexclusiveLock(true);
			options.setGracefulIgnoreSessions(true);
			
			WebLogicDeploymentManager manager = SessionHelper.getRemoteDeploymentManager(protocol, hostName, portString, adminUser, adminPassword);

			TargetModuleID[] targetModuleIDs = manager.getAvailableModules(ModuleType.WAR, manager.getTargets());
			
			for (int i = 0;i<targetModuleIDs.length; i++) {
					//need to make sure target is Socialize
					if (targetModuleIDs[i].getModuleID().toString().equals(appName)) {
						TargetModuleID[] onesWeWant = new TargetModuleID[1];
						onesWeWant[0] = targetModuleIDs[i];
						
						manager.stop(onesWeWant, options);
						ProgressObject stat = manager.undeploy(onesWeWant, options);
						//need to sleep to force application to complete
						Thread.sleep(5000);
						//log stat
						break;
					}				
			}
			
			printlog("DONE: Undeploy...", l4j);
		}
		catch (Exception e) {
			printlog("ERROR: Undeploying...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}	
	}
	
	
	
	private static void removeJNDI() {
		
	}

}
