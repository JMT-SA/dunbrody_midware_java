/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.mwpl;


import za.co.multitier.midware.sys.appservices.DeviceCommands;
import za.co.multitier.midware.sys.mwcm.BinTippingScan;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author hans
 */
public class MockServer {

    private static DeviceCommands.TRM_KEYPAD getButton(int button) {

        switch (button) {
            case 1:
                return DeviceCommands.TRM_KEYPAD.F3;

            case 2:
                return DeviceCommands.TRM_KEYPAD.F4;


            default:
                return DeviceCommands.TRM_KEYPAD.none;

        }
    }

    public static void main(String[] args) {

        String ip = "192.168.10.48";


        Scanner in = new Scanner(System.in);

        boolean stop_server = false;

        while (!stop_server) {
            
            String drop = "";
            String percon_barcode = "";
            String [] code_collection = new String[2];
                    

            String result = "";

            System.out.println("-------------------------------------------");
            System.out.println("Please enter drop code");
            System.out.println("-------------------------------------------");

            String input = in.nextLine();

            if (input == "-1")
                stop_server = true;
            else
                drop = input;


            System.out.println("-------------------------------------------");
            System.out.println("Please enter personelle barcode");
            System.out.println("-------------------------------------------");

            input = in.nextLine();

            if (input == "-1")
                stop_server = true;
            else
                percon_barcode = input;


            code_collection[0] = drop;
            code_collection[1]= percon_barcode;



            

           try
           {
            //result = BinTippingScan.scanBin(bintip_ip,bin_scanned,getButton((int) button),"bin_tipper_1" );
               CartonLabelScan labeling_scan = new CartonLabelScan(ip, "15.3", code_collection,CartonLabelScan.MODE_ROBOT_SCANNING);

               

               result = labeling_scan.processLabelScan();
           }
           catch(Exception e)
           {
                 System.out.println("Carton scan EXCEPTION: " );
                 System.out.println(e.getMessage());
                  e.printStackTrace();
           }

            finally
           {
               try {
                   za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().endTransaction();

               } catch (SQLException e) {
                    System.out.println("TRANSACTION COULD NOT BE ENDED: ");
                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
               }
           }

            System.out.println("RESULT: ");
            System.out.println(result);


        }


    }
}
