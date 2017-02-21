/*
 * BinTippingCache.java
 *
 * Created on January 30, 2007, 7:40 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.appservices;

import java.util.HashMap;
/**
 *
 * @author Administrator
 */

public class BinTippingCache
{
	
	private HashMap devices_store;
	private HashMap devices_run_codes;
	private static final BinTippingCache instance;
	
	static
	{
		
		instance = new BinTippingCache();
	}
	public static BinTippingCache getDevicesCache() {return instance;}
	
	
	public BinTippingCache()
	{
		
		devices_store = new HashMap();
		devices_run_codes = new HashMap();
	}
	
	
	public Object getDeviceState(String ip,String production_run_code,String mw_type) throws Exception
	{
		
		String key = ip + "|" + production_run_code + "|" + mw_type;
		
		return devices_store.get(key);
		
		
	}
	
	public void setDeviceState(String ip,String production_run_code,String mw_type,Object state) throws Exception
	{  
		try
		{
		String key = ip + "|" + production_run_code + "|" + mw_type;
		
		//---------------------------------------------------------------------------------
		//We need to check whether a given device of a given type has been switched
		//to a new run. If so we need to delete such a device's state from the device store
		//----------------------------------------------------------------------------------
		
		if (devices_run_codes.containsKey(ip))
		{
			if(!devices_run_codes.get(ip).equals(production_run_code))
			{
				//the device has been started on a new run, clear memory and reset run code
				String old_store_key = ip + "|" + (String)devices_run_codes.get(ip) + "|" + mw_type;
				devices_store.remove(old_store_key);
				
			}		
		}
		
		devices_store.put(key,state);
		devices_run_codes.put(ip,production_run_code);
		
		}
		catch(Exception e)
		{
			throw new Exception("Device state could not be set for ip: " + ip + " and production run code: " + production_run_code + " and device type: " + mw_type
				                + ". Reported exception: " + e);
		}
		
	}
	
	

	
	
}
