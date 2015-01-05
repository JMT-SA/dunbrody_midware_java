/*
 * InvalidBinState.java
 *
 * Created on January 30, 2007, 4:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


package za.co.multitier.midware.sys.mwcm;

/**
 *
 * @author Administrator
 */

import za.co.multitier.midware.sys.datasource.*;

public class InvalidBinState extends BinTippingState
{
	
   
	/** Creates a new instance of InvalidBinState */
	public InvalidBinState(String ip,String bin_id,BinTippingScan parent) throws Exception
	{
		super(ip,bin_id,parent);
		
	}
	
	
	public void scanBin(String mass) throws Exception
	{
		//try
		//{
			 //we need to find a possible reason for invalidness: could be:
			 // 1) A previous invalid bin was scanned
			 // 2) A previous tipped bin was scanned
			 // 3) A unknown bin was scanned
			 
			 String invalid_msg = BinTippingScan.BIN_NOT_FOUND_MSG; //#3 (default)
			 if(BinTippingDAO.getTippedBin(this.bin_id)	!= null)
				 invalid_msg = BinTippingScan.TIPPED_BIN_REQ_AUTH;
			 else if(BinTippingDAO.getInvalidBin(this.bin_id)!= null)
				 invalid_msg = BinTippingScan.INVALID_BIN_REQ_AUTH;
				
			 	
			  this.parent.active_state = new AwaitAuthState(this.ip,this.bin_id,this.parent,invalid_msg);
			  
			
		//} catch (Exception ex)
		//{
		//	throw new Exception("InvalidBinState's 'scan_bin()' method failed. Reported exception: " + ex.toString());
		//}
		
	}
	
}
