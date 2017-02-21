/*
 * AwaitScanState.java
 *
 * Created on January 30, 2007, 2:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwcm;


import java.util.HashMap;

import za.co.multitier.midware.sys.datasource.*;

/**
 * @author Administrator
 */
public class AwaitScanState extends BinTippingState {

    private Bin bin;

    /**
     * Creates a new instance of AwaitScanState
     */
    public AwaitScanState(String ip, String bin_id, BinTippingScan parent) throws Exception {
        super(ip, bin_id, parent);
    }

    public AwaitScanState(String ip, String bin_id, BinTippingScan parent, Bin bin) throws Exception {
        this(ip, bin_id, parent);
        this.bin = bin;
    }


    public static boolean is_tipped_bin(String bin_id) throws Exception {
        if (BinTippingDAO.getTippedBin(bin_id) != null)
            return true;
        else if (BinTippingDAO.getInvalidBin(bin_id) != null)
            return true;
        else
            return false;

        // this.parent.active_state = new AwaitAuthState(this.ip,this.bin_id,this.parent,invalid_msg);
    }


    //--------------------------------------------------------------------------
    //This method tries to find a bin with scanned-in id in the bins table, if
    //found, it creates a new tipped bin and deletes the bin, as well as create
    // a postBox out record - else TRANSISIONS to InvalidBin
    //--------------------------------------------------------------------------
    public void scanBin(String mass) throws Exception {
        //try
        //{

        this.bin = BinTippingDAO.getBin(this.bin_id);
        if (mass == null)
            mass = "";

        double real_mass = 0.00;

        Double crate_mass = this.bin.getMaterial_mass();
        if (crate_mass == null) {
            this.device_instruction = this.parent.createBintipInstruction(true, true, this.parent.active_device.getProduction_run_code(),
                    false, false, "NO BIN_TYPE MASS");
            return;
        }

        try {
            real_mass = Double.parseDouble(mass);

            if (real_mass > 0.00) {
                if ((real_mass - crate_mass) > 10.00)

                    this.bin.setWeight(real_mass - crate_mass);
                else
                {
                    this.device_instruction = this.parent.createBintipInstruction(true, true, this.parent.active_device.getProduction_run_code(),
                            false, false, "INVALID MASS: " + mass);
                    return;
                }

            }

        } catch (NumberFormatException ne) {
            this.device_instruction = this.parent.createBintipInstruction(true, true, this.parent.active_device.getProduction_run_code(),
                    false, false, "INVALID MASS: " + mass);
            return;
        }


        if (this.bin != null && !is_tipped_bin(this.bin_id)) {
            this.bin.setProduction_run_code(this.parent.active_device.getProduction_run_code());
            this.bin.setProduction_run_id(this.parent.active_device.getProduction_run_id());
            this.bin.setLine_code(this.parent.active_device.getLine_code());

            HashMap shift = ProductLabelingDAO.getShift(this.parent.active_device.getLine_id(), "packer");
            if (shift != null)
                this.bin.setShift_id((Integer) shift.get("id"));


            this.bin.setTipped_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));

            BinTippingDAO.validBinTransaction(this.bin, this.parent.active_device.getProduction_run_code());
            this.device_instruction = this.parent.createBintipInstruction(true, false, this.parent.active_device.getProduction_run_code(),
                    false, true, BinTippingScan.SCAN_NEXT_BIN_MSG);
        } else {
            this.parent.active_state = new InvalidBinState(this.ip, this.bin_id, this.parent);
            this.parent.active_state.scanBin(mass);
        }
        //} catch (Exception ex)
        //{
        //	throw new Exception("AwaitScanState's 'scan_bin()' method failed. Reported exception: " + ex.toString());
        //}

    }

}
