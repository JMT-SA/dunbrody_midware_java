/** ***************************************************************************
 * Class: 		AccessTransaction
 * Description:	J&J Mesware request access (logon/logoff) transaction
 *
 * @author: 	Dr. J. Fourie & ...
 * @version	 	Rev 1.00
 *
 * Date: 		August 2006
 */
package	za.co.multitier.midware.sys.mwaccess;

import java.io.*;

import java.net.*;

import java.util.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import za.co.multitier.sys.SysInterface;



import za.co.multitier.mesware.messages.MessageInterface;
//import za.co.multitier.mesware.messages.MailInterface;
//import za.co.multitier.mesware.messages.SmsInterface;

import za.co.multitier.midware.sys.protocol.SysProtocol;

import za.co.multitier.mesware.util.TransactionData;
import za.co.multitier.midware.sys.mwcm.AwaitAuthState;
import za.co.multitier.midware.sys.mwcm.BinTippingScan;
import za.co.multitier.midware.sys.appservices.*;
import za.co.multitier.midware.sys.datasource.*;

public class AccessTransaction
{
	//
	// Constructor handle
	//
	public	AccessTransaction           srv             = null;
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

	*/
   public AccessTransaction()
   {
	  this.srv 	= this;
   }

    //---------------------------------------------------------------------------------------------------------------
   //This method determines the instruction sent to the trm based on the application contect- i.e. it's
   //active state and whether the logged-on user has the 'override' permission(i.e. is the 'supervisor')
   //The following scenarios are possible
   //1) A bintip operator logs-on for the first time: ctx is->  Binscan's active state is null or not await_auth'
   //                                                         and authorisation is false
   //                                                 msg is-> 'scan  bin'
   //2) A supervisor logs on, but not in await auth state: ctx-> Binscan's active state is null or not await_auth'
   //                                                           and authorisation is true
   //                                                      msg -> 'scan bin'
   //3) A supervisor logs on, in await_auth state: ctx-> Binscan's active state is 'AwaitAuth' and authorisation
   //                                                    is true
   //                                                    msg -> 'Press F1(yes) or F2(no)'
   //4) A supervisor logs on, in await_auth state: ctx-> Binscan's active state is 'AwaitAuth' and authorisation
   //                                                    is false
   //                                                    msg -> 'Override Auth denied'
   //-------------------------------------------------------------------------------------------------------------
   private String setBintipLogonMessage(String username,String destinationIP,ActiveRunResource active_device)throws Exception
   {
	  String logon_msg = "scan bin"; //SCENARIOS 1 AND 2
	  try
	  {
		 BinTippingScan bin_scan = (BinTippingScan)BinTippingCache.getDevicesCache().getDeviceState(destinationIP,
			active_device.getProduction_run_code(),DeviceTypes.BIN_TIPPING);

		 if( authorize(username,DeviceTypes.BIN_TIPPING,"override"))
		 {
			if(bin_scan != null)
			{
			   bin_scan.HasOverrideAuthorisation = true;
			   bin_scan.authoriser_name = username;
			   if(bin_scan.active_state instanceof AwaitAuthState) //SCENARIO 3
			   {
				  logon_msg = "F1(yes)/F2(no)";
			   }

			}

		 }
		 else //SCENARIO 4
		 {
			logon_msg = "NO OVERR AUTH";
		 }

		 return logon_msg;

	  }
	  catch (Exception ex)
	  {
		 throw new Exception("The correct logon message could not be worked out. Reoported exception: " + ex);
	  }


   }
   

   public AccessTransaction(SysInterface trn, TransactionData trData)
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

	 */
	 public synchronized boolean commitTransaction(boolean commit)
	 {
		 //
		 // Commit code goes here....
		 //
		 return true; // Gee die MES net 'n indikasie of jou commit OK deurgeloop het
	 }
	 
   /**
	* Description: Processes incoming data for this particular application... 	<BR>
	* 				Called by mwserver after receiving incoming scans & data
	* @return      boolean (result)
	* @exception   NullPointerException
	*/    
   public synchronized boolean processTransaction()
   {
		String resultStr = "";
		String destinationIP= trData.destAddr;
	    String username = trData.username;
	    String password = trData.password;
	    String newPassword = trData.newPassword;
       ActiveRunResource active_device = null;
        String mode = trData.mode;

		try
		{
			if (trData.transactionType.equals("Logon"))
			{			
				//-------------------------------------------------------------------------------------
				//Logon can be different for differnt mw programs, so each program
				//has to work out the relevant message(post logon instrauction) to be sent
				//to the TRM.
				//The message can be appended to the result returned by 'authenticate()'
				//and the tag closed, but if the result already contains a msg attribute, we cannot
				//override it, since it can be a logon failure message or a system exception
				//------------------------------------------------------------------------------------
				if (trData.mode.equals("1"))
					resultStr = processTransactionLogon(username, password,mode);
				else if (trData.mode.equals("2"))
                {
					resultStr = processTransactionLogon(username, password,mode);
					active_device = DevicesDAO.getActiveDevice(destinationIP);

					String logon_msg = setBintipLogonMessage(username,destinationIP,active_device);
					resultStr += " LCD2=\"" + logon_msg + "\" />";
                }
				else
					resultStr = SysProtocol.TLOGONSTATUS + "Mode=\"1\" Status=\"true\" Msg=\"Op logged on...\" />";
			}
			else if (trData.transactionType.equals("Logoff"))
			{
				//
				// Demonstrating: DBMS Access for logoff
				//
				//resultStr = processTransactionLogoff(username, password);
				//
				// Inserting demo string...
				//
				if (trData.mode.equals("1"))
					resultStr =
						SysProtocol.TLOGOFFSTATUS +
						"Mode=\"1\" Username=\"" +
						trData.username +
						"\" Status=\"true\"  Msg=\"Op logged off...\" />";
				else if (trData.mode.equals("2"))
					resultStr =
						SysProtocol.TLOGOFFSTATUS +
						"Mode=\"2\" Username=\"" +
						trData.username +
						"\" Status=\"true\"  Msg=\"Sup logged off...\" />";
				else
					resultStr = SysProtocol.TLOGOFFSTATUS + "Type=\"1\" Status=\"true\" Msg=\"Op logged off...\" />";

				
			}
			else if (trData.transactionType.equals("ChangePassword"))
			{
				//
				// Demonstrating: DBMS Access change password
				//
				//resultStr = processTransactionChangePassword(username, password, newPassword);
				//
				// Inserting demo string...
				//
				resultStr =
					SysProtocol.TCHGPWDSTATUS +
					"Username=\"" +
					trData.username +
					"\" Status=\"true\" Msg=\"Password change ok...\" />";

				
			}
		}
		catch(NullPointerException npe) {}
		 catch(Exception ex)
	  {

		 String prod_run_code = "NOT KNOWN IN CONTEXT: ACTIVE DEVICE DOES NOT EXIST IN CONTEXT";
		 String mw_type = "NOT KNOWN IN CONTEXT: ACTIVE DEVICE DOES NOT EXIST IN CONTEXT";
		 if(active_device != null)
		 {
			mw_type = active_device.getResource_type_code();
			prod_run_code = active_device.getProduction_run_code();

		 }

		 Logger.handleException(msg,"unexpected exception occurred during logon for devive with ip: " + destinationIP,ex.toString(),"AccessTransaction." + mode,
			mw_type,0,prod_run_code,ex.getStackTrace(),destinationIP);
		 
		 resultStr =  SysProtocol.TLOGONSTATUS + "Status=\"false\" Msg=\"AUTH SYS ERR \" Username=\"" + username + "\" />";
         
	  }
	  finally
	  {
		 if(resultStr.indexOf("/>") == -1)
			resultStr += "/>";
		 msg.sysMsg(" RESULTSTR: " + resultStr);
         trData.addRecordToReturnData(resultStr);
		 return true;
	  }
   }
   
   
    public static boolean authorize(String username,String program,String permission) throws Exception
   {

		 try
		{

			//-----------------------------------------------------------------------------------
			//Get the REST service url string from central config and append the method parameters
			//-----------------------------------------------------------------------------------
			Properties settings = MidwareConfig.getInstance().getSettings();
			String url_string = (String)settings.get ("security_service_url");
			//append the parameters
			String params = "?user=" + username + "&program=" + program + "&permission=" + permission;
			url_string += params;

			//-----------------------------------
			//Make the REST service call
			//-----------------------------------
			URL url = new URL(url_string);
			InputStream instream = url.openStream();

			//----------------------------------------------------
			//Parse the xml result string for the call return value
			//----------------------------------------------------
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
			DocumentBuilder builder = factory.newDocumentBuilder ();
			org.w3c.dom.Document doc = builder.parse(instream);
			NodeList nodes = doc.getElementsByTagName("result");

			int result = Integer.parseInt(nodes.item (0).getTextContent());

			return result == 1;


		} catch (Exception e)
		{
			throw new Exception("Authorization program failed. reported Exception: " +  e.getMessage());
		}


   }

   /**
	* Description: Attempts to authorise a user for a specific access permission for a specific program(i.e. program
	*              as defined in RAILS security model)via a webservice implemented in RAILS

	*/

   public static boolean authenticate(String username,String password) throws Exception
   {

		 try
		{

			//-----------------------------------------------------------------------------------
			//Get the REST service url string from central configand append the method parameters
			//-----------------------------------------------------------------------------------
			Properties settings = MidwareConfig.getInstance().getSettings();
			String url_string = (String)settings.get ("security_service_url");
			//append the parameters
			String params = "?user=" + username + "&pwd=" + password;
			url_string += params;

			//-----------------------------------
			//Make the REST service call
			//-----------------------------------
			URL url = new URL(url_string);
			InputStream instream = url.openStream();

			//----------------------------------------------------
			//Parse the xml result string for the call return value
			//----------------------------------------------------
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
			DocumentBuilder builder = factory.newDocumentBuilder ();
			org.w3c.dom.Document doc = builder.parse(instream);
			NodeList nodes = doc.getElementsByTagName("result");
			int result = Integer.parseInt(nodes.item (0).getTextContent());
			return result == 1;


		} catch (Exception e)
		{
			throw new Exception("Authorization program failed. reported Exception: " +  e.getMessage());
		}


   }
   /**
	* Description: Attempts to authenticate a given user against the RAILS security model via a webservice implemented in RAILS

	*/
      
   public synchronized String processTransactionLogon(String username, String password,String mode)throws Exception
   {  
	  String result				= "";
	  String logon_ok_result	= SysProtocol.TLOGONSTATUS + "Status=\"true\" Mode=\"" + mode + "\" ";
	  String logon_failed_result= SysProtocol.TLOGONSTATUS + "Status=\"false\" Mode=\"" + mode + "\"";
      String role				= "OP";
	  
      if(mode.equals("2"))
          role = "SUP";
	  
	  try
	  {	
		 boolean authenticated = authenticate(username,password);
		 
		 if(authenticated)
			return logon_ok_result +  "Username=\"" + username + "\" Msg=\"" + role + " Logged on...\" ";
		 else
			return  logon_failed_result + "Username=\"" + username + " Msg=\"invalid username or\\and password...\" />";
	  }
	  catch(Exception ex)
	  {
		 throw ex;
//		 msg.sysMsg("bold+red", "Remote HTTP service Exception while calling the authenticate service: " + ex.toString());
//		 
//		 logon_failed_result += "Msg=\"AUTH SYS ERR...\" />";
//		 return logon_failed_result;
	  }  
   }
   
   /**
	* Description: Processes the logon data collected by the transaction

	*/
   
   public synchronized String processTransactionLogon_proto(String username, String password)
   {
//	  String		resultStr	= "";
//	  
//	  ResultSet	rs1			= null;
//	  ResultSet	rs2			= null;
//	  
//	  String[] sql1 =											// Check username=ok
//	  {
//		 "SELECT * from resourceaccess where Username='",
//		 username,
//		 "';"
//	  };
//	  
//	  try
//	  {
//		// rs1 = trn.processSql(sql1);
//		 
//		 if (rs1 != null)
//		 {
//			rs1.first();
//			int		i	= rs1.findColumn("ResourceID");
//			String	id	= rs1.getString(i);
//			i			= rs1.findColumn("Password");
//			String	pwd = rs1.getString(i);
//			
//			if (!pwd.equals(password))						// Check password...
//			{
//			   hdr1 += "Msg=\"Invalid password...\" />";
//			   
//			   return hdr1;
//			}
//			
//			String[] sql2 =									// Check valid user...
//			{
//			   "SELECT * from resources where ResourceID='",
//			   id,
//			   "';"
//			};
//			
//			//rs2 = trn.processSql(sql2);
//			if (rs2 == null)
//			{
//			   hdr1 += "Msg=\"User-UserID mismatch...\" />";
//			   
//			   return hdr1;
//			}
//			
//			resultStr =
//			   hdr +
//			   "Username=\"" + username +
//			   "\" Status=\"true\" />";
//			
//			return resultStr;
//		 }
//		 else												// User not found...
//		 {
//			hdr1 += "Msg=\"Invalid username...\" />";
//			
//			return hdr1;
//		 }
//	  }
//	  catch(SQLException sqe)
//	  {
//		 msg.sysMsg("bold+red", "SQL Exception while processing a logon resultset...");
//		 
//		 hdr1 += "Msg=\"Database error...\" />";
//		 
//		 return hdr1;
//	  }
//	  catch(NullPointerException npe)
//	  {return hdr1;}
//	  catch(Exception ex)
//	  {return hdr1;}
	  return "";
   }
   
   /**
	* Description: Processes the data collected by the transaction
	* 				This method is called by Mesware after receiving incoming scan

	*/
   public synchronized String processTransactionLogoff(String username, String password)
   {
//	  String		resultStr	= "";
//	  
//	  ResultSet	rs1			= null;
//	  ResultSet	rs2			= null;
//	  
//	  String[] sql1 =											// Check username=ok
//	  {
//		 "SELECT * from resourceaccess where Username='",
//		 username,
//		 "';"
//	  };
//	  
//	  try
//	  {
//		 //rs1 = trn.processSql(sql1);
//		 if (rs1 != null)
//		 {
//			rs1.first();
//			int		i	= rs1.findColumn("ResourceID");
//			String	id	= rs1.getString(i);
//			
//			String[] sql2 =									// Check valid user...
//			{
//			   "SELECT * from resources where ResourceID='",
//			   id,
//			   "';"
//			};
//			
//			//rs2 = trn.processSql(sql2);
//			if (rs2 == null)
//			{
//			   hdr1 += "Msg=\"User-UserID mismatch...\" />";
//			   
//			   return hdr1;
//			}
//			
//			resultStr =
//			   hdr +
//			   "Username=\"" + username +
//			   "\" Status=\"true\" />";
//			
//			return resultStr;
//		 }
//		 else												// User not found...
//		 {
//			hdr1 += "Msg=\"Invalid username...\" />";
//			
//			return hdr1;
//		 }
//	  }
//	  catch(SQLException sqe)
//	  {
//		 msg.sysMsg("bold+red", "SQL Exception while processing a logoff resultset...");
//		 
//		 hdr1 += "Msg=\"Database error...\" />";
//		 
//		 return hdr1;
//	  }
//	  catch(NullPointerException npe)
//	  { return hdr1; }
//	  catch(Exception ex)
//	  { return hdr1; }
	   return "";
   }
   
   /**
	* Description: Processes the change password data collected by the transaction

	*/
   public synchronized String processTransactionChangePassword(String username, String password, String newPassword)
   {
//	  String		resultStr	= "";
//	  String hdr				= SysProtocol.TCONTAINERMOVE;
//	  String hdr1				= SysProtocol.TCONTAINERMOVE + "Status=\"false\" ";
//	  
//	  ResultSet	rs1			= null;
//	  ResultSet	rs2			= null;
//	  
//	  String[] sql1 =											// Check username=ok
//	  {
//		 "SELECT * from resourceaccess where Username='",
//		 username,
//		 "';"
//	  };
//	  
//	  try
//	  {
//		 rs1 = trn.processSql(sql1);
//		 
//		 if (rs1 != null)
//		 {
//			rs1.first();
//			int		i	= rs1.findColumn("ResourceID");
//			String	id	= rs1.getString(i);
//			
//			i			= rs1.findColumn("Password");
//			String	pwd = rs1.getString(i);
//			
//			if (!pwd.equals(password))						// Check password...
//			{
//			   hdr1 += "Msg=\"Invalid password...\" />";
//			   
//			   return hdr1;
//			}
//			
//			String[] sql2 =									// Check valid user...
//			{
//			   "SELECT * from resources where ResourceID='",
//			   id,
//			   "';"
//			};
//			
//			rs2 = trn.processSql(sql2);
//			if (rs2 == null)
//			{
//			   hdr1 += "Msg=\"User-UserID mismatch...\" />";
//			   
//			   return hdr1;
//			}
//			
//			String[] sql3 =									// Update the password...
//			{
//			   "UPDATE resourceaccess ",
//			   "SET Password='" + newPassword + "' ",
//			   "where Username='" + username + "';"
//			};
//			
//			rs1 = trn.processSql(sql3);
//			
//			resultStr =
//			   hdr +
//			   "Username=\"" + username +
//			   "\" Status=\"true\" />";
//			
//			return resultStr;
//		 }
//		 else												// User not found...
//		 {
//			hdr1 += "Msg=\"Invalid username...\" />";
//			
//			return hdr1;
//		 }
//	  }
//	  catch(SQLException sqe)
//	  {
//		 msg.sysMsg("bold+red", "SQL Exception while processing a logon resultset...");
//		 
//		 hdr1 += "Msg=\"Database error...\" />";
//		 
//		 return hdr1;
//	  }
//	  catch(NullPointerException npe)
//	  {return hdr1;}
//	  catch(Exception ex)
//	  {return hdr1;}
	   return "";
   }
} // AccessTransaction

