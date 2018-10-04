package com.sometotest.eagent.tplan.base;

import com.tplan.robot.ApplicationSupport;
import com.tplan.robot.AutomatedRunnable;
import com.tplan.robot.preferences.UserConfiguration;
import com.tplan.robot.scripting.DefaultJavaTestScript;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class TPlanScriptRunner{
	private final static Logger logger = LoggerFactory.getLogger( TPlanScriptRunner.class );

	public Thread runScript ( DefaultJavaTestScript tplan_script ) throws URISyntaxException {
		//try{
			String licenseLocation = File.separator + "tplan" + File.separator + "script_runner";
			String licenseName = "TPlanLicense1-4-Dec-18.tlic";
			//String licenseFile = this.getClass().getResource( licenseLocation + File.separator + licenseName ).toURI().getPath();
			File imageFile = Utils.getFile( this, licenseLocation, licenseName );
			String licenseFile = imageFile.getPath();

			System.setProperty( "robot.licenseKey", licenseFile );

			String[] args = new String[]{ "-n", "-c", "java://localhost?screen=1" };

			ApplicationSupport robot = new ApplicationSupport();
			setTPlanConfiguration();
			AutomatedRunnable t = robot.createAutomatedRunnable(
							tplan_script, tplan_script.getClass().getName(), args, System.out, false );
			Thread runnable = new Thread( t );
			runnable.start();
			return runnable;
//		} catch ( URISyntaxException ex ) {
//			logger.info("Could not find the TPLan license!");
//			throw ex;
//		}
	}

	private void setTPlanConfiguration(){
		Map<String, String> overrideTable = UserConfiguration.getInstance().getOverrideTable();
		overrideTable.put( "localDesktop.useStoredKeymaps", "true" );
		overrideTable.put( "localDesktop.enableMouseWakeUp", "false" );
		overrideTable.put( "rfb.RefreshDaemon.enable", "false" );
		overrideTable.put( "localDesktop.debug", "true" );
		overrideTable.put( "tocr.commandTemplate",  "/opt/local/bin/tesseract $1 $2 $3" );
	}

	/**
	 * Copys the keymap, which is delivered with the eAgent client to the users home directory in order to avoid
	 * an initial keyboard mapping by T-Plan.
	 * TPlan cannot load specific file for Keymap !?!?!?!?!?!?!?
	 */
	public void copyKeymapToHomeDirectory(){
		String resourcePath = "";//(new File( this.class.getClassLoader().getResource( "" ).getPath() )).toString();
		File keymapDir = new File( resourcePath + File.separator + "base" + File.separator + "keymap" + File.separator );
		File homeDir = new File( System.getProperty( "user.home" ) );

		try{
			FileUtils.copyDirectory( keymapDir, homeDir );
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
