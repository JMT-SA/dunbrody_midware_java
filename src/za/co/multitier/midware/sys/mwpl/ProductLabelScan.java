/*
 * ProductLabelScan.java
 *
 * Created on February 6, 2007, 2:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpl;

import java.util.HashMap;
import java.util.Map;

import za.co.multitier.mesware.messages.MessageInterface;

import za.co.multitier.midware.sys.datasource.ActiveRunResource;
import za.co.multitier.midware.sys.protocol.SysProtocol;

import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.appservices.DeviceTypes;
import za.co.multitier.midware.sys.datasource.DevicesDAO;
import za.co.multitier.midware.sys.datasource.*;

/**
 * @author Administrator
 */
public abstract class ProductLabelScan extends DeviceScan {


    public static String LABEL_ERR =
            SysProtocol.TMSG +
                    " Msg=\"ERR: %s\"  Color=\"bold+red\"  Yellow=\"false\" Green=\"false\" Red=\"true\" LCD1=\"%s\" LCD2=\"%s\" LCD3=\"%s\" LCD4=\"\" />";


    public static String LABEL_MSG =
            SysProtocol.TMSG +
                    " Msg=\"%s\"  Color=\"bold+green\" Yellow=\"false\" Green=\"true\" Red=\"false\" LCD1=\"%s\" LCD2=\"%s\" LCD3=\"\" LCD4=\"\" />";


    protected String ip;
    protected String mass;
    protected String[] codeCollection;
    protected String run_number;
    protected String template_name = "DATA-Dunbrody_Zebra.ZPL";
    protected ActiveRunResource active_device = null;
    protected String device_type;
    public int mode;

    protected Map label_data;
    protected String label_message = "";
    protected String robot_button = "NONE";
    protected boolean cancel_data_send; //known error occurred, only send error message- set by subclass's 'seLabelData()' method
    //to alert this class to not send label data

    private PlTransaction pl_transaction;

    protected PlTransaction getPltransaction() {
        return this.pl_transaction;
    }


    private boolean is_static_label()
    {

       String field_val =  this.active_device.getStatic_label();
        if(field_val == null)
            return false ;

       if(!is_alternative_label())     //static can only be alternative label
           return false;

        field_val = field_val.toUpperCase();
        if(field_val.equals("YES")||field_val.equals("Y")||field_val.equals("TRUE")||field_val.equals("T")||field_val.equals("S")||field_val.equals("STATIC"))
         return true;
        else
            return false;

    }



    protected boolean is_alternative_label()
    {
        if(this.robot_button.equals("4"))
            return true;
        else
            return false;


    }

    protected String createLabelErrString(String msg) {
        String e1 = "";
        String e2 = "";
        String e3 = "";

        if (msg.length() >= 90) {
            e1 = msg.substring(0, 29);
            e2 = msg.substring(29, 60);
            e3 = msg.substring(61, 90);

        } else if (msg.length() > 60) {
            e1 = msg.substring(0, 29);
            e2 = msg.substring(30, 60);
            e3 = msg.substring(61, msg.length() - 1);
        } else {
            e1 = msg.substring(0, msg.length());
        }


        return String.format(ProductLabelScan.LABEL_ERR, e1,e1, e2, e3);

    }

    protected String createLabelMsgString(String msg) {
        String e1 = "";
        String e2 = "";
        String e3 = "";

        if (msg.length() >= 90) {
            e1 = msg.substring(0, 29);
            e2 = msg.substring(30, 60);
            e3 = msg.substring(61, 90);

        } else if (msg.length() > 60) {
            e1 = msg.substring(0, 29);
            e2 = msg.substring(30, 60);
            e3 = msg.substring(61, msg.length());
        } else {
            e1 = msg.substring(0, msg.length());
        }


        return String.format(ProductLabelScan.LABEL_MSG, e1, e2, e3);

    }

    public void send_integration_record() throws Exception {

    }

    public void set_transaction(PlTransaction pl_transaction) {
        this.pl_transaction = pl_transaction;
    }

    public ProductLabelScan(String ip, String mass, String codeCollection[], MessageInterface msg, int mode) {
        super(msg);
        this.ip = ip;
        this.mass = mass;
        this.mode = mode;

        if (mass.equals(""))
            this.mass = "0.00";

        this.codeCollection = codeCollection;

        this.label_data = new HashMap();

        String device_class = this.getClass().getName();
        if (device_class.indexOf("CartonLabelScan") > -1)
            this.device_type = DeviceTypes.CARTON_LABELING;


    }

    public ProductLabelScan(String ip, String mass, String codeCollection[], int mode) {

        this.ip = ip;
        this.mass = mass;
        this.mode = mode;

        if (mass.equals(""))
            this.mass = "0.00";

        this.codeCollection = codeCollection;

        this.label_data = new HashMap();

        String device_class = this.getClass().getName();
        if (device_class.indexOf("CartonLabelScan") > -1)
            this.device_type = DeviceTypes.CARTON_LABELING;


    }


    protected boolean isDeviceActive() throws Exception {
        String code = this.codeCollection[0];
        this.active_device = DevicesDAO.getActiveDevice(code);

        //extract robot button
        if (code.substring(code.length() - 2, code.length() - 1).equals("B"))
            robot_button =   code.substring(code.length() - 1, code.length());

        //cater for robot labeling where the pressed button is appended to robot name
        //remove the 'BX' part from code and retry the finding of active device in case users associates
        //same product spec to all buttons on robot
        if (this.active_device == null) {

            if (code.substring(code.length() - 2, code.length() - 1).equals("B")) {
                robot_button =   code.substring(code.length() - 1, code.length());
                code = code.substring(0, code.length() - 2);
                this.active_device = DevicesDAO.getActiveDevice(code);

            }
        }



        if (this.active_device != null)
            this.run_number = active_device.getProduction_run_code();
        else
            this.run_number = "unknown";

        return (active_device != null);


    }


    protected boolean is_robot_button_disabled()
    {
        return false;

//        if(this.robot_button == null||this.robot_button.toUpperCase().equals("NONE"))
//            return false;
//
//        int btn = Integer.valueOf(this.robot_button);
//
//        switch (btn) {
//            case 2:
//            case 3:
//                return true;
//            default:
//                return false;

//        }



    }

    protected String build_label() throws Exception {
        try {
            String message = this.label_message; //set by subclass
            message = message == null ? "OK" : message;
            Map label_data = this.label_data;  //set by subclass
            String template_file_name = this.template_name; //default template

            if(this.is_alternative_label())
            {
                if(this.active_device.getAdditional_template_name() == null||this.active_device.getAdditional_template_name().trim().equals(""))
                    throw new Exception("Button 4 pressed, but no additional template defined for drop: " + this.active_device.getResource_code());

                template_file_name = ProductLabelingDAO.getTemplateFileName(this.active_device.getAdditional_template_name());
                if(template_file_name == null ||template_file_name.trim().equals("") )
                    throw new Exception("Button 4 pressed, but no template file defined for additional template(" + this.active_device.getAdditional_template_name() +
                           "). Drop is:  " + this.active_device.getResource_code());




            }
            else
            {
               if(this.active_device.getTemplate_name() == null||this.active_device.getTemplate_name().trim().equals(""))
                    throw new Exception("No template defined for drop: " + this.active_device.getResource_code());

                template_file_name = ProductLabelingDAO.getTemplateFileName(this.active_device.getTemplate_name());
                if(template_file_name == null ||template_file_name.trim().equals("") )
                    throw new Exception("No template file defined for template(" + this.active_device.getTemplate_name() +
                            "). Drop is:  " + this.active_device.getResource_code());

            }


            StringBuilder label_intruction = new StringBuilder();
            label_intruction.append(SysProtocol.TPRODUCTLABEL + "Status=\"true\" Threading=\"true\" RunNumber=\"");
            label_intruction.append(this.run_number + "\" Code=\"");
            label_intruction.append(this.codeCollection[0] + "\" F0=\"" + template_file_name + "\" ");

            int n_fields = label_data.size();

            if(!this.is_static_label()) {
                for (int i = 1; i <= n_fields; i++) {
                    String key = "F" + new Integer(i).toString();
                    String val = "";
                    if (label_data.get(key) != null)
                        val = label_data.get(key).toString();

                    String field = key + "=\"" + val + "\"";
                    label_intruction.append(field + " ");
                }
            }
//            else if(is_alternative_label())
//                build_alternative_label(label_intruction);

            label_intruction.append("Msg=\"" + message + "\" />");
            return label_intruction.toString();
        } catch (Exception e) {
            throw new Exception("The 'build_label' method failed. Reported exception: " + e.toString());
        }

    }


    protected void build_alternative_label(StringBuilder label_instruction)
    {

    }

    protected abstract String setLabelData() throws Exception;

    protected abstract String post_labeling_transaction() throws Exception;

	
	/*==========================================================================================
	 *This is a template method that controls the server side process of label printing
	 *Label printing sub classes must implement two methods in this algorythm:
	 *1)setLabelData() {fetch data to be printed and create the F1..F(n) series of field-value 
	 *                  pairs as a Map data structure}
	 *2)post_labeling_transaction() {perform any required db transaction on successful labeling}
	 ========================================================================================= */


    public String process_verification_scan() throws Exception

    {
        Long carton_num = Long.valueOf(this.codeCollection[0]);
        Carton label = ProductLabelingDAO.getCartonLabel(carton_num);
        if (label == null)
            return createLabelErrString("Ctn Label: " + this.codeCollection[0] + " not found");
        else {

            if (ProductLabelingDAO.getCarton(carton_num) != null)
                return createLabelErrString("Ctn : " + this.codeCollection[0] + " already verified");
            else {
                ProductLabelingDAO.createCartonFromLabel(label);
                return createLabelMsgString("Carton verified");
            }
        }


    }


    public String processLabelScan() {
        try {


            if (this.mode == CartonLabelScan.MODE_ROBOT_QC_SCANNING)
                return process_verification_scan();


            String err = null;
            if (!isDeviceActive())
                return createLabelErrString("Station: " + this.codeCollection[0] + " not linked");

            if (is_robot_button_disabled())
                return createLabelErrString("Robot button: " + this.robot_button + " is disabled");

            err = this.setLabelData(); //subclass must fetch and set label data in Map format (and store in 'label_data' member variable)


            if (err != null)
                return err;


//			if(this.label_data == null||this.label_data.size() == 0)
//				throw new Exception("Label data was not set by label processing transaction(subclass)");

            String labeling_instruction = this.build_label();

            String line_scan_result = this.post_labeling_transaction(); //subclass given opportunity to perform any further db transactions
            if (this.mode == CartonLabelScan.MODE_ROBOT_SCANNING)
                return labeling_instruction;
            else
                return line_scan_result;

        } catch (Exception ex) {
            System.out.println("Carton scanning exception: " + ex.toString());
            ex.printStackTrace();

            DeviceScan.handle_exception(this.midware_console, "Product Scan exception occurred for ip:" + this.ip + " and station code " + this.codeCollection[0],
                    ex.toString(), this.getClass().getName() + ".processLabelScan()", this.device_type, 0, this.run_number, ex.getStackTrace());


            return this.createLabelErrString(ex.getMessage().toString());
        }

    }


}
