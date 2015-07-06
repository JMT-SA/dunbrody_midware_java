/*
 * CartonLabelScan.java
 *
 * Created on February 6, 2007, 5:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.mwpl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
//import za.co.multitier.mesware.messages.MailInterface;
//import za.co.multitier.mesware.services.gsm.GsmServerInterface;

import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.appservices.MidwareConfig;
import za.co.multitier.midware.sys.datasource.*;

/**
 *
 * @author Administrator
 */
public class CartonLabelScan extends ProductLabelScan {


    public static final int MODE_LINE_SCANNING = 6;
    public static final int MODE_ROBOT_QC_SCANNING = 11;
    public static final int MODE_ROBOT_SCANNING = 12;
    //public static final String R



    /** Creates a new instance of CartonLabelScan */
    public CartonLabelScan(String ip, String mass, String codeCollection[], MessageInterface msg,int mode) {
        super(ip, mass, codeCollection, msg,mode);
    }

    public CartonLabelScan(String ip, String mass, String codeCollection[],int mode) {
        super(ip, mass, codeCollection,mode);
    }



    private String packer;
    private Long carton_num;
    

    private String from_address;
    private String to_address;
    FgSetup fg_setup;
    ProductionRun run;
    String pick_ref;
    String iso_week;
    String  phc;
    String line2;

    protected String setLabelData() throws Exception {



        boolean error = false;

        this.fg_setup = ProductLabelingDAO.getFgSetup(this.active_device.getSetup_detail_id());
        if (this.fg_setup == null) {
            return String.format(ProductLabelScan.LABEL_ERR,"No active FG setup was found for DROP code: " + codeCollection[0],"No active FG setup was found for DROP code: " + codeCollection[0],"No active FG setup was found for DROP code: " + codeCollection[0] );
            //throw new Exception("No active FG setup was found for DROP code: " + codeCollection[0]);


        }



        run = ProductLabelingDAO.getProductionRun(this.active_device.getProduction_run_id());
        if (run == null) {
            throw new Exception("Production run with id: " + String.valueOf(fg_setup.getProduction_run_id()) + " does not exist.");


        }

        this.carton_num = ProductLabelingDAO.getNextMesObjectId(ProductLabelingDAO.MesObjectTypes.CARTON);

        int packhouse_id = ProductLabelingDAO.getLinePackHouseId(run.getLine_id());

        Resource packhouse = ProductLabelingDAO.getResource(packhouse_id);




        Integer  res_no = packhouse.getResource_number();

        if(res_no == null)
            throw new Exception("Packhouse has null value for resource_number") ;


        Map data = this.label_data;



        try {
            //Pick ref, bit tricky
            pick_ref = "";

            iso_week = ProductLabelingDAO.getCurrentIsoWeek();
            if (iso_week == null) {
                throw new Exception("iso week does not exist for today's date(" + new java.sql.Date(new java.util.Date().getTime()) + ")");

            }
            if (iso_week.length() == 1) {
                iso_week = "0" + iso_week;

                //get wekday

            }
            Calendar today = new GregorianCalendar();
            int weekday = today.get(Calendar.DAY_OF_WEEK) - 1;
            weekday = weekday == 0 ? 7 : weekday;

            String pc_code = "0";
            pc_code = "";

            //pick_ref = iso_week + carton_template.getPc_code_num() + iso_week.substring(1,2);
            pick_ref = iso_week.substring(1, 2) + String.valueOf(weekday) + String.valueOf(res_no) + iso_week.substring(0, 1);


        } catch (Exception ex) {
            throw new Exception("Pick reference could not be calculated. Reported Exception: " + ex.toString());
        }

        Account account = null;
        PUC puc = null;




        phc = ProductLabelingDAO.getLinePhc(run.getLine_id());

        this.packer = this.codeCollection[1];


        fg_setup.setCarton_number(this.carton_num);
        fg_setup.setDrop_resource_id(this.active_device.getResource_id());
        fg_setup.setPack_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));
        fg_setup.setGap(run.getGap());
        fg_setup.setOrchard_code(run.getOrchard_code());



        try {
            double real_mass = Double.parseDouble(this.mass);

            if (real_mass > 0.00) {
                fg_setup.setWeight(real_mass);

            }
        } finally {
        }



        HashMap shift = ProductLabelingDAO.getShift(run.getLine_id(),"packer",this.codeCollection[1]);
        if(shift == null)
            // throw new Exception("No shift found for line: " + run.getLine_code() + " and scanner: " + this.codeCollection[1] );
            return String.format(ProductLabelScan.LABEL_ERR,"No shift found for line: " + run.getLine_code() + " and scanner: " + this.codeCollection[1],"No shift found for line: " + run.getLine_code() + " and scanner: " + this.codeCollection[1],"No shift found for line: " + run.getLine_code() + " and scanner: " + this.codeCollection[1] );


        fg_setup.setPacker_barcode(this.codeCollection[1]);
        fg_setup.setFg_setup_id(fg_setup.getId());
        fg_setup.setPick_reference(this.pick_ref);
        fg_setup.setIso_week(Integer.valueOf(this.iso_week));
        fg_setup.setCreated_on(new java.sql.Timestamp(new java.util.Date().getTime()));
        fg_setup.setFarm_code(run.getFarm_code());
        fg_setup.setAccount_code(run.getAccount_code());
        fg_setup.setPuc(run.getPuc_code());
        fg_setup.setSeason(run.getSeason());
        Integer employee_id = ProductLabelingDAO.getEmployeeId(this.codeCollection[1]);
        fg_setup.setPacker_incentivized_employee_id(employee_id);
        fg_setup.setBatch_code(run.getBatch_code());
        fg_setup.setProduction_run_id(run.getId());
        fg_setup.setShift_id((Integer)shift.get("id"));



        Double calculated_mass = ProductLabelingDAO.getCartonCalculatedMass(fg_setup.getFg_product_id());
        line2 = active_device.getProduction_run_code();
        if(calculated_mass == null||calculated_mass < 1.00)
        {
            line2 = "Derived Product weight not defined";
            return String.format(ProductLabelScan.LABEL_ERR,"Derived Product weight not defined","Derived Product weight not defined",line2);
        }
        else
            fg_setup.setCalculated_mass(calculated_mass);




        String orchard = fg_setup.getOrchard_code() == null? "mixed" : fg_setup.getOrchard_code();
        String gap = fg_setup.getGap() == null? "":fg_setup.getGap();

        data.put("F1",fg_setup.getCarton_number().toString());
        data.put("F2",fg_setup.getCommodity_code());
        data.put("F3",fg_setup.getVariety_description());
        data.put("F4",fg_setup.getCommodity_description());
        data.put("F5",fg_setup.getProduction_run_id().toString());
        data.put("F6",fg_setup.getGrade_code());
        data.put("F7",fg_setup.getPack_code());
        data.put("F8",fg_setup.getOrganization_code());
        data.put("F9","L3999");
        data.put("F10",fg_setup.getInventory_code());
        data.put("F11",fg_setup.getPick_reference().toString());
        data.put("F12",fg_setup.getPuc() + "/" + orchard);
        data.put("F13",gap);

        if(fg_setup.getCommodity_code().toUpperCase().equals("SC"))
            data.put("F14",fg_setup.getSize_count_code() + "/" + fg_setup.getSize_ref());
        else
            data.put("F14",fg_setup.getSize_ref() + "/" + fg_setup.getSize_count_code());


        data.put("F16",fg_setup.getPacker_barcode());
        //data.put("F18",fg_setup.getPacked_tm_group_code());
        data.put("F18","");
        data.put("F19",this.getFormattedNowDate());



        return null;


    }

    //==================================================================
    //This method does the following as an atomic transaction;
    //1) create carton
    //2) update order_quantity_produced on carton setup
    //3) update mes_ctl_sequence
    //=================================================================
    //
    public void send_integration_record() throws Exception {

    }

    protected String post_labeling_transaction() throws Exception {

        String ok_msg = "";

        DataSource.getSqlMapInstance().startTransaction();
        
        if(this.mode == CartonLabelScan.MODE_LINE_SCANNING) {
            ProductLabelingDAO.createCarton(fg_setup);
            ok_msg = "Carton created for drop: " + this.codeCollection[0] + " and scanner: " + this.codeCollection[1];

        }
        else {
            ProductLabelingDAO.createCartonLabel(fg_setup);
            ok_msg = "Label created for drop: " + this.codeCollection[0] + " and scanner: " + this.codeCollection[1];

        }


        ProductLabelingDAO.updateRunStats(fg_setup,null);


        za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().commitTransaction(); //remove for live


        String result = String.format(ProductLabelScan.LABEL_MSG,ok_msg,"PROD: " + ProductLabelingDAO.getFgProductCode(fg_setup.getFg_product_id()),"SETUP: " + ProductLabelingDAO.getFgSetupCode(fg_setup.getFg_setup_id()));
        //System.out.println(result);
        return result;

        //this.getPltransaction().set_do_db_transactio(true);    //uncomment for live
        //System.out.println("exit label data");
        //DataSource.getSqlMapInstance().commitTransaction();




    }
}
