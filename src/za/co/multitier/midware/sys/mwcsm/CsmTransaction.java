/** ***************************************************************************
 * Class: 		CsmTransaction
 * Description:	System J&J Mesware cold storage transaction
 *
 * @author: 	Dr. J. Fourie & ...
 * @version	 	Rev 1.00
 *
 * Date: 		June 2006
 */
package	za.co.multitier.midware.sys.mwcsm;

import za.co.multitier.sys.SysInterface;

import za.co.multitier.mesware.messages.MessageInterface;
//import za.co.multitier.mesware.messages.MailInterface;
//import za.co.multitier.mesware.messages.SmsInterface;

import za.co.multitier.mesware.util.TransactionData;

public class CsmTransaction
{
	//
	// Constructor handle
	//
	public	CsmTransaction              srv             = null;
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
	 * Description: CfTRansaction base class constructor

	 */
	public CsmTransaction()
	{
        this.srv 	= this;
	}

	/**
	 * Description: CsmTransaction base class constructor

	 */
	public CsmTransaction(SysInterface trn, TransactionData trData)
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
	 * Description: Processes incoming data for this particular application...
	 * 				This method is called by Mesware after receiving incoming scan

	 */
	public synchronized boolean processTransaction(
		String destinationIP,
		String unit,
		String probe,
		String metric,
		String battery,
		String date,
		String time,
		String value)
	{
		String resultStr = "";

		// **********************************************************
		// Processing demo
		// **********************************************************
		
		if (battery.contains("H"))
		{
			String demoStr1 = 
				"ColdStorage Unit= " + unit + 
				" Probe=" + probe +
				" Battery=OK" + 
				" Date= " + date + 
				" Time=" + time + 
				" Value= " + value + metric;
				msg.sysMsg("bold+black", demoStr1);
		}
		else if (battery.contains("L"))
		{
			String demoStr1 = 
				"ColdStorage Unit= " + unit + 
				" Probe=" + probe +
				" Battery=LOW" + 
				" Date= " + date + 
				" Time=" + time + 
				" Value= " + value + metric;
				msg.sysMsg("bold+red", demoStr1);
		}

		return true;
    }
} // CsmTransaction
