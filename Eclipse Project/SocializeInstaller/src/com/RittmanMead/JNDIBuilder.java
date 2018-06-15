package com.RittmanMead;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.jdbc.pool.OracleDataSource;

public class JNDIBuilder extends InstallParent{
	private static final Logger l4j = LogManager.getLogger(JNDIBuilder.class.getName());
	private static String dataSourceString = "jdbc/rmrep_datasource";
	
	public static void build(String serverIpString, String port, String serverName, String serverLogin, String serverPassword, String jdbcString, String dbUser, String dbPassword) throws Exception{
		try {
			printlog("BEGIN: Building JNDI...", l4j);
			Hashtable<String, String> table = buildMap(serverIpString + ":" + port, serverLogin, serverPassword);
			Context context = new InitialContext(table);
			OracleDataSource datasource = buildDataSource(serverName, jdbcString, dbUser, dbPassword);
			submitToServer(context, datasource);
			printlog("DONE: Building JNDI...", l4j);
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Building JNDI...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}

	}
	
	private static void submitToServer(Context context, OracleDataSource dataSource) throws Exception{
		printlog("BEGIN: Building JNDI Server Submission...", l4j);
		try {
			context.bind(dataSourceString, (javax.sql.DataSource)dataSource);
			context.close();
			
			printlog("DONE: Building JNDI Server Submission...", l4j);

		}
		catch (Exception e) {
			printlog("ERROR: Building JNDI Server Submission...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
	}
	
	//build map for context values for jndi
	private static Hashtable<String, String> buildMap(String serverIpString, String serverLogin, String serverPassword) throws Exception{
		Hashtable<String, String> table = new Hashtable<String, String>();

		try {
			printlog("BEGIN: Building JNDI Configuration...", l4j);
			
			table.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			table.put(Context.PROVIDER_URL, "t3://" + serverIpString);
			table.put(Context.SECURITY_PRINCIPAL, serverLogin);
			table.put(Context.SECURITY_CREDENTIALS, serverPassword);	

		}
		catch (Exception e) {
			printlog("ERROR: Building JNDI Configuration...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
		
		printlog("DONE: Building JNDI Configuration...", l4j);
		return table;
	}
	
	private static OracleDataSource buildDataSource(String serverName, String jdbcString, String dbUser, String dbPassword) throws Exception{
		printlog("BEGIN: Building Data Source...", l4j);
		OracleDataSource dataSource = new OracleDataSource();

		try {
			dataSource.setURL(jdbcString);
			dataSource.setUser(dbUser);
			dataSource.setPassword(dbPassword);
			dataSource.setServerName(serverName);
			
		}
		catch (Exception e) {
			printlog("ERROR: Building Data Source...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
		
		printlog("DONE: Building Data Source...", l4j);
		return dataSource;

	}

}
