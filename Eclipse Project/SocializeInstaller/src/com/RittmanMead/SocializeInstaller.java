package com.RittmanMead;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocializeInstaller extends InstallParent{
	private static String userCreatePath = "./SQLScripts/user_create_original.sql";
	private static String custom_user_path = "./SQLScripts/user_create.sql";
	private static String dbplaceHolderText = "pathplaceholder";
	private static String pwplaceHolderText = "passwordplaceholder";
	private static String alterSessionText = "12caltersessionplaceholder";
	private static String alterContainerText = "12ccontainerplaceholder";
	private static String alterContainerReplacement = "CONTAINER=CURRENT";
	private static String applicationConstantURL = "http://(server-url):9704/Socialize/applicationConstant/list";
	private static final Logger l4j = LogManager.getLogger(SocializeInstaller.class.getName());
	private static String socializeUser = "RMREP";
	private static HashMap<String, String> argumentsMap = new HashMap<String, String>();
	
	//argument headers
	private static String methodHeader = "-Method";
	private static String jdbcHeader = "-JDBC";
	private static String addressHeader = "-Address";
	private static String adminPortHeader = "-AdminPort";
	private static String serverPortHeader = "-ServerPort";
	private static String pathHeader = "-Path";
	private static String serverNameHeader = "-ServerName";
	private static String serverUserHeader = "-WeblogicUser";
	private static String serverLoginHeader = "-WeblogicPassword";
	private static String dbLocationHeader = "-DatabasePath";
	private static String dbLoginHeader = "-DatabaseUser";
	private static String dbPasswordHeader = "-DatabasePassword";
	private static String newDBUserPasswordHeader = "-NewDBUserPassword";
	private static String restartHeader = "-Restart";
	private static String _12cContainerHeader = "-12cContainer";
	private static String linkColorHeader = "-OffColor";
	private static String linkColorHeader2 = "-ActiveColor";
	private static String booleanHeader = "-Option";
	
	public static void main(String[] args) throws Exception{
		String method = null;
		
		if (args.length == 0) {
			new InstallationWindow();
		}
		else if (args.length > 0) {
			for (String arg: args) {
				if (arg.indexOf(':') < 0){
					System.out.println("Missing \':\' to separate arguments in \"arg\".");
					System.exit(1);
				}
				else {
					argumentsMap.put(arg.substring(0, arg.indexOf(':')), arg.substring(arg.indexOf(':') + 1, arg.length()));
				}
			}
			
			switch (argumentsMap.get(methodHeader)){
			case "BuildDatabase":
				dbBuild();
				break;
			case "BuildJNDI":
				jndiBuild();
				//System.out.println("This is currently not supported. Please manually enable the JNDI connection named \"jdbc/remrep_datasource\".");
				break;
			case "BuildDeploy":
				deployBuild();
				break;
			case "BuildConstants":
				System.out.println("To activate your Application Constants please go to: " + applicationConstantURL);
				break;
			case "BuildLink":
				customLinkBuild();
				break;
			case "Uninstall":
				uninstall();
				break;
			case "UninstallAll":
				uninstallAll();
				break;
			case "InstallAll":
				installAll();
				break;
			case "RestartServer":
				restartServer();
				break;
			case "CustomizeLink":
				customizeSocializeLink();
				break;
			default:
				System.out.println("ERROR Usage: -Method:<Command> \nApplicable Commands: InstallAll, UninstallAll, BuildDatabase, BuildJNDI, BuildDeploy, BuildConstants, BuildLink, Uninstall");
			}
		}
	}
	
	private static void customizeSocializeLink() throws Exception{
		String[] headersINeed = {pathHeader, linkColorHeader, linkColorHeader2};
		
		try {
			printlog("BEGIN: Customizing Socialize link...", l4j);
			checkInput(headersINeed);
			
			CustomizeLinkBuilder.build(argumentsMap.get(pathHeader), argumentsMap.get(linkColorHeader), argumentsMap.get(linkColorHeader2));
			
			printlog("DONE: Customizing Socialize link...", l4j);

		}
		catch (RMException e) {
			System.exit(1);
		}
		catch (Exception e) {
			printlog("ERROR: Customizing Socialize link...", l4j, e);
			System.exit(1);
		}
	}
	
	private static void installAll() throws Exception{
		String[] headersINeed = {jdbcHeader, addressHeader, adminPortHeader, serverPortHeader, pathHeader, serverNameHeader, serverUserHeader, serverLoginHeader, dbLocationHeader, dbLoginHeader, dbPasswordHeader, newDBUserPasswordHeader};
		
		try {	
			printlog("BEGIN: Install All...", l4j);
			
			//check if map has minimum components required to continue
			checkInput(headersINeed);
			
			//each individual method checks the map input to see if it has the key/values it needs
		
			//build DB
			dbBuild();
			System.out.println("Construction of database completed, this step should not be repeated.");
			
			//build jndi
			//currently disabled
			jndiBuild();
			System.out.println("Construction of JNDI Connection completed, this step should not be repeated.");
			
			
			//build deploy
			deployBuild();
			System.out.println("Deployment completed, this step should not be repeated.");

			//customlinks
			customLinkBuild();
			System.out.println("Construction of customlinks.xml completed, this step should not be repeated.");
			
			//check to see if want to restart
			boolean restart = false;
			if (argumentsMap.get(restartHeader) != null) {
				if (argumentsMap.get(restartHeader).equals("true")){
					restart = true;
				}
			}
			
			if (!restart) {
				printlog("Presentation Services must be restarted for the custom link to display.", l4j);
			}
			else {
				printlog("Restart flag is set to true. Are you sure you want to restart the server? This may take several minutes.", l4j);
				Scanner scan = new Scanner(System.in);
				String answer = "";
				while (!(answer.equals("y")) && !(answer.equals("n"))){
						printlog("Please enter y or n.", l4j);
						answer = scan.nextLine();
				}
				
				if (answer.equals("y")){
					restartServer();
					printlog("Server restart successful. Thank you for waiting!", l4j);
				}
				else if (answer.equals("n")){
					printlog("Server restart aborted. This must be done before using Socialize.", l4j);
				}
				
				scan.close();

			}
			
			printlog("DONE: Install All...", l4j);
		}
		catch (RMException e) {
			System.exit(1);
		}
		catch (Exception e) {
			printlog("ERROR: Install All...", l4j, e);
			System.exit(1);
		}
	}
	
	private static void uninstallAll() throws Exception {
		String[] headersINeed = {pathHeader, jdbcHeader, addressHeader, dbLoginHeader, adminPortHeader, dbPasswordHeader, serverUserHeader, serverLoginHeader};
		
		try {
			
			printlog("BEGIN: Uninstall All...", l4j);
			checkInput(headersINeed);
			
			SocializeUNInstaller.start(argumentsMap.get(pathHeader), false);
			SocializeUNInstaller.start(argumentsMap.get(jdbcHeader), argumentsMap.get(dbLoginHeader), argumentsMap.get(dbPasswordHeader));
			SocializeUNInstaller.start(argumentsMap.get(addressHeader), argumentsMap.get(adminPortHeader), argumentsMap.get(serverUserHeader), argumentsMap.get(serverLoginHeader));
			
			printlog("DONE: Uninstall All...", l4j);
		}
		catch (RMException e) {
			System.exit(1);
		}
		catch (Exception e) {
			printlog("ERROR: Uninstall All...", l4j, e);
			System.exit(1);
		}
	}
	
	private static void uninstall() throws Exception{
		try {
			printlog("BEGIN: Uninstall...", l4j);

			switch(argumentsMap.size()) {
				case 2:
					String[] headersINeed = {pathHeader};
					checkInput(headersINeed);
					
					//remove customlinks
					if (argumentsMap.get(booleanHeader).equals("true"))
					{
						//decor is true to uninstall decoration in commons.css
						SocializeUNInstaller.start(argumentsMap.get(pathHeader), true);
					}
					else {
						SocializeUNInstaller.start(argumentsMap.get(pathHeader), false);

					}
					
					boolean restart = false;
					if (argumentsMap.get(restartHeader) != null) {
						if (argumentsMap.get(restartHeader).equals("true")){
							restart = true;
						}
					}
					
					if (!restart) {
						printlog("Presentation Services must be restarted for the custom link to display.", l4j);
					}
					else {
						printlog("Restart flag is set to true. Are you sure you want to restart the server? This may take several minutes.", l4j);
						Scanner scan = new Scanner(System.in);
						String answer = "";
						while (!(answer.equals("y")) && !(answer.equals("n"))){
								printlog("Please enter y or n.", l4j);
								answer = scan.nextLine();
						}
						
						if (answer.equals("y")){
							restartServer();
							printlog("Server restart successful. Thank you for waiting!", l4j);
						}
						else if (answer.equals("n")){
							printlog("Server restart aborted. This must be done before using Socialize.", l4j);
						}
						
						scan.close();

					}
					
					break;
				case 4:
					String[] headersINeed2 = {jdbcHeader, dbLoginHeader, dbPasswordHeader};
					checkInput(headersINeed2);
					
					//drop tables
					SocializeUNInstaller.start(argumentsMap.get(jdbcHeader), argumentsMap.get(dbLoginHeader), argumentsMap.get(dbPasswordHeader));
					break;
				case 5:
					String[] headersINeed3 = {addressHeader, adminPortHeader, serverUserHeader, serverLoginHeader};
					checkInput(headersINeed3);
					
					//undeploy
					SocializeUNInstaller.start(argumentsMap.get(addressHeader), argumentsMap.get(adminPortHeader), argumentsMap.get(serverUserHeader), argumentsMap.get(serverLoginHeader));
					break;
				default:
					System.out.println("ERROR Usage: Invalid number of arguments.");
					System.exit(1);
					break;
			}
			
			printlog("DONE: Uninstall...", l4j);
		}
		catch (RMException e) {
			System.exit(1);
		}
		catch (Exception e) {
			printlog("ERROR: Uninstall...", l4j, e);
			System.exit(1);
		}
	}
	
	//build the Socialize Link
	private static void customLinkBuild() throws Exception {
		String[] headersINeed = {pathHeader};			

		try {
			printlog("BEGIN: BuildLink...", l4j);
			checkInput(headersINeed);
			
			LinkBuilder.build(argumentsMap.get(pathHeader));
			printlog("For the custom links to be updated, the server must be restarted. Either perform this step manually or pass the -Method:RestartServer.", l4j);
			printlog("DONE: BuildLink...", l4j);
			printlog("This step should not be repeated, or you may see adverse results.\nTo perform this step again, please uninstall it first.", l4j);
			printlog("The server may require a restart for this to take effect.", l4j);
			
		}
		catch (RMException e) {
			System.exit(1);
		}
		catch (Exception e) {
			printlog("ERROR: BuildCustomLink...", l4j, e);
			System.exit(1);
		}
	}
	
	private static void dbBuild() throws Exception{
		String[] headersINeed = {dbLocationHeader, jdbcHeader, dbLoginHeader, dbPasswordHeader, newDBUserPasswordHeader};
	
		
		try {
			
			printlog("BEGIN: BuildDatabase...", l4j);
			checkInput(headersINeed);

		
			String dbHomePath = argumentsMap.get(dbLocationHeader);
			
			if ((dbHomePath.charAt(dbHomePath.length()-1) != '\\') && (dbHomePath.charAt(dbHomePath.length()-1) != '/')) {
				System.out.println("Last character in path must be either / or \\");
				System.exit(1);
			}
			
			//update file for dbHomePath, new UserPW
			String finalString = "";
			String userCreateScript = InstallParent.readFile(userCreatePath, false, true);
			String newString = userCreateScript.replace(dbplaceHolderText, dbHomePath);
			String updatedString = newString.replace(pwplaceHolderText, argumentsMap.get(newDBUserPasswordHeader));		
			
			//handle stuff if 12c
			if (argumentsMap.get(_12cContainerHeader) != null) {
				String oc12Container = argumentsMap.get(_12cContainerHeader);		
				String updatedString2 = updatedString.replace(alterSessionText, "ALTER SESSION SET CONTEXT=" + oc12Container);
				finalString = updatedString2.replace(alterContainerText, alterContainerReplacement);
			}
			else {
				String updatedString2 = updatedString.replace(alterSessionText, "");
				finalString = updatedString2.replace(alterContainerText, "");
			}
			
			InstallParent.writeToFile(custom_user_path, finalString, true);
			
			DatabaseBuilder.build(argumentsMap.get(jdbcHeader), argumentsMap.get(dbLoginHeader), argumentsMap.get(dbPasswordHeader));
			
			printlog("DONE: BuildDatabase...", l4j);
		}
		catch (RMException e) {
			System.exit(1);
		}
		catch (Exception e) {
			printlog("ERROR: BuildDatabase...", l4j, e);
			System.exit(1);
		}
	}
	
	private static void jndiBuild() throws Exception{
		String[] headersINeed = {addressHeader, serverPortHeader, serverNameHeader, serverUserHeader, serverLoginHeader, jdbcHeader, newDBUserPasswordHeader};
		
		try {

			printlog("BEGIN: BuildJNDI...", l4j);
			checkInput(headersINeed);

			String dbUser = socializeUser;
			JNDIBuilder.build(argumentsMap.get(addressHeader), argumentsMap.get(serverPortHeader), argumentsMap.get(serverNameHeader), argumentsMap.get(serverUserHeader), argumentsMap.get(serverLoginHeader), argumentsMap.get(jdbcHeader), dbUser, argumentsMap.get(newDBUserPasswordHeader));
			printlog("DONE: BuildJNDI...", l4j);
		}
		catch (RMException e) {
			System.exit(1);
		}
		catch (Exception e) {
			printlog("ERROR: BuildJNDI...", l4j, e);
			System.exit(1);
		}
	}

	private static void deployBuild() throws Exception{
		String[] headersINeed = {addressHeader, adminPortHeader, serverUserHeader, serverLoginHeader, serverNameHeader};
		
		try {
			
			printlog("BEGIN: BuildDeploy...", l4j);
			checkInput(headersINeed);

			DeployBuilder.build(argumentsMap.get(addressHeader), argumentsMap.get(adminPortHeader), argumentsMap.get(serverUserHeader), argumentsMap.get(serverLoginHeader), argumentsMap.get(serverNameHeader));
			
			printlog("DONE: BuildDeploy...", l4j);
		}
		catch (RMException e) {
			System.exit(1);
		}
		catch (Exception e) {
			printlog("ERROR: BuildDeploy...", l4j, e);
			System.exit(1);
		}
	}
	
	private static void checkInput(String[] headers) {
		StringBuilder missingArgsString = new StringBuilder("ERROR Usage - Missing Arguments: ");
		boolean errors = false;
		for (String header: headers) {
			if (argumentsMap.get(header) == null) {
				errors = true;
				missingArgsString.append(header + ":(value) ");
			}
		}
		
		if (errors) {
			printlog(missingArgsString.toString(), l4j);
			System.exit(1);
		}
	}
	
	private static void restartServer() throws Exception{
		String[] headersINeed = {addressHeader, adminPortHeader, serverUserHeader, serverLoginHeader};
		
		try {
			
			printlog("BEGIN Server Restart...", l4j);
			checkInput(headersINeed);
			
			//code from oracle 22 Introducing the Oracle BI Systems Management API... Section 22.3.1.2
			String jmxUrl = "service:jmx:t3://" + argumentsMap.get(addressHeader) + ":" + argumentsMap.get(adminPortHeader) + "/jndi/weblogic.management.mbeanservers.domainruntime";
			Hashtable<String, String> h = new Hashtable<String, String>();
			h.put(Context.SECURITY_PRINCIPAL, argumentsMap.get(serverUserHeader));
			h.put(Context.SECURITY_CREDENTIALS, argumentsMap.get(serverLoginHeader));
			h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, "weblogic.management.remote");
			JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(jmxUrl), h);	
			MBeanServerConnection mbs = connector.getMBeanServerConnection();
			ObjectName biDomainMBeanName = new ObjectName("oracle.biee.admin:type=BIDomain,group=Service");
			ObjectName[] names = (ObjectName[])mbs.getAttribute(biDomainMBeanName, "BIInstances");
			ObjectName name = names[0];
			
			printlog("BEGIN Stopping Server...", l4j);
			Object[] args = new Object[]{};
			String[] sig = new String[]{};
			mbs.invoke(name, "stop", args, sig);
			printlog("DONE Stopping Server...", l4j);

			
			printlog("BEGIN Starting Server...", l4j);
			mbs.invoke(name, "start", args, sig);
			printlog("DONE Starting Server...", l4j);

			printlog("DONE Server Restart...", l4j);

		}
		catch (Exception e) {
			printlog("ERROR Server Restart...", l4j, e);
			throw new Exception(e);
		}

	}

}
