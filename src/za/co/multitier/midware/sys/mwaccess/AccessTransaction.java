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

import za.co.multitier.midware.sys.appservices.*;

import za.co.multitier.mesware.messages.MessageInterface;

import za.co.multitier.mesware.services.email.MailInterface;
//import za.co.multitier.mesware.services.gsm.GsmServerInterface;

import za.co.multitier.messcada.protocol.SysProtocol;
import za.co.multitier.mesware.util.TransactionData;

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
	private static MailInterface        mail            = null; // Implies mail must be available!!
	//private static GsmServerInterface   sms				= null; // Implies sms must be available!!

	private SysInterface				trn				= null;

	private TransactionData				trData			= null;
   

   public AccessTransaction()
   {
	  this.srv 	= this;
   }
   

   public AccessTransaction(SysInterface trn, TransactionData trData)
   {
		this();

		this.trn 	= trn;
		this.trData = trData;

		this.msg 	= trData.msg;
		this.mail	= trData.mailClient;
		//this.sms	= trData.gsmClient;
   }
   

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

		try
		{
			if (trData.transactionType.equals("Logon"))
			{			
				//-------------------------------------------------------------------------------------
				//Logon can be different for different mw programs, so each program
				//has to work out the relevant message(post logon instrauction) to be sent
				//to the TRM.
				//The message can be appended to the result returned by 'authenticate()'
				//and the tag closed, but if the result already contains a msg attribute, we cannot
				//override it, since it can be a logon failure message or a system exception
				//------------------------------------------------------------------------------------
				if (trData.mode.equals("1"))
					resultStr =
						SysProtocol.TLOGONSTATUS +
						"Mode=\"1\" Username=\"" +
						trData.username + 
						"\" Status=\"true\" Msg=\"Op logged on...\" />";

				else if (trData.mode.equals("2"))
					resultStr =
						SysProtocol.TLOGONSTATUS +
						"Mode=\"2\" Username=\"" +
						trData.username +
						"\" Status=\"true\" Msg=\"Sup logged on...\" />";
				else
					resultStr =
						SysProtocol.TLOGONSTATUS +
						"Mode=\"1\" Username=\"" +
						trData.username +
						"\" Status=\"true\" Msg=\"Op logged on...\" />";

				trData.addRecordToReturnData(resultStr);
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
					resultStr =  SysProtocol.TLOGOFFSTATUS +
						"Mode=\"1\" Username=\"" +
						trData.username +
						"\" Status=\"true\"  Msg=\"Op logged off...\" />";
				else if (trData.mode.equals("2"))
					resultStr = SysProtocol.TLOGOFFSTATUS +
						"Mode=\"2\" Username=\"" +
						trData.username +
						"\" Status=\"true\"  Msg=\"Sup logged off...\" />";
				else
					resultStr = SysProtocol.TLOGOFFSTATUS +  "Type=\"1\" Status=\"true\" Msg=\"Op logged off...\" />";

				trData.addRecordToReturnData(resultStr);
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

				trData.addRecordToReturnData(resultStr);
			}
		}
		catch(NullPointerException npe) {}
		catch(Exception ex)
		{
			resultStr =  SysProtocol.TLOGONSTATUS +   "Status=\"false\" Msg=\"AUTH SYS ERR \" />";

			trData.addRecordToReturnData(resultStr);

			return false;
		}

		msg.sysMsg(resultStr);

		return true;
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
   

      
   public synchronized String processTransactionLogon(String username, String password)throws Exception
   {  
	  String result = "";
	  String logon_ok_result	=	"<LogonStatus ";
	  String logon_failed_result			= "<LogonStatus Status=\"false\" ";
	  
	  try
	  {	
		 boolean authenticated = authenticate(username,password);
		 
		 if(authenticated)
			return logon_ok_result + "Username=\"" + username + "\" Status=\"true\" ";
		 else
			return  logon_failed_result + "Msg=\"invalid username or\\and password...\" />";
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
   

   
   public synchronized String processTransactionLogon_proto(String username, String password)
   {
//	  String		resultStr	= "";
//	  String hdr				= "<LogonStatus ";
//	  String hdr1				= "<LogonStatus Status=\"false\" ";
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
   

   public synchronized String processTransactionLogoff(String username, String password)
   {
//	  String		resultStr	= "";
//	  String hdr				= "<LogoffStatus ";
//	  String hdr1				= "<LogoffStatus Status=\"false\" ";
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
   

   public synchronized String processTransactionChangePassword(String username, String password, String newPassword)
   {
//	  String		resultStr	= "";
//	  String hdr				= "<ChangePasswordStatus ";
//	  String hdr1				= "<ChangePasswordStatus Status=\"false\" ";
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

