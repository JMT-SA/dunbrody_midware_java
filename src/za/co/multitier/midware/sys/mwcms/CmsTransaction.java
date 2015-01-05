/** ***************************************************************************
 * Class: 		CmsTransaction
 * Description:	System J&J Mesware CMS transaction
 *
 * @author: 	Dr. J. Fourie...
 * @version	 	Rev 1.00
 *
 * Date: 		October 2008
 */
package	za.co.multitier.midware.sys.mwcms;

import com.ibatis.sqlmap.client.SqlMapClient;

import java.sql.SQLException;
import java.util.List;
import za.co.multitier.sys.SysInterface;

import za.co.multitier.midware.sys.protocol.SysProtocol;

import za.co.multitier.mesware.messages.MessageInterface;
//import za.co.multitier.mesware.messages.MailInterface;
//import za.co.multitier.mesware.messages.SmsInterface;

import za.co.multitier.mesware.util.TransactionData;

import za.co.multitier.mesware.sys.mwpl.PlConstants;

public class CmsTransaction
{
	//
	// Constructor handle
	//
	public	CmsTransaction              srv             = null;
	//
	// Interfaces
	//
	private static MessageInterface     msg             = null; // Implies msg must be available!!
	//private static MailInterface        mail            = null; // Implies mail must be available!!
	//private static SmsInterface         sms				= null; // Implies sms must be available!!
	
	private SysInterface				trn				= null;

	private TransactionData				trData			= null;

	// *************************************************************************
	// Constructor methods....
	// *************************************************************************
	/**
	 * Description: CmTransaction base class constructor

	 */
	public CmsTransaction()
	{
        this.srv 	= this;
	}

	/**
	 * Description: CmTransaction base class constructor

	 */
	public CmsTransaction(SysInterface trn, TransactionData trData)
	{
		this();

        this.trn 	= trn;
		this.trData = trData;

        this.msg 	= trData.msg;
		//this.mail	= trData.mailClient;
		//this.sms	= trData.smsClient;
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

	 */
	public synchronized boolean processTransaction()
	{
		int mode = -1;

		try
		{
			mode = Integer.parseInt(trData.mode);
        }
        catch(NumberFormatException nfe)
        {
			trData.addRecordToReturnData(SysProtocol.TMSG + "Status=\"false\" Red=\"true\" Yellow=\"false\" Green=\"false\" msg=\"Mode conversion error\" />");
			return false;
        }

		String resultStr = "";
             
//        SqlMapClient sqlMapInstance = za.co.multitier.mesware.data.datasource.DataSource.getSqlMapInstance();
              
        // *************************************************************************************************************************
        // Notes:
        //  - Printing: 
        //      - Mesware 'assumes' that it has to print a label if the fields F0-Fx are present in the return packet
        //
        //      - Please add the F0-Fx fields as LAST fields in return packet as it has to be stripped out in order
        //         to send RF scanner applicable data back to th scanner and print data to the printer (SLOW cpus)
        //
        //      - Printing fields required on a packet also intended for printing are: 
        //          - StartNr=0  	Make default 0 if not used
        //	        - CountNr=1  	Make default 1 if not used
        //	        - F0-Fx		The print fields
        //	        - Use �[NR]� everywhere in F0-Fx fields where replacement with �CountNr� needed
        //	        - If return packet �Status=�false� then printing will NOT proceed.
        //
        //  - Input1-3 Enable & Visible fields : Present on every packet as per process control rules
        // **************************************************************************************************************************                    

		try
		{
 			if (trData.transactionType.equals("1.1"))
			{
                switch(mode)
                {
                    case 0 : ;                                  // PDT Format String
                        resultStr = 
                            "<PdtFormat Status=\"true\" Msg=\"\" " +
                            " LCD1=\"\" LCD2=\"\" LCD3=\"\" LCD4=\"\" LCD5=\"\" LCD6=\"\" LCD7=\"\" LCD8=\"\" LCD9=\"\" " +
                            " Input1=\"\" Input1Visible=\"true\" Input1Enable=\"true\" " +
                            " Input2=\"\" Input2Visible=\"true\" Input2Enable=\"true\" " +
                            " Input3=\"\" Input3Visible=\"true\" Input3Enable=\"true\" />";

						trData.addRecordToReturnData(resultStr);
                        break;
                        
                    case PlConstants.MODEPC		: ; // Packing station number only
                    case PlConstants.MODEI2		: ; // PStation Nr + Personnel Nr.....
                    case PlConstants.MODEI2U	: ; // PStation Nr + undefined length personnel number
                    case PlConstants.MODEI2M	: ; // Input1 + Input3(mass)....
                  //  case PlConstants.MODEI3		: ; // Input1 + Input2 + Input3(mass)....
                  //  case PlConstants.MODEI3U	: ; // PStation Nr + undefined length personnel number + Input3 (mass)
                        resultStr = processTransactionIntake(trData.codeCollection); // i.e. input[0], input[1] & input[3]
                        //
                        // Override with demo string...
                        //
                        resultStr = 
                            "<" + trData.transactionType +
                            " Status=\"true\" Msg=\"Transaction OK\" " +
                            " LCD1=\"\" LCD2=\"\" LCD3=\"\" LCD4=\"\" LCD5=\"\" LCD6=\"\" LCD7=\"\" LCD8=\"\" LCD9=\"\" " +
                            " Input1=\"\" Input1Visible=\"true\" Input1Enable=\"true\" " +
                            " Input2=\"\" Input2Visible=\"true\" Input2Enable=\"true\" " +
                            " Input3=\"\" Input3Visible=\"true\" Input3Enable=\"true\" />";

						trData.addRecordToReturnData(resultStr);
                        break;
                } // switch(mode
			}
			else
			{
				// To be defined as required....
			}
		}
        catch(NumberFormatException nfe)
		{
			trData.addRecordToReturnData(SysProtocol.TMSG + "Status=\"false\" Red=\"true\" Yellow=\"false\" Green=\"false\" msg=\"NFE\" />");
			return false;
		}
		catch(NullPointerException npe)
		{
			trData.addRecordToReturnData(SysProtocol.TMSG + "Status=\"false\" Red=\"true\" Yellow=\"false\" Green=\"false\" msg=\"NPE\" />");
			return false;
		}
		catch(Exception ex)
		{
			trData.addRecordToReturnData(SysProtocol.TMSG + "Status=\"false\" Red=\"true\" Yellow=\"false\" Green=\"false\" msg=\"Exception error...\" />");
			return false;
		}

		msg.sysMsg(resultStr);

		return true;
    }
	

	/**
	 * Description: Processes the data collected by the transaction
	 * 				This method is called by Mesware after receiving incoming scan

	 * @return      String (result string)
	 * @exception   NullPointerException, NumberFormatException
	 */
	public synchronized String processTransactionIntake(String input[])
	{             
//        SqlMapClient sqlMapInstance = za.co.multitier.mesware.data.datasource.DataSource.getSqlMapInstance();
		
      //  try
      //  {
//           List list = sqlMapInstance.queryForList("getPersonnelData");
           // List list = sqlMapInstance.queryForList("getPersonData", "1001");

            //System.out.println("Here 2: " + list.size() + ":" + list.isEmpty());

//            for (int i=0; i < list.size(); i++ )
           // {
                //System.out.println(list.get(i));
           // }
      //  }
//        catch(SQLException sqe) 
      //  {
      //  }
	//	catch(NullPointerException npe)
        {
        }
		//catch(Exception ex) {}

		return "";
	}
} // CmsTransaction
