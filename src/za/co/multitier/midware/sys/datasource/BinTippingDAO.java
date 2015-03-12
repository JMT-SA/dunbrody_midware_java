/*
 * DevicesDAO.java
 *
 * Created on January 25, 2007, 1:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.datasource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.appservices.DeviceTypes;

public class BinTippingDAO {
    //------------
    //BIN METHODS
    //------------

    public static Integer getLineId(String line_code) throws Exception {
        try {

            //HashMap params = new HashMap();
            //params.put("station_code",station_code);
            //params.put("run_id",production_run_id);

            Integer line_id = (Integer) DataSource.getSqlMapInstance().queryForObject("getLineId", line_code);
            return line_id;
        } catch (SQLException ex) {
            throw new Exception("Line id could not be fetched. Reported exception: " + ex);
        }

    }

    private static String criteriaCheck(Bin bin, Integer bin_tipping_setup_id,ProductionRun run) throws Exception {


        RmtSetup setup = (RmtSetup) DataSource.getSqlMapInstance().queryForObject("getRmtSetup", bin_tipping_setup_id);


        //farm code
        if (setup.isMix_farm()== null ||setup.isMix_farm() ==  false) {
            if (run.getFarm_code() == null||!(run.getFarm_code().equals(bin.getFarm_code()))) {
                return "CRIT:FARM. REQ: " + setup.getFarm_code();
            }
        }

//        if (setup.isMix_delivery()== null||setup.isMix_delivery() == false) {
//            if (setup.getDelivery_id() == null ||!(setup.getDelivery_id().equals(bin.getDelivery_id()))) {
//                return "CRIT:DELIVERY. REQ: " + String.valueOf(setup.getDelivery_number());
//            }
//        }
        
        if (!(setup.getCommodity_code().equals(bin.getCommodity_code())))
            return "REQ COMM: " + setup.getCommodity_code();


        if (!(setup.getVariety_group_code().equals(bin.getVariety_group_code())))
        {
            if (!(setup.getVariety_code().equals(bin.getVariety_code())))
                return "REQ VAR: " + setup.getVariety_code();
        }





        return null;

    }

    public static String validate(String bin_number,int bin_tipping_setup_id,ProductionRun run) throws Exception {
        try {


            Bin bin = getBin(bin_number);
            if (bin == null) {
                return "BIN NOT FOUND";
            }

            if (run.getOrchard_code() != null)
            {
                if(bin.getOrchard_code() == null)
                    return "BIN MUST HAVE ORCH FOR RUN";

                if(bin.getFarm_code() == null)
                    return "BIN MUST HAVE FARM FOR RUN";

                if(!(bin.getFarm_code().equals(run.getFarm_code())))
                    return "BIN FARM: " + bin.getFarm_code() + ". RUN: " + run.getFarm_code();

              if (!(bin.getOrchard_code().equals(run.getOrchard_code())))
                  return "BIN ORCH: " +  bin.getOrchard_code()  + ". RUN: " + run.getOrchard_code();
            }


            String failed_criteria = null;
            failed_criteria = criteriaCheck(bin, bin_tipping_setup_id,run);
            return failed_criteria;




        } catch (SQLException ex) {
            throw new Exception("validation system failed. Reported exception: " + ex);
        }
    }



    public static Integer getTippedBinsCount(int run_id) throws Exception {
        try {


            Integer valid_tipped_count = (Integer) DataSource.getSqlMapInstance().queryForObject("getValidTippedCountForRun", run_id);
            Integer invalid_tipped_count = (Integer) DataSource.getSqlMapInstance().queryForObject("getInvalidTippedCountForRun", run_id);


            return valid_tipped_count + invalid_tipped_count;



        } catch (SQLException ex) {
            throw new Exception("tipped count could not be fetched. Reported exception: " + ex);


        }

    }

    public static void updateBinRunStats(Bin bin) throws Exception {



        if (bin.getWeight() == null) {
            bin.setWeight(0.0);


        }

        DataSource.getSqlMapInstance().update("updateBinsTippedStats", bin);



    }

    public static void validBinTransaction(Bin bin, String production_run_code) throws Exception {

            DataSource.getSqlMapInstance().startTransaction();
            BinTippingDAO.createTippedBin(bin);


            if (bin.getWeight() == null) {
                bin.setWeight(0.0);


            }
            //DataSource.getSqlMapInstance().update("incrementBinsTipped",bin);
            //DataSource.getSqlMapInstance().update("addBinWeight",bin);
            //updateBinRunStats(bin);
            ProductLabelingDAO.updateRunStats(null, bin);

            //DeviceScan.send_integration_record("bin_tipped", bin.getBin_id().toString(), "Bin");

            DataSource.getSqlMapInstance().commitTransaction();



    }

    public static void invalidBinAuthorizedTransaction(InvalidBin bin) throws Exception {
        try {
            DataSource.getSqlMapInstance().startTransaction();
            BinTippingDAO.createInvalidBin(bin);
            DataSource.getSqlMapInstance().update("incrementBinsTipped", bin);
            //TO DO: CREATE POSTBOX RECORD
            //TO DO: CREATE BIN ERROR LOG RECORD
            DataSource.getSqlMapInstance().commitTransaction();
            //DeviceScan.send_integration_record("bin_tipped_invalid",bin.getId().toString(),"BinsTippedInvalid");



        } catch (Exception ex) {

            throw new Exception("invalidBinAuthorizedTransaction failed. Reported exception: " + ex.toString());


        } finally {
            //DataSource.getSqlMapInstance().endTransaction();
        }


    }

    public static void createBinErrorLogEntry(InvalidBin bin) throws Exception {
        try {

            MidwareErrorLogEntry error = new MidwareErrorLogEntry();
            error.setAuthorisor_name(bin.getAuthorisor_name());
            error.setError_code(bin.getError_code());
            error.setError_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));
            error.setError_description(bin.getError_description());
            error.setLine_code(bin.getLine_code());
            error.setMw_type(DeviceTypes.BIN_TIPPING);
            error.setObject_id(bin.getBin_id().toString());
            error.setProduction_run_code(bin.getProduction_run_code());
            error.setProduction_run_id(bin.getProduction_run_id());
            //error.setProduction_schedule_name(bin.getProduction_schedule_name());

            UtilsDAO.createErrorLogEntry(error);



        } catch (SQLException ex) {
            throw new Exception("Bin could not be created. Reported exception: " + ex);


        }

    }

    public static Bin getBin(String bin_id) throws Exception {
        try {

            Bin bin = (Bin) DataSource.getSqlMapInstance().queryForObject("getBin", Long.valueOf(bin_id));


            return bin;


        } catch (SQLException ex) {
            throw new Exception("Bin could not be fetched. Reported exception: " + ex);


        }

    }

    //----------
    //TIPPED BIN
    //----------
    public static Bin createTippedBin(Bin bin) throws Exception {
        try {
            //System.out.println("bin receive datetime is: " + bin.getBin_receive_datetime().toString());
            DataSource.getSqlMapInstance().update("createTippedBin", bin);
            //create integration record



            return bin;


        } catch (Exception ex) {
            throw new Exception("Tipped bin could not be created. Reported exception: " + ex);


        }

    }

    public static Bin getTippedBin(String bin_id) throws Exception {
        try {

            Bin bin = (Bin) DataSource.getSqlMapInstance().queryForObject("getTippedBin", Long.valueOf(bin_id));


            return bin;


        } catch (SQLException ex) {
            throw new Exception("Tipped bin could not be fetched. Reported exception: " + ex);


        }

    }

    //------------
    //INVALID BIN
    //------------
    public static InvalidBin createInvalidBin(InvalidBin bin) throws Exception {
        try {

            DataSource.getSqlMapInstance().insert("createInvalidBin", bin);


            return bin;


        } catch (SQLException ex) {
            throw new Exception("Invalid bin could not be created. Reported exception: " + ex);


        }

    }

    public static Bin getInvalidBin(String bin_id) throws Exception {
        try {

            Bin bin = (Bin) DataSource.getSqlMapInstance().queryForObject("getInvalidBin", bin_id);


            return bin;


        } catch (SQLException ex) {
            throw new Exception("Invalid bin could not be fetched. Reported exception: " + ex);

        }

    }
}
