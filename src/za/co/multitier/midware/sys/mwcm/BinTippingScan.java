/*
 * BinTippingScan.java
 *
 * Created on January 30, 2007, 11:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwcm;

import java.util.HashMap;
import za.co.multitier.midware.sys.protocol.SysProtocol;

import za.co.multitier.midware.sys.appservices.*;
import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.midware.sys.datasource.*;


/**
 *
 * @author Administrator
 */
public class BinTippingScan extends DeviceScan {
    
    //-------------------
    //Device instructions
    //-------------------
    
//	private static final String BINTIP_NOT_ACTIVE = SysProtocol.TCONTAINERMOVE +  "Status = \"false\" Red = \"false\"" +
//		                                            " Yellow = \"true\" Green = \"false\"> Device not active </ContainerMove>";
    
    private static final String WRONG_BUTTON = SysProtocol.TCONTAINERMOVE + "Status=\"false\" Red=\"false\"" +
        " Yellow=\"true\" Green=\"false\" Msg=\"wrong button\" LCD1=\"PLEASE SCAN BIN\" LCD2=\" \"    />";

    private static final String BINTIP_NOT_ACTIVE = SysProtocol.TCONTAINERMOVE + "Status=\"false\" Red=\"true\"" +
            " Yellow=\"false\" Green=\"false\" Msg=\"Device not active\" LCD1=\"Device not active\" LCD2=\"%s\"    />";
    
    private static final String BINTIP_ERROR = SysProtocol.TCONTAINERMOVE + "Status=\"false\" Red=\"true\"" +
        " Yellow=\"false\" Green=\"false\" Msg=\"System error occurred\" LCD1=\"System error occurred\" LCD2=\" \" />";
    
    
    private static final String SCREEN_ERROR = SysProtocol.TCONTAINERMOVE + "Status=\"false\" Red=\"true\"" +
        " Yellow=\"false\" Green=\"false\" Msg=\"Screen error occurred\" LCD1=\"Screen error occurred\" LCD2=\" \" />";
    
    private static final String BINTIP_INSTRUCTION = SysProtocol.TCONTAINERMOVE + "Status=\"%s\" RunNumber=\"%s\" Red=\"%s\"" +
        " Yellow=\"%s\" Green=\"%s\" Msg=\"%s\" LCD1=\"%s\" LCD2=\"%s \" />";
    
    //-----------------
    //message constants
    //-----------------
    
    public static  String SCAN_NEXT_BIN_MSG = "SCAN BIN";
    public static  String BIN_NOT_FOUND_MSG = "NOT FND:REQ OV";
    public static  String REMOVE_BIN_MSG = "REMOVE BIN";
    public static  String PRESS_F3_F4_MSG = "F3('Y')/F4('N')";
    public static  String TIPPED_BIN_REQ_AUTH = "TIPD BIN:REQ OV";
    public static  String INVALID_BIN_REQ_AUTH = "PV INV BIN:REQ OV";
    public static  String NO_OVERRIDE_AUTH = "OVERRIDE DENIED";
    public static  String IS_CHILD_RUN = "THIS IS A CHILD RUN";
    
    //-------------------------
    //MEMBER VARIABLES
    //--------------------------
    public BinTippingState active_state;
    public ActiveRunResource active_device;
    public String authoriser_name;
    
    public MessageInterface ServerConsole;
    
    DeviceCommands.TRM_KEYPAD key_pressed;
    
    public boolean HasOverrideAuthorisation = false;
    
    private String getTippedCountForRun(int run_id) throws Exception {

        Integer tipped_count =  BinTippingDAO.getTippedBinsCount(run_id);
        return tipped_count.toString();
    }
    
    
    public String createBintipInstruction(boolean status,boolean red,String run_code,boolean yellow, boolean green,String message)throws Exception {
        //-----------------------------------------------------------------------------------------
        //If the message is the 'SCAN BIN' message(i.e. successfull scan or override took place), we
        //need to extend the message construction in the following way
        // -> get the total tipped count for the run and line and append to 'SCAN BIN MESSAGE'
        // -> replace the run_code with the day_line_batch_code
        //-----------------------------------------------------------------------------------------
        if(run_code == null)
            run_code ="";


        if(message.equals(this.SCAN_NEXT_BIN_MSG)) {
            String day_batch_code = this.active_device.getDay_line_batch_number();
            String tipped_count = getTippedCountForRun(this.active_device.getProduction_run_id());
            String extended_message = message + "(" + tipped_count + " TPD)";
            return String.format(BinTippingScan.BINTIP_INSTRUCTION,status,day_batch_code,red,yellow,green,extended_message,extended_message,run_code);
        } else
            return String.format(BinTippingScan.BINTIP_INSTRUCTION,status,run_code,red,yellow,green,message,message,run_code);
        
    }
    
    public static String createCustomBintipInstruction(boolean status,boolean red,String run_code,boolean yellow, boolean green,String message)throws Exception {
        
        return String.format(BinTippingScan.BINTIP_INSTRUCTION,status,run_code,red,yellow,green,message,message,run_code);
        
    }
    
    /** Creates a new instance of BinTippingScan */
    
    
    public BinTippingScan(MessageInterface msg) {
        super(msg);
    }

      public BinTippingScan() {

    }
    
    
    
    public String scanBin(String ip,String bin_id,DeviceCommands.TRM_KEYPAD key_pressed,ActiveRunResource active_device,String mass) throws Exception {
       // try {
            if(active_state == null)
                this.active_state = new AwaitScanState(ip,bin_id,this);
            else
                this.active_state.bin_id = bin_id;
            
            this.active_device = active_device;
            this.key_pressed = key_pressed;
            //-----------------------------------------------------------------------------------------------------------
            //Set the current bin id: it is possible that an active state object that existed before this server
            //roundtrip had an id and that a different id was now scanned-in, in which case the active state
            //object would never get the new id- because they are assigned during creation time in their constructors
            //Some transactions, such as found in awaitauth state, need both ids
            //---------------------------------------------------------------------------------------------------------
            this.active_state.setCurrent_bin_id(bin_id);
            this.active_state.scanBin(mass);
            if(active_state.device_instruction == null||active_state.device_instruction.trim().equals(""))
                throw new Exception("Bin Scan state: " + active_state.getClass().getName() + " did not set a processing instruction! ");
            else
                return active_state.device_instruction;
        //} catch (Exception ex) {
          //  throw ex;
        //}
        
        
    }
    
    //-------------------------------------------------------------------------------------------
    //This static method is called from the CM midware component's process_transaction
    //It expects a string as the xml processing instruction to be sent to the bintipper
    //This method main purpose is obtain the correct instance of this class- it could have
    //been stored in cache or it must be created for the first time or it may need to be created
    //from disk(in the event of a power failure)
    //-------------------------------------------------------------------------------------------
    public static String scanBin(MessageInterface msg,String ip,String bin_id,DeviceCommands.TRM_KEYPAD key_pressed,String bin_tipper_name,String mass) {
        ActiveRunResource active_device = null;
        String bin_fetch_err = "PLEASE SCAN BIN";
        double real_mass = 0.00;





        try {

            active_device = DevicesDAO.getActiveDevice(bin_tipper_name);

            
            if(active_device == null)
                return String.format(BinTippingScan.BINTIP_NOT_ACTIVE,bin_tipper_name);

             ProductionRun run = (ProductionRun)ProductLabelingDAO.getProductionRun(active_device.getProduction_run_id());

            
            
            //Now try to get an instance from memory
            BinTippingScan bin_scan = (BinTippingScan)BinTippingCache.getDevicesCache().getDeviceState(ip,active_device.getProduction_run_code(),DeviceTypes.BIN_TIPPING);
            if(bin_scan == null)
                bin_scan = new BinTippingScan(msg);
            
            if(key_pressed == DeviceCommands.TRM_KEYPAD.F3||key_pressed == DeviceCommands.TRM_KEYPAD.F4) {
                //In this case no scan took place- we need to get the bin_id from memory
               if(bin_scan.active_state == null)
                   return BinTippingScan.WRONG_BUTTON;
                else
                  bin_id = bin_scan.active_state.bin_id;
            } else
            {
                // HashMap shift = ProductLabelingDAO.getShift(run.getLine_id(),"packer");
                //if(shift == null)
                //    bin_fetch_err = "NO SHIFT FOR LINE: " + run.getLine_code();
                //else
               // {

                    bin_fetch_err = BinTippingDAO.validate(bin_id,active_device.getSetup_detail_id(),run);
                //}
            }
            
            if(bin_fetch_err == null){
                String device_instruction = bin_scan.scanBin(ip,bin_id,key_pressed,active_device,mass);
                BinTippingCache.getDevicesCache().setDeviceState(ip,active_device.getProduction_run_code(),DeviceTypes.BIN_TIPPING,bin_scan);
                return device_instruction;
            }
            else
            {

               String batch_code = "";//active_device == null?" NA":active_device.getDay_line_batch_number().toString();
                return createCustomBintipInstruction(false,true,batch_code,false,false,bin_fetch_err);
            }
                //bin fetch error occurred
            
            
        } catch (Exception ex) {
            String prod_run_code = "NOT KNOWN IN CONTEXT: ACTIVE DEVICE DOES NOT EXIST IN CONTEXT";
            if(active_device != null)
                prod_run_code = active_device.getProduction_run_code();
            
            DeviceScan.handle_exception(msg,"Bin Scan exception occurred for ip:" + ip + " and bin_id: " + bin_id,
                ex.toString(),"BintippingScan.scan_bin",DeviceTypes.BIN_TIPPING,0,prod_run_code,ex.getStackTrace());
            return BINTIP_ERROR;
        }
        //BinTippingScan bin_scan = BinTippingCache.getDevicesCache().getDeviceState(ip,
        
    }


     public static String scanBin(String ip,String bin_id,DeviceCommands.TRM_KEYPAD key_pressed,String bin_tipper_name,String mass) throws Exception {
         ActiveRunResource active_device = null;
        String bin_fetch_err = "";

            active_device = DevicesDAO.getActiveDevice(bin_tipper_name);


            if(active_device == null)
                return String.format(BinTippingScan.BINTIP_NOT_ACTIVE,bin_tipper_name);

             ProductionRun run = (ProductionRun)ProductLabelingDAO.getProductionRun(active_device.getProduction_run_id());


            //Now try to get an instance from memory
            BinTippingScan bin_scan = (BinTippingScan)BinTippingCache.getDevicesCache().getDeviceState(ip,active_device.getProduction_run_code(),DeviceTypes.BIN_TIPPING);
            if(bin_scan == null)
                bin_scan = new BinTippingScan();

            if(key_pressed == DeviceCommands.TRM_KEYPAD.F3||key_pressed == DeviceCommands.TRM_KEYPAD.F4) {
                //In this case no scan took place- we need to get the bin_id from memory

                bin_id = bin_scan.active_state.bin_id;
            } else
            {
                 //HashMap shift = ProductLabelingDAO.getShift(run.getLine_id(), "packer");
                //if(shift == null)
                //    bin_fetch_err = "NO SHIFT FOR LINE: " + run.getLine_code();
                //else
               // {

                    bin_fetch_err = BinTippingDAO.validate(bin_id,active_device.getSetup_detail_id(),run);
                //}
            }

            if(bin_fetch_err == null){
                String device_instruction = bin_scan.scanBin(ip,bin_id,key_pressed,active_device,mass);
                BinTippingCache.getDevicesCache().setDeviceState(ip,active_device.getProduction_run_code(),DeviceTypes.BIN_TIPPING,bin_scan);
                return device_instruction;
            }
            else
            {
               String batch_code = active_device == null?" NA":active_device.getDay_line_batch_number().toString();
                return createCustomBintipInstruction(false,true,batch_code,false,false,bin_fetch_err);
            }
                //bin fetch error occurred



        //BinTippingScan bin_scan = BinTippingCache.getDevicesCache().getDeviceState(ip,

    }
    
    
    
}
