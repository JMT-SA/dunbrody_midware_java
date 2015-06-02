/** ***************************************************************************
 * Class: 		RSTransaction
 * Description:	System J&J Mesware request-server transaction
 *
 * @author: 	Dr. J. Fourie & ...
 * @version	 	Rev 1.00
 *
 * Date: 		November 2007
 */
package	za.co.multitier.midware.sys.mwrs;

import java.sql.SQLException;
import za.co.multitier.sys.SysInterface;


import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.mesware.services.email.MailInterface;

import za.co.multitier.mesware.util.TransactionData;
import za.co.multitier.midware.sys.appservices.PdtSymbol6800;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;

public class RSTransaction
{
	//
	// Constructor handle
	//
	public	RSTransaction               srv             = null;
	//
	// Interfaces
	//
	private static MessageInterface     msg             = null; // Implies msg must be available!!
	private static MailInterface        mail            = null; // Implies mail must be available!!


	private SysInterface				trn				= null;

	private TransactionData				trData			= null;

	// *************************************************************************
	// Constructor methods....
	// *************************************************************************
	/**
	 * Description: RSTransaction base class constructor
	 *
	 *

	 * @return void

	 */
	public RSTransaction()
	{
        this.srv 	= this;
	}

	/**
	 * Description: RSTransaction base class constructor
	 *
	 *

	 * @return void

	 */
	public RSTransaction(SysInterface trn, TransactionData trData)
	{
		this();

        this.trn 	= trn;
		this.trData = trData;

        this.msg 	= trData.msg;
		this.mail	= trData.mailClient;

	}

    // *************************************************************************
	// Processing methods...
	// *************************************************************************
	/**
	 * Method:		commitTransaction
	 * Description: Commit after MES layer has determined...
	 * 				This method is called by Mesware after the business transaction has been processed (but not committed)

	 */
	 public synchronized boolean commitTransaction(boolean commit)
	 {
		 //
		 // commit = true if OK to commit
		 //
		 //
		 // Commit code goes here....
		 //
		 return true; // Gee die MES net 'n indikasie of jou commit OK deurgeloop het
	 }

	/**
	 * Method:		processTransaction
	 * Description: Processes incoming data for this particular application...
	 * 				This method is called by Mesware after receiving incoming call

	 * @return      String (Argument string)
	 * @exception   NullPointerException
	 */
	public synchronized boolean processTransaction()
	{
		

            String resultStr = "";

		try
		{
            //System.out.println("run darem");
             String destinationIP = trData.destAddr;
             String operator = trData.operator;
             String supervisor = trData.supervisor;
             String args = trData.requestData;

            
             int mode = 0;
             int n_nums_required = 0;
             String modeStr = trData.mode;
                  
            try {

                  mode = Integer.parseInt(trData.mode);
            } catch (NumberFormatException ex) {
                throw new Exception("Mode string must have a numeric value");
            }

            if(mode <= 0)
               throw new Exception("Mode must be greater than zero");

              if(args.trim().equals(""))
               throw new Exception("Args is empty");

                //System.out.println("run darem. mode is: " + String.valueOf(mode));
             switch(mode)
             {

                 case 1: //request for new record number(carton, pallet, etc)
                 {
                     //-----------------------------------
                     //validate parameters(arg iputstring)
                     //-----------------------------------
                     String[] args_array = args.split(",");

                     if(args_array.length != 2)
                       throw new Exception("Args input string must have 2 parameters separated by a comma");


                    try {
                        n_nums_required = Integer.parseInt(args_array[1]);
                    } catch (NumberFormatException ex) {
                        throw new Exception("The left-part of args input string must be a valid number- the amount of object ids to fetch");
                    }

                     ProductLabelingDAO.MesObjectTypes object_type = ProductLabelingDAO.MesObjectTypes.NONE;

                     if(args_array[0].toUpperCase().equals("CARTON"))
                         //gen_number = ProductLabelingDAO.getNextMesObjectId(ProductLabelingDAO.MesObjectTypes.CARTON);
                         object_type = ProductLabelingDAO.MesObjectTypes.CARTON;

                     else if (args_array[0].toUpperCase().equals("PALLET"))
                         object_type = ProductLabelingDAO.MesObjectTypes.PALLET;

                     else if(args_array[0].toUpperCase().equals("REBIN"))
                         object_type = ProductLabelingDAO.MesObjectTypes.REBIN;
                     else
                         throw new Exception("The right-part of args input string must be either CARTON or PALLET or REBIN");


                     //---------------------------------------------------------------------------------
                     //Fetch the required amount of numbers and build a pipe-delimited string of numbers
                     //---------------------------------------------------------------------------------
                      //System.out.println("run darem 3");
                     for(int i = 1;i <= n_nums_required;i++)
                     {
                           Long gen_number = ProductLabelingDAO.getNextMesObjectId(object_type);
                           if(i == 1)//for a single number request, well only get inside loop once- right here
                               resultStr = gen_number.toString();
                           else
                               resultStr += "," + gen_number.toString();


                     }

                     break;

                 }

                 case 2:
                 {
                     //System.out.println("case 2 called");
                     //resultStr = PdtSymbol6800.handle_request("4","RequestServer",args,null,null,destinationIP,operator);

                     // resultStr = SysProtocol.TREQUESTSERVER + "Status=\"true\" LCD1=\"Line 1\" LCD2=\"Line 2\" LCD3=\"Line 3\" LCD4=\"Line 4\" LCD5=\"Line 5\" LCD6=\"Line 6\" />";
                     break;
                 }

                 default:
                     throw new Exception("Mode: " + modeStr + " is unknown");

             }


		}

        catch(Exception e)
        {
           //System.out.println("result is: " + resultStr);
           //msg.println("result is: " + resultStr);

           resultStr = "Request server transaction failed.Exception: " + e.getMessage().toString();

           //System.out.println(resultStr);
          // msg.println(resultStr);
        }
   		finally
		{

         // System.out.println("result is: " + resultStr);
          trData.addRecordToReturnData(resultStr);
         try {//HZ
               za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().endTransaction();
             } catch (SQLException ex) {
              ex.printStackTrace();
            }
            msg.sysMsg(resultStr);


			return true;
		}
    }
} // RSTransaction
