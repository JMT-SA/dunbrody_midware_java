/*
 * BinTippingState.java
 *
 * Created on January 30, 2007, 9:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwcm;

/**
 *
 * @author Administrator
 */

import za.co.multitier.midware.sys.appservices.DeviceNotFoundException;
import za.co.multitier.midware.sys.datasource.*;


public abstract class BinTippingState
{
	
	//----------------
	//MEMBER VARIABLES
	//----------------
	
	protected String device_instruction;
	protected String ip;
	public String bin_id;
	protected BinTippingScan parent;
	private String current_bin_id;
	

	/** Creates a new instance of BinTippingState */
	public BinTippingState(String ip,String bin_id,BinTippingScan parent) throws Exception
	{
		if(ip == null)
			throw new Exception("Bin tipping state cannot be created with a null ip");
		
		//if(bin_id == null)
		//	throw new Exception("Bin tipping state cannot be created with a null bin_id");
		
		
		this.ip = ip;
		this.bin_id = bin_id;
		this.parent = parent;
		
		
	}
	
	
	
	//--------
	//METHODS
	//--------
	
	public synchronized java.sql.Timestamp current_datetime() throws Exception
	{
		try
		{
	   
			return new java.sql.Timestamp(new java.util.Date().getTime());
		} 
		catch(Exception e)
		{
			throw new Exception("The 'get_current_datetime' function failed: " + e.toString());
		}
		finally
		{
			int t = 0;
		}
	}
	
	public abstract void scanBin(String mass) throws Exception;
	public String getDeviceInstruction()
	{
		return device_instruction;
	}

	public String getCurrent_bin_id()
	{
		return current_bin_id;
	}

	public void setCurrent_bin_id(String current_bin_id)
	{
		this.current_bin_id = current_bin_id;
	}

	
	
	
	
}
