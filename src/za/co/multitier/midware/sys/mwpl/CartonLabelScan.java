/*
 * CartonLabelScan.java
 *
 * Created on February 6, 2007, 5:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.mwpl;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
//import za.co.multitier.mesware.messages.MailInterface;
//import za.co.multitier.mesware.services.gsm.GsmServerInterface;

import com.sun.deploy.util.StringUtils;
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

    List<LabelTemplateField> label_template_fields;

    List<PackhouseTreatment> treatments;
    List<PackhouseTreatment> waxs;

    List<PackhouseTreatment> treats;

    protected String setLabelData_old() throws Exception {



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
        data.put("F3","");//fg_setup.getVariety_description());
        data.put("F4",fg_setup.getCommodity_description());
        data.put("F5",fg_setup.getProduction_run_id().toString());
        data.put("F6",fg_setup.getGrade_code());
        data.put("F7",fg_setup.getPack_code());
        data.put("F8",fg_setup.getOrganization_code());
        data.put("F9","L3999"); //phc
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



    protected String setLabelData() throws Exception {


        boolean error = false;

        this.fg_setup = ProductLabelingDAO.getFgSetup(this.active_device.getSetup_detail_id());
        if (this.fg_setup == null) {
            return String.format(ProductLabelScan.LABEL_ERR, "No active FG setup was found for DROP code: " + codeCollection[0], "No active FG setup was found for DROP code: " + codeCollection[0], "No active FG setup was found for DROP code: " + codeCollection[0]);
            //throw new Exception("No active FG setup was found for DROP code: " + codeCollection[0]);


        }


        run = ProductLabelingDAO.getProductionRun(this.active_device.getProduction_run_id());
        if (run == null) {
            throw new Exception("Production run with id: " + String.valueOf(fg_setup.getProduction_run_id()) + " does not exist.");


        }

        this.carton_num = ProductLabelingDAO.getNextMesObjectId(ProductLabelingDAO.MesObjectTypes.CARTON);

        int packhouse_id = ProductLabelingDAO.getLinePackHouseId(run.getLine_id());

        Resource packhouse = ProductLabelingDAO.getResource(packhouse_id);


        Integer res_no = packhouse.getResource_number();

        if (res_no == null)
            throw new Exception("Packhouse has null value for resource_number");


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


        HashMap shift = ProductLabelingDAO.getShift(run.getLine_id(), "packer", this.codeCollection[1]);
        if (shift == null)
            // throw new Exception("No shift found for line: " + run.getLine_code() + " and scanner: " + this.codeCollection[1] );
            return String.format(ProductLabelScan.LABEL_ERR, "No shift found for line: " + run.getLine_code() + " and scanner: " + this.codeCollection[1], "No shift found for line: " + run.getLine_code() + " and scanner: " + this.codeCollection[1], "No shift found for line: " + run.getLine_code() + " and scanner: " + this.codeCollection[1]);


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
        fg_setup.setShift_id((Integer) shift.get("id"));


        Double calculated_mass = ProductLabelingDAO.getCartonCalculatedMass(fg_setup.getFg_product_id());
        line2 = active_device.getProduction_run_code();
        if (calculated_mass == null || calculated_mass < 1.00) {
            line2 = "Derived Product weight not defined";
            return String.format(ProductLabelScan.LABEL_ERR, "Derived Product weight not defined", "Derived Product weight not defined", line2);
        } else
            fg_setup.setCalculated_mass(calculated_mass);

        //==================================================================
        //    Miracle code Label Printing 072015
        //==================================================================

        this.treatments = ProductLabelingDAO.getTreatments(this.fg_setup.getPacked_tm_group_code(),"treatments");
        this.waxs = ProductLabelingDAO.getTreatments(this.fg_setup.getPacked_tm_group_code(),"waxs");

//        this.treats = ProductLabelingDAO.getTreats(this.fg_setup.getPacked_tm_group_code());
//        Map treats = get_treats(this.treats);

        ArrayList<String> treatments = get_treatments(this.treatments);
        ArrayList<String> waxs = get_treatments(this.waxs);

        this.fg_setup.setConcat_treatments(treatments);
        this.fg_setup.setConcat_waxs(waxs);

        //        Find all the fields for the template(label_data_field,label_templates => field_name,position)

        String template_name = is_alternative_label()? this.active_device.getAdditional_template_name():this.active_device.getTemplate_name();
        this.label_template_fields = ProductLabelingDAO.getLabelTemplateField(template_name);
        //        this.data_field_value_translations = ProductLabelingDAO.getDataFieldValue();

        set_template_specific_data(fg_setup,label_template_fields,data);

        //==================================================================
        //    Miracle code Label Printing 072015
        //==================================================================

        return null;

    }

    //==================================================================
    //    Miracle code Label Printing 072015
    //==================================================================

    private void set_template_specific_data(FgSetup fg_setup,List<LabelTemplateField> label_template_fields, Map data){

//        get all data fields from fg_setup
        Map data_fields = create_all_data_fields_map(fg_setup);

//      generate the map; 1 entry for each field in template_label_data_fields ordered by position; key = "F"+position =>"F1","F2" .....

        Map template_label_data_fields = generate_template_label_data_fields_map(label_template_fields,data_fields);

//        print out to data
        String err = this.print_out_data(template_label_data_fields, data);

    }

    private Map create_all_data_fields_map(FgSetup fg_setup){

        Map data_fields = new HashMap();
        try {
            Class myClass = fg_setup.getClass();
            Method[] methods = myClass.getMethods();
            for (Method method:methods)
            {
                String method_name=method.getName();
                if(method_name.startsWith("get"))
                {
                    method.setAccessible(true);
                    if (method_name.startsWith("getConcat_"))
                    {
                        data_fields.put(Introspector.decapitalize(method_name.substring(method_name.startsWith("getConcat_") ? 10 : 10)),method.invoke(fg_setup)== null? "":method.invoke(fg_setup));
                    }
                    else {
                        data_fields.put(get_field_name_from_method_name(method_name), String.valueOf(method.invoke(fg_setup) == null ? "" : method.invoke(fg_setup)));
                    }
                }
            }
        } catch (Exception ex) {
        }
        return data_fields;
    }

    private String get_field_name_from_method_name(String method){

        String field_name = Introspector.decapitalize(method.substring(method.startsWith("get") ? 3 : 3));

        return field_name;
    }

    private Map generate_template_label_data_fields_map(List<LabelTemplateField> template_data_fields,Map data_fields){

        Map template_data_fields_map = new HashMap();

//      get data from List
        for(LabelTemplateField obj : template_data_fields){
            String field_name = String.valueOf(obj.getField_name());
            String field_type = String.valueOf(obj.getField_type());
            String variable1 = String.valueOf(obj.getVariable1());
            String variable2 = String.valueOf(obj.getVariable2());
            String separator = String.valueOf(obj.getSeparator());
            String position = String.valueOf(obj.getPosition()+1);
//            String template_name = String.valueOf(obj.getTemplate_name());
//            String template_file_name = String.valueOf(obj.getTemplate_file_name());
//            String language = String.valueOf(obj.getLanguage());
//            String label_template_field_language = String.valueOf(obj.getLabel_template_field_language());
            String language = (obj.getLabel_template_field_language() ==null || String.valueOf(obj.getLabel_template_field_language()).equals(String.valueOf(obj.getLanguage()))) ? String.valueOf(obj.getLanguage()): String.valueOf(obj.getLabel_template_field_language());

//          call new class  & method e.g. LabelFunction.new(function_name,separator,variable1,variable2).value
            String data_field_value = new LabelFunction(language,field_name,field_type,separator,variable1,variable2,data_fields,fg_setup).value;
            template_data_fields_map.put("F" + position, data_field_value);

        }
        return template_data_fields_map;
    }

    private String print_out_data(Map template_label_data_fields,Map data){

        if (template_label_data_fields.isEmpty() && ! is_alternative_label()) {
            original_print_out_data(data);
        }
        else{
            data.putAll(template_label_data_fields);
        }
        return null;
    }

    private void original_print_out_data(Map data){
        String orchard = fg_setup.getOrchard_code() == null ? "mixed" : fg_setup.getOrchard_code();
        String gap = fg_setup.getGap() == null ? "" : fg_setup.getGap();

        data.put("F1",fg_setup.getCarton_number().toString());
        data.put("F2",fg_setup.getCommodity_code());
        data.put("F3","");
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

    }

    private ArrayList get_treatments(List<PackhouseTreatment> treatment_codes){

        ArrayList<String> codes = new ArrayList<String>();
        for(PackhouseTreatment obj : treatment_codes){
            codes.add(String.valueOf(obj.getTreatment_code()));
        }
        return codes;
    }

    private Map get_treats(List<PackhouseTreatment> treatment_codes){

        ArrayList<String> keys = new ArrayList<String>();
        Map codes = new HashMap();
        for(PackhouseTreatment obj : treatment_codes){
            if(!keys.contains(String.valueOf(obj.getTreatment_type_code()))){
                keys.add(String.valueOf(obj.getTreatment_type_code()));
            }
        }
        codes = get_key_treats(treatment_codes,keys);
        return codes;
    }

    private Map get_key_treats(List<PackhouseTreatment> treatment_codes,ArrayList<String> keys){

        int key_size = keys.size();
        Map codes = new HashMap();
        for (int i=0; i<key_size; i++){
            String key = keys.get(i).toString();
            ArrayList<String> key_values = new ArrayList<String>();
            for(PackhouseTreatment obj : treatment_codes){
                if(key.equals(String.valueOf(obj.getTreatment_type_code()))){
                    key_values.add(String.valueOf(obj.getTreatment_code()));
                }
            }
            String key_method_name = "setConcat_" + String.valueOf(key);
            invoke_fg_setup_method(key_method_name,key_values);
//            this.fg_setup.method_name.invoke(key_values);
            codes.put(key,key_values);
        }
        return codes;
//        return "{" + treatments_values + "}";
    }

    private void invoke_fg_setup_method(String key_method_name,ArrayList<String> key_values){
        try {
            Class myClass = this.fg_setup.getClass();
            Method[] methods = myClass.getMethods();
            for (Method method:methods)
            {
                String method_name=method.getName();
                if(method_name.equalsIgnoreCase(key_method_name))
                {
                    method.setAccessible(true);
                       method.invoke(key_values);
                }
            }
        } catch (Exception ex) {
        }
    }
    //==================================================================
    //    Miracle code Label Printing 072015
    //==================================================================


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

            ProductLabelingDAO.updateRunStats(fg_setup,null);
        }
        else {
            ProductLabelingDAO.createCartonLabel(fg_setup);
            ok_msg = "Label created for drop: " + this.codeCollection[0] + " and scanner: " + this.codeCollection[1];

        }




        za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().commitTransaction(); //remove for live


        String result = String.format(ProductLabelScan.LABEL_MSG,ok_msg,"PROD: " + ProductLabelingDAO.getFgProductCode(fg_setup.getFg_product_id()),"SETUP: " + ProductLabelingDAO.getFgSetupCode(fg_setup.getFg_setup_id()));
        //System.out.println(result);
        return result;

        //this.getPltransaction().set_do_db_transactio(true);    //uncomment for live
        //System.out.println("exit label data");
        //DataSource.getSqlMapInstance().commitTransaction();




    }
}
