/*
 * PalletizingAction.java
 *
 * Created on January 30, 2007, 11:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpt;


import java.util.Properties;
import za.co.multitier.midware.sys.appservices.*;
import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.midware.sys.appservices.DeviceCommands.ROBOT_KEYPAD;
import za.co.multitier.midware.sys.datasource.*;
import za.co.multitier.midware.sys.protocol.SysProtocol;

/**
 *
 * @author Administrator
 */
public class PalletizingAction extends DeviceScan
{
	
	//-------------------
	//Device instructions
	//-------------------
	

	public static String WELCOME_SCREEN = SysProtocol.TPALLETIZE + "Status=\"true\" Yellow=\"false\" Red=\"false\" Green=\"true\" LCD1=\"EMPTY BAY\" LCD2=\"SCAN CARTON OR RTB\" />";
	
	public static String ROBOT_SCREEN = SysProtocol.TPALLETIZE + "Status=\"true\" Yellow=\"%s\" Red=\"%s\" Green=\"%s\" LCD1=\"%S\" LCD2=\"%S\" />";
	
	 
	private static Properties messages = MidwareConfig.getInstance().getPalletizingMessages();
	
	//--------------
	//ERROR MESSAGES
	//--------------
	public static String PUT_CTB_BACK_MSG = getMessage("PUT_CTB_BACK_MSG");
	public static String NO_CTN_MSG = getMessage("NO_CTN_MSG");
	public static String WELCOME_MSG = getMessage("WELCOME_MSG");
	public static String EMPTY_BAY_MSG = getMessage("EMPTY_BAY_MSG");
	public static String CARTON_ALREADY_ON_PALLET_MSG = getMessage("CARTON_ALREADY_ON_PALLET_MSG");
        public static String CARTON_NOT_INSPECTED_MSG = getMessage("CARTON_NOT_INSPECTED_MSG");
	public static String INVALID_CARTON_MSG = getMessage("INVALID_CARTON_MSG");
	public static String CRITERIA_FAIL_MSG = getMessage("CRITERIA_FAIL_MSG");
	public static String INVALID_BUTTON = getMessage("INVALID_BUTTON");
	public static String INIT_STATE_ERROR_MSG = getMessage("INIT_STATE_ERROR_MSG");
	public static String REMOVE_CARTON_MSG = getMessage("REMOVE_CARTON_MSG");
	public static String OTHER_BAY_CARTON_MSG = getMessage("OTHER_BAY_CARTON_MSG");
	public static String NO_PALLET_FOR_CARTON_MSG = getMessage("NO_PALLET_FOR_CARTON_MSG");
	public static String CARTON_OF_OTHER_PALLET = getMessage("CARTON_OF_OTHER_PALLET");
	public static String NO_SAMPLE_CARTON_ON_PALLET = getMessage("NO_SAMPLE_CARTON_ON_PALLET");
	public static String USER_REJECTED_RTB_MSG = getMessage("USER_REJECTED_RTB_MSG");
	public static String SAMPLE_NOT_INSPECTED_MSG = getMessage("SAMPLE_NOT_INSPECTED_MSG");
	public static String RTB_PROMPT = getMessage("RTB_PROMPT");
	public static String INSPECT_CTN_OTHER_PLT = getMessage("INSPECT_CTN_OTHER_PLT");
	public static String SCRAPPED_MSG = getMessage("CARTON_SCRAPPED");
	
	public static String SCAN_CARTON_FROM_PALLET_MSG = getMessage("SCAN_CARTON_FROM_PALLET_MSG");
	public static String INCOMPLETE_PALLET = getMessage("INCOMPLETE_PALLET");
	public static String UNKNOWN_STATE = getMessage("UNKNOWN_STATE");
	
   //------------------------
   //WARNING & INFO MESSAGES
   //------------------------		          
	public static String TOO_MANY_CARTONS_MSG = getMessage("TOO_MANY_CARTONS_MSG");
	public static String TOO_FEW_CARTONS_MSG = getMessage("TOO_FEW_CARTONS_MSG");
	public static String PARTIAL_PALLET_PROMPT = getMessage("PARTIAL_PALLET_PROMPT");
	public static String TRANSFER_PROMPT = getMessage("TRANSFER_PROMPT");
	public static String NO_PALLET_IN_BAY_MSG = getMessage("NO_PALLET_IN_BAY_MSG");
	public static String PALLET_ALREADY_INSPECTED_MSG = getMessage("PALLET_ALREADY_INSPECTED_MSG");
	public static String PALLET_BEING_INSPECTED_MSG = getMessage("PALLET_BEING_INSPECTED_MSG");
	public static String PALLET_OF_RTB_CARTON_BEING_INSPECTED_MSG = getMessage("PALLET_OF_RTB_CARTON_BEING_INSPECTED_MSG");
	public static String FAILED_FULL_PALLET_MSG = getMessage("FAILED_FULL_PALLET_MSG");
        public static String PALLET_ON_CONSIGNMENT_OR_LOAD_MSG = getMessage("PALLET_ON_CONSIGNMENT_OR_LOAD");
	public static String IS_NOT_INSPECTION_CARTON_MSG = getMessage("IS_NOT_INSPECTION_CARTON_MSG");
	public static String CARTON_NOT_LINKED_TO_BAY_MSG = getMessage("CARTON_NOT_LINKED_TO_BAY_MSG");
	public static String PALLET_COMPLETE_NOT_APPLICABLE_MSG = getMessage("PALLET_COMPLETE_NOT_APPLICABLE_MSG");
	public static String PALLET_NOT_INSPECTED_MSG = getMessage("PALLET_NOT_INSPECTED_MSG");
	public static String NO_SAMPLE_REQUIRED_MSG = getMessage("NO_SAMPLE_REQUIRED_MSG");
	public static String CARTON_TRANSFERRED_MSG = getMessage("CARTON_TRANSFERRED_MSG");
	public static String SCAN_INSPECTION_CTN_PROMPT = getMessage("SCAN_INSPECTION_CTN_PROMPT");
	
	//-----------------
	//message constants
	//-----------------
	
	//-------------------------
	//MEMBER VARIABLES
	//--------------------------
	//public BinTippingState active_state;
	
	
	public MessageInterface ServerConsole;
	
	DeviceCommands.ROBOT_KEYPAD button_pressed;
	
	public PalletizingState active_state;
	
	public Bay parent_bay;
	
        public String provided_screen = null;
	
	private static String getMessage(String message)
	{
	   return (String)messages.get (message);
	}
	
	public static String createErrorRobotScreen(String LCD1,String LCD2)
	{
		
		return String.format(PalletizingAction.ROBOT_SCREEN,false,true,false,LCD1,LCD2);
		
	}
	
	public static String createWarningRobotScreen(String LCD1,String LCD2)
	{
		
		return String.format(PalletizingAction.ROBOT_SCREEN,true,false,false,LCD1,LCD2);
		
	}
	
	public static String createNormalRobotScreen(String LCD1,String LCD2)
	{
		
		return String.format(PalletizingAction.ROBOT_SCREEN,false,false,true,LCD1,LCD2);
		
	}
	
	
	/**
	 * Creates a new instance of PalletizingAction
	 */
	
	
	public PalletizingAction(MessageInterface msg)
	{
		super(msg);
		//System.out.println("in PalletizingAction new ");
		
	}
        
        public PalletizingAction()
	{
		
		
	}


	
	

	public static boolean isBayEmpty(String skip_ip,String bay_num) throws Exception
	{
		
		return (PalletisingDAO.getBayCartonCount(skip_ip,bay_num).equals(0));
	}
	
	
    private boolean isStateRestorationNeeded(String bay_num,String skip_ip,Long carton_number,DeviceCommands.ROBOT_KEYPAD button) throws Exception
	{
		Bay bay = (Bay)MidwareCache.getDevicesCache().getDeviceState(skip_ip,bay_num);
		boolean isEmptyBay = false;
		if(bay == null)
			return ! isBayEmpty(skip_ip,bay_num);
		else
		{
		    if(bay.getActive_bay_transaction() != null && bay.getActive_bay_transaction() instanceof UnknownState)
				return true;
			else
				return false;
		}
	}
	
		
	
	//----------------------------------------------------------------------------------------------------------
	// This method tries to determine, which state need to be restored, after having determined that we need to
	// restore state (because of unexpected memory loss in app's process space)
	// POSSIBLE ANSWERS CAN ONLY BE:
	// 1) Pallet Building state
	// 2) Mixed Pallet building state
	//
	//NOTE: If the scanned-in carton is invalid(doesn't exist in db),the 'RemoveForUnknownState' is returned:
	//      this transaction will (as all removeFor<State>  family members) prompt the user to remove the carton, and
	//      once confirmed by user, will transition to a 'null' state
	//----------------------------------------------------------------------------------------------------------
	
	public  PalletizingState calculateUnknownState(String bay_num,String skip_ip,Long carton_number,DeviceCommands.ROBOT_KEYPAD button) throws Exception
	{
		try
		{
			
			boolean isRemoveCartonScenario =false;
			Carton carton = ProductLabelingDAO.getCarton(carton_number);
			Pallet pallet = PalletisingDAO.getBayPallet(bay_num,skip_ip);
			
			if(pallet == null)
					throw new Exception("The bay(" + bay_num + ") is not empty, but no pallet could be found on the bay!");
			
			if(carton == null)
				return new RemoveForUnknownState(button,carton_number,skip_ip,bay_num,this,null);
			else if(pallet.getProcess_status().toUpperCase().equals("PALLETIZING_MIXED"))
				return new MixedPalletBuildingState(button,carton_number,skip_ip,bay_num,this);
			else
			    return new PalletBuildingState(button,carton_number,skip_ip,bay_num,this);
			
				
		} catch (Exception ex)
		{
			throw new Exception("Bay state could not be populated. Reported exception: " + ex.toString());
		}
				
			
	}
	
	
	//----------------------------------------------------------------------------------------------------------
	// This method tries to determine, which state need to be restored, after having determined that we need to
	// restore state (because of unexpected memory loss in app's process space)
	//This version of the method tries to determine the lost state if a carton number is not present
	//
	//-----------------------------------------------------------------------------------------------------------
	public  PalletizingState calculateUnknownState(String bay_num,String skip_ip,DeviceCommands.ROBOT_KEYPAD button) throws Exception
	{
		try
		{
			
			boolean isRemoveCartonScenario =false;
			Pallet pallet = PalletisingDAO.getBayPallet(bay_num,skip_ip);
			
			if(pallet == null)
					throw new Exception("The bay(" + bay_num + ") is not empty, but no pallet could be found on the bay!");
			
			else if(pallet.getProcess_status().toUpperCase().equals("PALLETIZING_MIXED"))
				return new MixedPalletBuildingState(button,null,skip_ip,bay_num,this);
			else
			    return new PalletBuildingState(button,null,skip_ip,bay_num,this);
			
				
		} catch (Exception ex)
		{
			throw new Exception("Bay state could not be populated. Reported exception: " + ex.toString());
		}
				
			
	}
	
	
	//----------------------------------------------------------------------------------------------------------
	// This method clears the active bay state(remove from memory and then recalculated the underlying
	// state of the bay. So this method will cancel any transaction that is in progress
	//-----------------------------------------------------------------------------------------------------------
	public  String refreshBay(String bay_num,String skip_ip,DeviceCommands.ROBOT_KEYPAD button) throws Exception
	{
		//try
		//{
			
			MidwareCache.getDevicesCache().clearDeviceState(skip_ip,bay_num);;
			Bay bay = new Bay();
			bay.setBay_code(bay_num);
			bay.setSkip_code(skip_ip);
			bay.setId(getBayId(bay));
			 
			Pallet pallet = PalletisingDAO.getBayPallet(bay_num,skip_ip);
			PalletizingState active_state = null;
			
			if(pallet == null)
				active_state = new PreInitialState(button,null,skip_ip,bay_num,this);	
			else
			{
				bay = populateBay(bay_num,skip_ip);
				if(pallet.getProcess_status().toUpperCase().equals("PALLETIZING_MIXED"))
					active_state = new MixedPalletBuildingState(button,null,skip_ip,bay_num,this);
				else
					active_state = new PalletBuildingState(button,null,skip_ip,bay_num,this);
				
			}
			
			bay.setActive_bay_transaction(active_state);
			MidwareCache.getDevicesCache().setDeviceState(skip_ip,bay_num,bay);
			return active_state.getRobotScreen();
			
		//} catch (Exception ex)
		//{
		//	throw new Exception("Bay state could not be populated. Reported exception: " + ex.toString());
		//}
				
			
	}
	
	//------------------------------------------------------------------------------------------
	//This version of populate bay, tries to restore state, if the carton_id is unknown, e.g.
	//if state loss took place and the user presses a button
	//------------------------------------------------------------------------------------------
	public Bay populateBay(String bay_num,String skip_ip) throws Exception
	{
		try
		{
		
		
			boolean isEmptyBay = PalletizingAction.isBayEmpty(skip_ip,bay_num);
			if(isEmptyBay)
				return null;
			
			Bay bay = PalletisingDAO.getBay(bay_num,skip_ip);
			
			if(bay == null);
				//throw new PalletizingException("BAY NOT FOUND IN DB","A bay record could not be found for the provided  skip and bay numbers",
				//                            this.midware_console,skip_ip,bay_num,"not provided","?");
			
			
			
			Pallet pallet = PalletisingDAO.getBayPallet(bay_num,skip_ip);
			if(pallet == null);
				//throw new PalletizingException("NO PALLET ON BAY","The bay(" + bay_num + ") is not empty, but no pallet could be found on the bay!",
									//	this.midware_console,skip_ip,bay_num," not provided","?");
			bay.setState_pallet(pallet);
			
            
                        Carton carton_setup = null;
                        Pallet pallet_template = null;
                        
                       // if(pallet.getCarton_setup_id()!= null)
                       // {
                       //     carton_setup = ProductLabelingDAO.getCartonSetup(pallet.getCarton_setup_id());
                       // }
                        if(pallet.getPallet_template_id() != null)
                        {
                             pallet_template = ProductLabelingDAO.getPalletTemplate(pallet.getPallet_template_id());
			     carton_setup = ProductLabelingDAO.getCartonSetup(pallet_template.getCarton_setup_id());
                        }
                        else
                        {
                            Carton temp_carton = ProductLabelingDAO.getOldestPalletCarton(pallet.getId());
                            if(temp_carton == null);
                               // throw new PalletizingException("OLDEST CARTON NOT FOUND","OLDEST CARTON NOT FOUND",
				//						this.midware_console,skip_ip,bay_num," oldest carton not found","?");
                              carton_setup = ProductLabelingDAO.getCartonSetup(temp_carton.getCarton_setup_id());
                            
                        }
                        
                        
			if(carton_setup == null);
			// throw new PalletizingException("CARTON SETUP MISSING","A carton setup with id " +  pallet_template.getCarton_setup_id().toString() + " could not be found.",
			//	                            this.midware_console,skip_ip,bay_num,"not provided","?");
			
			bay.setState_carton_setup(carton_setup);
			ProductionRun run = ProductLabelingDAO.getProductionRun(pallet.getProduction_run_id());
			
			
			bay.setState_run(run);
			
			
				
			Integer cpp = PalletisingDAO.getCartonsPerPallet(carton_setup.getId());
			if(cpp == null)
					throw new Exception("Cartons per pallet could not be determined for carton setup id " +carton_setup.getId().toString());
			
			bay.setState_cpp(cpp);
			
			PalletizingCriteria criteria = null;
			
			if(pallet != null && pallet.getProcess_status() != null && pallet.getProcess_status().toUpperCase().equals("PALLETIZING_MIXED"))
			
				criteria = PalletisingDAO.getMixedPalletCriteria(pallet.getId());
				
			
			if(criteria == null)
			{
				criteria = PalletisingDAO.getRelevantPalletizingCriteria(carton_setup.getId(),run.getId(),run.getProduction_schedule_id());
						
			    if(criteria == null);
					//throw new PalletizingException("CRITERIA MISSING","Relevant palletizing criteria could not be found for carton setup id " +carton_setup.getId().toString() +
					//	                " and run: " + String.valueOf(run.getId()) + " and schedule: " + String.valueOf(run.getProduction_schedule_id()),
				       //                     this.midware_console,skip_ip,bay_num,"not provided","?");
				
			}
			
			bay.setState_criteria(criteria);
			
			FgSetup fg_setup = PalletisingDAO.getFgSetup(carton_setup.getId());
			 if(fg_setup == null);
					//throw new PalletizingException("FG SETUP MISSING","Relevant FG Setup could not be found for carton setup id " +carton_setup.getId().toString() +
					//	                " and run: " + String.valueOf(run.getId()) + " and schedule: " + String.valueOf(run.getProduction_schedule_id()),
				        //                    this.midware_console,skip_ip,bay_num,"not provided","?");
			
			bay.setState_Fg_Setup(fg_setup);
		
			bay.setPopulated(true);
			MidwareCache.getDevicesCache().setDeviceState(skip_ip,bay_num,bay);
			this.parent_bay = bay;
			
			
			return bay;
		} catch (Exception ex)
		{
			throw new Exception("Bay state could not be populated. Reported exception: " + ex.toString());
		}
		
		
	}
	
	
	//------------------------------------------------------------------------------------------
	//This version of populate bay, tries to restore state, if the carton_id is unknown, e.g.
	//if state loss took place and the user presses a button
	//------------------------------------------------------------------------------------------
	public Bay rePopulateBay(String bay_num,String skip_ip,Pallet pallet) throws Exception
	{
		//try
		//{
		
		
			boolean isEmptyBay = PalletizingAction.isBayEmpty(skip_ip,bay_num);
			if(isEmptyBay)
				return null;
			
			Bay bay = PalletisingDAO.getBay(bay_num,skip_ip);
			
			if(bay == null);
				//throw new PalletizingException("BAY NOT FOUND IN DB","A bay record could not be found for the provided  skip and bay numbers",
				//                            this.midware_console,skip_ip,bay_num,"not provided","?");
			

			if(pallet == null);
				//throw new PalletizingException("NO PALLET PROVIDED","NO PALLET PROVIDED TO REPOPULATE BAY!",
				//						this.midware_console,skip_ip,bay_num," not provided","?");
			bay.setState_pallet(pallet);
			
                        
                        //-----------------------------------------------------------------------------------------------
                        //Get the carton setup: carton setup should be obtained from the pallet_template on pallet, but
                        //reworked pallets does not have a pallet template available: in this case, the carton setup must
                        //be obtained from the oldest carton on the pallet
                        //------------------------------------------------------------------------------------------------
			Carton carton_setup = null;
                        Pallet pallet_template = null;
                        
                        if(pallet.getCarton_setup_id()!= null)
                        {
                            carton_setup = ProductLabelingDAO.getCartonSetup(pallet.getCarton_setup_id());
                        }
                        else if(pallet.getPallet_template_id() != null)
                        {
                             pallet_template = ProductLabelingDAO.getPalletTemplate(pallet.getPallet_template_id());
			     carton_setup = ProductLabelingDAO.getCartonSetup(pallet_template.getCarton_setup_id());
                        }
                        else
                        {
                            Carton temp_carton = ProductLabelingDAO.getOldestPalletCarton(pallet.getId());
                            if(temp_carton == null)
                                throw new PalletizingException("OLDST CTN NOT FND","OLDEST CARTON NOT FOUND",
										this.midware_console,skip_ip,bay_num," oldest carton not found","?");
                              carton_setup = ProductLabelingDAO.getCartonSetup(temp_carton.getCarton_setup_id());
                            
                        }
                        
			if(carton_setup == null)
			 //throw new PalletizingException("CARTON SETUP MISSING","A carton setup with id " +  pallet_template.getCarton_setup_id().toString() + " could not be found.",
				                          //  this.midware_console,skip_ip,bay_num,"not provided","?");
			
			bay.setState_carton_setup(carton_setup);
			ProductionRun run = ProductLabelingDAO.getProductionRun(pallet.getProduction_run_id());
			
			
			bay.setState_run(run);
			
			
				
			Integer cpp = PalletisingDAO.getCartonsPerPallet(carton_setup.getId());
			if(cpp == null)
					throw new Exception("Cartons per pallet could not be determined for carton setup id " +carton_setup.getId().toString());
			
			bay.setState_cpp(cpp);
			
			PalletizingCriteria criteria = null;
			
			if(pallet != null && pallet.getProcess_status() != null && pallet.getProcess_status().toUpperCase().equals("PALLETIZING_MIXED"))
			
				criteria = PalletisingDAO.getMixedPalletCriteria(pallet.getId());
				
			
			if(criteria == null)
			{
				criteria = PalletisingDAO.getRelevantPalletizingCriteria(carton_setup.getId(),run.getId(),run.getProduction_schedule_id());
						
			    if(criteria == null)
					throw new PalletizingException("CRITERIA MISSING","Relevant palletizing criteria could not be found for carton setup id " +carton_setup.getId().toString() + 
						                " and run: " + String.valueOf(run.getId()) + " and schedule: " + String.valueOf(run.getProduction_schedule_id()),
				                            this.midware_console,skip_ip,bay_num,"not provided","?");
				
			}
			
			bay.setState_criteria(criteria);
			
			FgSetup fg_setup = PalletisingDAO.getFgSetup(carton_setup.getId());
			 if(fg_setup == null);
					//throw new PalletizingException("FG SETUP MISSING","Relevant FG Setup could not be found for carton setup id " +carton_setup.getId().toString() +
					//	                " and run: " + String.valueOf(run.getId()) + " and schedule: " + String.valueOf(run.getProduction_schedule_id()),
				        //                    this.midware_console,skip_ip,bay_num,"not provided","?");
			
			bay.setState_Fg_Setup(fg_setup);
		
			bay.setPopulated(true);
			MidwareCache.getDevicesCache().setDeviceState(skip_ip,bay_num,bay);
			this.parent_bay = bay;
			
			
			return bay;
		//} catch (Exception ex)
		//{
		//	throw new Exception("Bay state could not be populated. Reported exception: " + ex.toString());
		//}
				
	}

	//========================================================================================
	//The entry conditions for this method is:
	//-> A valid carton was scanned
	//-> The in-memory bay is empty, i.e. 
	//   1) a new pallet is to be created 
	//   2) a return to bay action is being performed
	//   3) only the in-memory bay object is empty, due to unexpected descruction of this app's process space
	//NB: THE PURPOSE OF THIS METHOD IS PURELY TO POPULATE TYHE BAY WITH ALL KINDS OF STATE
	//    THAT WILL BE NEEDED BY EVERY SUBSEQUENT PALLETIZING OPERATION
	//=========================================================================================
	public Bay populateBay(Carton carton,String bay_num,String skip_ip) throws Exception
	{
		try
		{
		
		
			boolean isEmptyBay = PalletizingAction.isBayEmpty(skip_ip,bay_num);
			
			Bay bay = PalletisingDAO.getBay(bay_num,skip_ip);
			
			if(bay == null);
				//throw new PalletizingException("BAY NOT FOUND IN DB","A bay record could not be found for the provided  skip and bay numbers",
				//                            this.midware_console,skip_ip,bay_num,carton.getCarton_number().toString(),"?");
			
			
			Carton carton_template = ProductLabelingDAO.getCartonTemplate(carton.getCarton_template_id());
			if(carton_template == null);
			// throw new PalletizingException("CARTON TEMPLATE MISSING","A carton template with id " + carton.getCarton_template_id().toString() + " could not be found",
			//	                            this.midware_console,skip_ip,bay_num,carton.getCarton_number().toString(),"?");
			
			Carton carton_setup = ProductLabelingDAO.getCartonSetup(carton_template.getCarton_setup_id());
                        carton.setCarton_setup_id(carton_setup.getId());
			if(carton_setup == null);
			 //throw new PalletizingException("CARTON SETUP MISSING","A carton setup with id " +  carton_template.getCarton_setup_id().toString() + " could not be found(template id is " + carton_template.getId().toString(),
			//	                            this.midware_console,skip_ip,bay_num,carton.getCarton_number().toString(),"?");
			
			bay.setState_carton_setup(carton_setup);
			ProductionRun run = ProductLabelingDAO.getProductionRun(carton.getProduction_run_id());
			
			
			bay.setState_run(run);
			
			//Try to get the pallet if it exists, else get the pallet_template
			
			Pallet pallet = null;
			if(isEmptyBay)
			{
				Pallet pallet_template = PalletisingDAO.getPalletTemplateForCartonSetup(carton_setup.getId());
				if(pallet_template == null);
					//throw new PalletizingException("PLLT TEMPLATE NOT FOUND","A pallet template for carton setup id " + carton_setup.getId().toString() + " could not be found",
				          //                  this.midware_console,skip_ip,bay_num,carton.getCarton_number().toString(),"?");
				bay.setStatePallet_template(pallet_template);
				
			}
			else
			{
				pallet = PalletisingDAO.getBayPallet(bay_num,skip_ip);
				if(pallet == null);
					//throw new PalletizingException("NO PALLET ON BAY","The bay(" + bay_num + ") is not empty, but no pallet could be found on the bay!",
				                       //     this.midware_console,skip_ip,bay_num,carton.getCarton_number().toString(),"?");
				bay.setState_pallet(pallet);
			}
			
			
			Pallet pallet_setup = PalletisingDAO.getPalletSetup(carton_template.getId());
			bay.setState_pallet_setup(pallet_setup);
			if(pallet_setup == null);
				//	throw new PalletizingException("MISSING CARTON SETUP","A pallet setup for carton template id " +carton_template.getId().toString() + " could not be found",
				//                            this.midware_console,skip_ip,bay_num,carton.getCarton_number().toString(),"?");
			
			Integer cpp = PalletisingDAO.getCartonsPerPallet(carton_setup.getId());
			if(cpp == null);
				//	throw new Exception("Cartons per pallet could not be determined for carton setup id " +carton_setup.getId().toString());
			
			bay.setState_cpp(cpp);
			
			PalletizingCriteria criteria = null;
			
			if(pallet != null && pallet.getProcess_status() != null && pallet.getProcess_status().toUpperCase().equals("PALLETIZING_MIXED"))
			
				criteria = PalletisingDAO.getMixedPalletCriteria(pallet.getId());
				
			
			if(criteria == null)
			{
				criteria = PalletisingDAO.getRelevantPalletizingCriteria(carton_setup.getId(),run.getId(),run.getProduction_schedule_id());
						
			    if(criteria == null);
				//	throw new PalletizingException("CRITERIA MISSING","Relevant palletizing criteria could not be found for carton setup id " +carton_setup.getId().toString() +
					//	                " and run: " + String.valueOf(run.getId()) + " and schedule: " + String.valueOf(run.getProduction_schedule_id()),
				        //                    this.midware_console,skip_ip,bay_num,carton.getCarton_number().toString(),"?");
				
			}
			
			bay.setState_criteria(criteria);
			
			FgSetup fg_setup = PalletisingDAO.getFgSetup(carton_setup.getId());
			 if(fg_setup == null);
					//throw new PalletizingException("FG SETUP MISSING","Relevant FG Setup could not be found for carton setup id " +carton_setup.getId().toString() +
					//	                " and run: " + String.valueOf(run.getId()) + " and schedule: " + String.valueOf(run.getProduction_schedule_id()),
				        //                    this.midware_console,skip_ip,bay_num,carton.getCarton_number().toString(),"?");
			
			bay.setState_Fg_Setup(fg_setup);
		
			bay.setPopulated(true);
			MidwareCache.getDevicesCache().setDeviceState(skip_ip,bay_num,bay);
			this.parent_bay = bay;
			
			
			return bay;
		} catch (Exception ex)
		{
			throw new Exception("Bay state could not be populated. Reported exception: " + ex.toString());
		}
		
		
	}
	
	
	public void affirmRobotScreen(Bay bay) throws Exception
	{
		
		if(this.active_state== null)
			throw new Exception("No active transaction defined to get a screen from");
		
		String screen = this.active_state.getRobotScreen();
		if(screen == null||screen.trim().equals("")||screen.indexOf("<Palletize")== -1)
			throw new Exception("No robot screen defined for the active transaction");
		
		 //see if a special message was sent by someone- if so display on LCD line 2

		 if(bay != null)
		 {
			 if(bay.getState_special_instruction()!= null && ! bay.getState_special_instruction().trim().equals(""))
			 {
				 String special_msg = bay.getState_special_instruction();
				 int start_pos = screen.indexOf("LCD2");
				 if(start_pos  > -1)
				 {
					String screen_left = screen.substring(0,start_pos);
					screen = screen_left + "lCD2=\"" + special_msg + "\" />";
					this.active_state.robot_screen = screen;
					bay.setState_special_instruction(null);
				 }
				 
			 }
		 }
		
			
		
	}
	
	
	public Integer getBayId(Bay bay) throws Exception
	{
		if(bay.getId()== null)
		{
			Bay bay_in_db = PalletisingDAO.getBay(bay.getBay_code(),bay.getSkip_code());
			bay.setId(bay_in_db.getId());
		}
		
		return bay.getId();
		
	}
	
	//=================================================================================================
	//This method- the main controller of the transaction is reasonably complex, because it has to deal
	//with potential loss of state from memory and try to recover from it. The basic algorythm, however, is:
	//1) Get the active bay state or try to recover or reproduce it from database tables
	//2) call the appropriate transactional method on the active transaction- in some cases e.g. initial state
	//   or RTB state, this method creates a the new state and transaction itself
	//3) On return of the call made to active transaction, ask it for it's robot screen
	//4) validate the screen
	//5) return the screen
	//=================================================================================================
	public String process_action(String skip_ip,String bay_num,ROBOT_KEYPAD button_pressed,String carton_scanned)
	{
		 
		try
		{
			
		   if(PalletizingAction.messages == null)
			  throw new Exception("Palletizing messages is null. Midware config propably was not loaded correctly. See error log for details");
		   
		   
		   //------------------------------
		   //handle refresh command if sent
		   //------------------------------
		   if(button_pressed == DeviceCommands.ROBOT_KEYPAD.btn5)
		   {
			   
				//System.out.println("REFRESH CALLED");
				return refreshBay(bay_num,skip_ip,button_pressed); //robot screen is returned
			  
		   }
		   
			Long carton_num = null;
			if(!(carton_scanned.trim().equals("")))
				
				try
				{
					carton_num = Long.parseLong(carton_scanned);
				} catch (NumberFormatException ex)
				{
				   //DeviceScan.handle_exception(this.midware_console,"Palletizing action could not be processed(skip ip: " + skip_ip + ", bay number: " + bay_num + ", carton scanned: " + carton_scanned + ", button pressed: " + button_pressed.toString() + ") .Reported exception: " ,
				   //                    ex.toString(),"PalletizingAction.ProcessAction",DeviceTypes.PALLETISING,0,"",ex.getStackTrace(),skip_ip);
                                    //throw ex;
                                    System.out.println(ex.getMessage());
                                    ex.printStackTrace();
				  return createErrorRobotScreen("INVALID BARCODE ","TRY TO RE-SCAN CTN");
					
				}
			
			Bay bay = (Bay)MidwareCache.getDevicesCache().getDeviceState(skip_ip,bay_num);
			
			if(bay == null||(bay != null && ! bay.isPopulated()))
                        {
				if(button_pressed == DeviceCommands.ROBOT_KEYPAD.none)
				{
					if(isStateRestorationNeeded(bay_num,skip_ip,carton_num,button_pressed))
					{
						this.active_state = calculateUnknownState(bay_num,skip_ip,carton_num,button_pressed);
						if(!(this.active_state instanceof ConfirmRemoveState))
						{
							Carton carton = ProductLabelingDAO.getCarton(carton_num); //carton guarenteed to exist if we reach this point

							bay = populateBay(bay_num,skip_ip); //we have to use this version, because the scanned-in carton
							                                    //is likely not the carton needed to re-populate the bay
						}
					}
					else ////Bay is null or unpopulated, we had no accidental state loss and a scan took place
					{
						//-----------------------------------------------------------------------------------------------------------------
						//At this point 2 states can be possible: 1) Return to bay 2) Initialstate- if active transaction, set during
						//previous rounddtrip, is RTB, the RTB need to remain as is. Else this scan can only be the first scan of a carton
						//on a new bay (i.e. InitialState)
						//----------------------------------------------------------------------------------------------------------------
						if(bay == null||!(((bay.getActive_bay_transaction()!=null &&!( bay.getActive_bay_transaction() instanceof ConfirmTransferState) && bay.getActive_bay_transaction() instanceof ReturnToBayState))))
							this.active_state = new InitialState(button_pressed,carton_num,skip_ip,bay_num,this); //first carton on new pallet
					  
					}
				}
	
				else //Bay is null and button was pressed
				{
				  //determine if we need state restoration
				   if(isStateRestorationNeeded(bay_num,skip_ip,null,button_pressed))	
				   {
					   this.populateBay(bay_num,skip_ip);
					    this.active_state = calculateUnknownState(bay_num,skip_ip,button_pressed);
				   }
				   else
				   {
					   //------------------------------------------------------------------------------------------------------
					   //Within this branch the only valid state can be return_to_bay
					   //bay doesn'nt have to be empty to reach this block- it can also be 'unpopulated', i.e. have no state'
					   //-------------------------------------------------------------------------------------------------------
					   if(bay != null && bay.getActive_bay_transaction()!= null && (bay.getActive_bay_transaction() instanceof ReturnToBayState||bay.getActive_bay_transaction() instanceof ConfirmRemoveState||bay.getActive_bay_transaction() instanceof ConfirmTransferState))
						   ; //do nothing fine as is
					   else if(button_pressed == DeviceCommands.ROBOT_KEYPAD.btn1) //RTB is the only valid transaction here
						  this.active_state = new ReturnToBayState(button_pressed,carton_num,skip_ip,bay_num,this);
					   else
						  //this.active_state = new WrongButtonState(button_pressed,carton_num,skip_ip,bay_num,this);
				          return this.createWarningRobotScreen(this.INVALID_BUTTON,"");
				   }
				
				}
			}
			
			//set(for convenience)this.active_state
			if(bay!= null && bay.getActive_bay_transaction()!= null)
				this.active_state = bay.getActive_bay_transaction();
			
                        //----------------------------------------------------------------------------------------------
			//Work around for rare bug that occurs:
                        //if, for some reason, active state is null, determine what it should be and create/re-create it
                        //-----------------------------------------------------------------------------------------------
                     
                        if(this.active_state == null)
                        {
                          //first see esCache().getDeviceState(skip_ip,bay_num);if active state is in memory
                          bay = (Bay)MidwareCache.getDevicesCache().getDeviceState(skip_ip,bay_num);
			  if(bay != null)
			  {
				this.active_state = bay.getActive_bay_transaction();

			  }
                          //recreate state- it's not in memory'
                          if(this.active_state == null)
                             this.refreshBay(bay_num,skip_ip,button_pressed); //will create bay, with correct state
                          
                             this.active_state = ((Bay)MidwareCache.getDevicesCache().getDeviceState(skip_ip,bay_num)).getActive_bay_transaction();
                        }
                        
                        
			 this.active_state.transaction_cycle ++;
			 this.active_state.update_command_state(button_pressed,carton_num, skip_ip,bay_num,this);
			 
			 
			 if(button_pressed != DeviceCommands.ROBOT_KEYPAD.none)	
				 this.active_state.button_pressed();
			 else
				 this.active_state.carton_scanned();
			
			 //re-retrieve the bay from the cache as this is the single global acces point
			 //(state classes retreived or created within this method will have used that single
			 // access point to store state)
			  bay = (Bay)MidwareCache.getDevicesCache().getDeviceState(skip_ip,bay_num);
			  if(bay == null)
			  {
				bay = new Bay();
				bay.setBay_code(bay_num);
				bay.setSkip_code(skip_ip);
				bay.setId(getBayId(bay));

			  }
			
                         if(this.active_state == null) //pallet complete will have set it to null
                         {
                             this.refreshBay(bay_num,skip_ip,button_pressed); //will create bay, with correct state
                          
                             this.active_state = ((Bay)MidwareCache.getDevicesCache().getDeviceState(skip_ip,bay_num)).getActive_bay_transaction();
                         }
                             
                             
			 //store the active transaction on the bay, and then store bay in memory, if the bay is null
			 //(first retry to get the bay from the cache- the active transaction could since have stored the bay there)
			
			 
			 bay.setActive_bay_transaction(this.active_state); //at very least store the active transaction
			 MidwareCache.getDevicesCache().setDeviceState(skip_ip,bay_num,bay);
			 
			  this.affirmRobotScreen(bay); //basic validity checks + insertion of special message if sent by someone
			 
			 
                          
		         String robot_screen = this.active_state.getRobotScreen();
                         if(this.provided_screen != null) // a way to override the normal screen of an active 
                           robot_screen = this.provided_screen;
			 //-------------------------------------------------------------------------------------------------------------
			 //the 'is_error_screen' property' is used by states to denote that their normal screens are
			 //not active, but some error screen. This property needs to be reset after the screen is returned
			 //to device, since this property must never live across roundtrips. It's real use is to allow states
			 //to not apply their normal screen building stuff on the 'getRobotScreen' method. A given state can never know
			 //exactly where or when or why they had to produce am 'abnormal' screen.They simply need to know that
			 //it occurred and they only need to know it for the current server transaction
			 //------------------------------------------------------------------------------------------------------------
			 if(this.active_state != null)
			   ;//System.out.println("Active State is: " + this.active_state.getClass().getName().toString());
			 else
			  ;// System.out.println("Active state: None");
				
			 this.active_state.is_error_screen = false;
			 return robot_screen;
			 
			
		} catch (Exception ex)
		{
			
		    String err = "contact IT";
			if(ex instanceof PalletizingException)
			{
				err = ((PalletizingException)ex).short_msg;
			}
			//DeviceScan.handle_exception(this.midware_console,"Palletizing action could not be processed(skip ip: " + skip_ip + ", bay number: " + bay_num + ", carton scanned: " + carton_scanned + ", button pressed: " + button_pressed.toString() + ") .Reported exception: " ,
			//	                       ex.toString(),"PalletizingAction.ProcessAction",DeviceTypes.PALLETISING,0,"",ex.getStackTrace(),skip_ip);
		      System.out.println();
                      ex.printStackTrace();
                    return createErrorRobotScreen("System error occurred",err);
		}
		
		finally
		{
			//System.out.println("in PalletizingAction.process_action FINALLY ");
		}
		
		
	}
	
	
	
}
