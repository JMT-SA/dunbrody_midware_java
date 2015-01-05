/*
 * MidwareConfig.java
 *
 * Created on March 30, 2007, 3:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.appservices;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class MidwareConfig
{
	private static MidwareConfig instance;
	
	 public static MidwareConfig getInstance() 
	 {
		 if(instance == null)
			 instance = new MidwareConfig();
		 
		 return instance;
	 }
	
	private Properties settings;
	private Properties palletizing_messages;
	
	private MidwareConfig () 
	{
		try
		{
            MyClassPath.addFile("/opt/jmt/midware_config");
			settings = new Properties();
			InputStream prop_file = ClassLoader.getSystemClassLoader ().getResourceAsStream("app_settings.properties");
			settings.load (prop_file);
			
			palletizing_messages = new Properties();
			InputStream palletizing_messages_file = ClassLoader.getSystemClassLoader ().getResourceAsStream("palletizing_messages.properties");
			palletizing_messages.load (palletizing_messages_file);
			
		} catch (IOException ex)
		{
		   //Logger.handleException(null,"midware properties file could not be loaded.",ex.toString(),DeviceTypes.UNKNOWN,"N.A.",3,"",ex.getStackTrace());
            System.out.println("PROPERTIES FILE NOT LOADED");
            ex.printStackTrace();
			
		}
		
	}

	public Properties getSettings ()
	{
	  
		return settings;
	}
	
	public Properties getPalletizingMessages ()
	{
		return palletizing_messages;
	}
	
}
