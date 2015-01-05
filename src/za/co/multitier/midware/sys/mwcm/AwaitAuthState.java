/*
 * AwaitAuthState.java
 *
 * Created on January 30, 2007, 5:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwcm;
 

import za.co.multitier.midware.sys.datasource.*;
import za.co.multitier.midware.sys.appservices.*;
/**
 *
 * @author Administrator
 */
public class AwaitAuthState extends BinTippingState
{
    protected String invalid_id;
	protected String invalid_msg;
	
	/** Creates a new instance of AwaitAuthState */
	public AwaitAuthState(String ip,String bin_id,BinTippingScan parent,String invalid_msg) throws Exception
	{
		super(ip,bin_id,parent);
		 this.device_instruction = this.parent.createBintipInstruction(false,true,this.parent.active_device.getProduction_run_code(),
					false,false,invalid_msg);
		this.invalid_id = bin_id;
		this.invalid_msg = invalid_msg;
		//this.parent.midware_console.sysMsg("invalid id " + this.invalid_id);
	}
	
	//---------------------------------------------------------------------------------------------------
	//This method must handle 3 possible scenarios:
	//1) the bintip operator has cancelled the previous invalid bin scan with a valid bin scan-
	//   we need to check for that- if this is the case, this state must transition to 'AwaitBinScan'
	//2) An operator has authorised an override and pressed either
	//   a) 'Yes' (F3): in which case we need to allow the bin to be tipped and logg the occurrence
	//   b) 'No'  (F4): '''  '''        ''''     not allow the bin tip and sent a 'remove bin' message'
	//3) A supervisor has not authorised an override or a new valid bin scan occurred, in which case we
	//   need to transision to the InvalidBinState (which will transision back to us)
	//----------------------------------------------------------------------------------------------------
	public void scanBin(String mass) throws Exception
	{
		//try
		//{
			 //------------------------------------------------------------------------------------
			// Check for scenario where user cancelled an invalid bin scan by scanning a valid bin
			//-------------------------------------------------------------------------------------
			   Bin bin = null;
			  if (this.parent.key_pressed == DeviceCommands.TRM_KEYPAD.none)
			     bin = BinTippingDAO.getBin(this.bin_id);
				 
			  //Create bin object- needed for various scanarios
			  InvalidBin invalid_bin = new InvalidBin();
			  invalid_bin.setAuthorisor_name(this.parent.authoriser_name);
			  String bin_id = null;
			  if(this.getCurrent_bin_id()== null) //two ids scenario (we came in this state with one id and then a scan took place)
				  bin_id = this.bin_id;
			  else
				  bin_id = this.getCurrent_bin_id();
			  
			  invalid_bin.setBin_id(bin_id);
			  invalid_bin.setLine_code(this.parent.active_device.getLine_code());
			  invalid_bin.setProduction_run_code(this.parent.active_device.getProduction_run_code());
			  invalid_bin.setProduction_run_id(this.parent.active_device.getProduction_run_id());
			  invalid_bin.setTipped_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));
			  invalid_bin.setError_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));
	          
			  if(bin != null && !AwaitScanState.is_tipped_bin(this.bin_id))
			  { //Previous invalid scan thus cancelled
				  this.parent.active_state = new AwaitScanState(this.ip,this.bin_id,this.parent,bin);
				  this.parent.active_state.scanBin(mass);
				  //Log fact that invalid bin was cancelled
                   InvalidBin inv_bin = new InvalidBin();


                  inv_bin.setError_description("A valid bin was scanned to cancel the invalid bin with id: " + this.invalid_id);
					//Question: Is this right- we do not care or control whether user did in fact remove bin?
                  inv_bin.setError_code(MidwareErrorLogEntry.INVALID_BIN_CANCELLED_WITH_VALID_BINSCAN);
                  inv_bin.setBin_id(this.invalid_id); //the old invalid bin id
					BinTippingDAO.createBinErrorLogEntry(inv_bin);
				  
				  
			  }
			  else
			  {
				  if(this.parent.HasOverrideAuthorisation && (this.parent.key_pressed == DeviceCommands.TRM_KEYPAD.F3||
					                              this.parent.key_pressed == DeviceCommands.TRM_KEYPAD.F4))
				  {
					 
					  	  
					  if(this.parent.key_pressed == DeviceCommands.TRM_KEYPAD.F3)//i.e. 'YES'
					  {
						   //------------------------------------------------------------
						   //Get bins_tipped or bins_tipped_invalid record and get weight
						   //------------------------------------------------------------
						   Bin orig_bin = BinTippingDAO.getInvalidBin(invalid_bin.getBin_id().toString());
						   if(orig_bin == null)
							  orig_bin = BinTippingDAO.getTippedBin(invalid_bin.getBin_id().toString());
						   
						   if(orig_bin != null)
							   invalid_bin.setWeight(orig_bin.getWeight());
						   
						  //Transision to AwaitScan state
						   invalid_bin.setError_description(this.invalid_msg);
						   BinTippingDAO.invalidBinAuthorizedTransaction(invalid_bin);
					 
						   this.parent.active_state = new AwaitScanState(this.ip,null,this.parent);
						   this.parent.active_state.device_instruction = this.parent.createBintipInstruction(true,false,this.parent.active_device.getProduction_run_code(),
					       false,true,BinTippingScan.SCAN_NEXT_BIN_MSG);
						  
						  
					  }
					  else if(this.parent.key_pressed == DeviceCommands.TRM_KEYPAD.F4)//i.e. 'NO''
					  {
						   invalid_bin.setError_description("Supervisor ordered invalid bin to be removed");
						   invalid_bin.setError_code(MidwareErrorLogEntry.INVALID_BIN_REMOVAL);
						   //Question: Is this right- we do not care or control whether user did in fact remove bin?
						   BinTippingDAO.createBinErrorLogEntry(invalid_bin);
						   this.parent.active_state = new AwaitScanState(this.ip,this.bin_id,this.parent);
						   this.parent.active_state.device_instruction = this.parent.createBintipInstruction(false,true,this.parent.active_device.getProduction_run_code(),
					       false,false,BinTippingScan.REMOVE_BIN_MSG);
					  }
					  else //Wrong key pressed or nothing or wrong bin scan, but has authorisation
					  {
						   this.device_instruction = this.parent.createBintipInstruction(false,true,this.parent.active_device.getProduction_run_code(),
					       false,false,BinTippingScan.PRESS_F3_F4_MSG);
					  }
					  
					
				  }
				  else //No authorisation
				  {
					  this.device_instruction = this.parent.createBintipInstruction(false,true,this.parent.active_device.getProduction_run_code(),
				     false,false,BinTippingScan.NO_OVERRIDE_AUTH);
				  }
				  
				  //we need to reset authorisation- else operator is forever authorised;
				  this.parent.HasOverrideAuthorisation = false;
				  this.parent.authoriser_name = null;
				  
			  }
			  
			
		//} catch (Exception ex)
//		{
//			throw new Exception("AwaitAuthBinState's 'scan_bin()' method failed. Reported exception: " + ex.toString());
//		}
		
	}
	
}
