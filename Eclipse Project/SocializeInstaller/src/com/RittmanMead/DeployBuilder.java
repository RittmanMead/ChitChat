package com.RittmanMead;

import java.io.File;

import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.status.DeploymentStatus;
import javax.enterprise.deploy.spi.status.ProgressObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.tools.SessionHelper;

public class DeployBuilder extends InstallParent{
	
	private static String warPath = "./war/Socialize.war";
	private static final Logger l4j = LogManager.getLogger(DeployBuilder.class.getName());
	private static String protocol = "t3";
	private static String appName = "Socialize";
	private static String description = "server";
	
	public static String build(String hostName, String portString, String adminUser, String adminPassword, String serverName) throws Exception{
		DeploymentStatus stat = null;
		try {
			printlog("BEGIN: Building Deployment...", l4j);
			WebLogicDeploymentManager deployManager = SessionHelper.getRemoteDeploymentManager(protocol, hostName, portString, adminUser, adminPassword);
			DeploymentOptions options = new DeploymentOptions();
			
			Target targets[] = getTarget(deployManager, serverName);
			stat = deploy(options, deployManager, targets);
			printlog("DONE: Building Deployment...", l4j);
		}
		catch (RMException e) {
			throw new RMException(e.getMessage());
		}
		catch (Exception e) {
			printlog("ERROR: Building Deployment...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}	
		
		return stat.toString();
	}
	
	private static Target[] getTarget(WebLogicDeploymentManager deployManager, String name) throws Exception{	
		Target[] deployTargets = null;
		
		try {
			printlog("BEGIN: Acquiring Targets...", l4j);
			Target targets[] = deployManager.getTargets();
			deployTargets = new Target[1];
					
			for (Target t: targets) {
				if ((t.getDescription().equals(description)) && (t.getName().equals(name))) {
					deployTargets[0] = t;
					break;
				}
			}
			
			printlog("DONE: Acquiring Targets...", l4j);

		}
		catch (Exception e) {
			printlog("ERROR: Acquiring Targets...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
		
		return deployTargets;
	}
	
	private static DeploymentStatus deploy(DeploymentOptions options, WebLogicDeploymentManager deployManager, Target[] deployTargets) throws Exception{
		DeploymentStatus stat = null;
		
		try {
			
			//deploy
			printlog("BEGIN: Deployment Process...", l4j);
			
			options.setName(appName);
			options.setUseNonexclusiveLock(true);
			options.setGracefulIgnoreSessions(true);
			ProgressObject processStatus = deployManager.distribute(deployTargets, new File(warPath), null, options);
			stat = processStatus.getDeploymentStatus();
			
			//need to sleep to wait for logout in order to start
			Thread.sleep(10000);
			
			//now start
			TargetModuleID[] targetModuleIDs = deployManager.getAvailableModules(ModuleType.WAR, deployManager.getTargets());
			for (int i = 0;i<targetModuleIDs.length; i++) {
				//manager.undeploy(new TargetModuleID[]{targetModuleIDs[i]});
					if (targetModuleIDs[i].getModuleID().toString().equals("Socialize")) {
						//should be the Socialize .war
						TargetModuleID[] onesWeWant = new TargetModuleID[1];
						onesWeWant[0] = targetModuleIDs[i];
						
						//sleep to make sure it starts
						processStatus = deployManager.start(onesWeWant, options);
						Thread.sleep(8000);
						stat = processStatus.getDeploymentStatus();
					}				
			}
			printlog(stat.toString(), l4j);
			printlog("The previous line may not indicate a failure. View the Weblogic Console to be sure.", l4j);

		}
		catch (Exception e) {
			printlog("ERROR: Deployment Process...", l4j, e);
			throw new RMException("An unexpected error occurred: " + e.getMessage());
		}
		
		printlog("DONE: Deployment Process...", l4j);
		return stat;
		
	}

}
