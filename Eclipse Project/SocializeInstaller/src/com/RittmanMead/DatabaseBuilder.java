package com.RittmanMead;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DatabaseBuilder extends InstallParent{
	//paths for the sql scripts we will run
	private static String[] script_paths;
	private static String[] SQLArray;
	private static String loaderPath = "./configs/install_sql_scripts.txt";
	private static final Logger l4j = LogManager.getLogger(DatabaseBuilder.class.getName());

	
	
	public static void build(String jdbcString, String login, String pw) throws Exception{
		try {
			printlog("BEGIN: Building Socialize tables...", l4j);
			
			//load files into script_paths
			script_paths = getScriptPaths(loaderPath);
			SQLArray = new String[script_paths.length];
			
			//get sql scripts from files
			//these lines throw exceptions from loading the files
			printlog("BEGIN: Loading SQL files...", l4j);

			for (int i = 0; i < script_paths.length; i++) {
				printlog("BEGIN: Loading - " + script_paths[i], l4j);
				SQLArray[i] = getSQL(script_paths[i]);
			}
			printlog("DONE:loading SQL files...", l4j);
			
			//these lines throw exceptions from connecting to database
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection(jdbcString, login, pw);
			runSQL(conn, SQLArray, script_paths);
			
			printlog("DONE: Building Socialize tables...", l4j);
			
		} 
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Building Socialize tables...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
	}
	
	//grabs the paths of the sql files
	private static String[] getScriptPaths(String loaderPath) throws Exception {
		String scriptString = null;
		printlog("BEGIN: Grabbing Script Paths...", l4j);
		
		scriptString = readFile(loaderPath, false, true);		
		printlog("DONE: Grabbing Script Paths...", l4j);
		return scriptString.split(";");
	}
	
	//executes the SQL against the database
	private static void runSQL(Connection conn, String[] args, String[] paths) throws Exception{
		Statement exec = null;
		String currentQuery = null;
		try {
			
			printlog("BEGIN: Running SQL", l4j);

			exec = conn.createStatement();

			
			//split to allow multiple queries in same file, and don't run blank lines
				for (int i = 0; i < args.length; i++) {
					String query = args[i];
				
					printlog("BEGIN: Executing Script - " + paths[i], l4j);
					for (String individualQuery: query.split(";")){
						if (individualQuery.length() > 1) {
							currentQuery = individualQuery;
							exec.executeUpdate(individualQuery);
						}
					}
					printlog("DONE: Executing Script - " + paths[i], l4j);

				}
				conn.commit();
			
				printlog("DONE: Running SQL", l4j);

		}

		catch (Exception e) {
			conn.rollback();
			printlog("ERROR: Running SQL", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
		finally {
			exec.close();
			conn.close();
		}
		
	}
	

	//next two methods can throw File exceptions
	private static String getSQL(String path) throws Exception{
		return readFile(path, false, true);
	}
	
}
