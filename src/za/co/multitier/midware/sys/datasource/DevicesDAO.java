/*
 * DevicesDAO.java
 *
 * Created on January 25, 2007, 1:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

import za.co.multitier.midware.sys.mwpl.ProductLabelScan;

import java.sql.SQLException;
import java.util.HashMap;

public class DevicesDAO
{
	
   //getDeviceByCodeAndSequence

	
	
	public static ActiveRunResource getActiveDevice(String device_code) throws Exception
	{
		try
		{

            ActiveRunResource device = (ActiveRunResource) DataSource.getSqlMapInstance().queryForObject("getDevice", device_code);
			return device;
		} catch (Exception ex)
		{
            if(ex.getMessage().toUpperCase().indexOf("TOO MANY CLIENTS") <= 0 && ex.getMessage().toUpperCase().indexOf("TOO MANY") > 0)
            {

                throw new Exception("More than one setup deployed to:" + device_code + ".Not allowed");
            }
			else
            throw new Exception("Active device could not be fetched. Reported exception: " + ex);
		}
		
	}
	
}
