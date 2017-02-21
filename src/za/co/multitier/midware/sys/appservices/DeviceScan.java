/*
 * DeviceScan.java
 *
 * Created on January 30, 2007, 10:37 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.appservices;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import za.co.multitier.mesware.messages.MessageInterface;
//import za.co.multitier.mesware.messages.MailInterface;
/**
 *
 * @author Administrator
 */



public class DeviceScan
{
    
   //private static MailInterface mailer;
   
   
//   public static void setMailer(MailInterface mail_engine)
//   {
//       if(mailer == null)
//           mailer = mail_engine;
//   }
//
//
//   public static MailInterface getMailer() throws Exception
//   {
//       if(mailer == null)
//           throw new Exception("Mailer is null");
//       return mailer;
//   }
   
   public MessageInterface midware_console;
   
   /** Creates a new instance of DeviceScan */
   public DeviceScan(MessageInterface msg)
   {
	  this.midware_console =  msg;
	 
   }

    public DeviceScan()
   {

   }


   public static String calc_checkdigitSSCC(String barcode )
	{
		barcode = barcode.trim();
		long sum = 0;
		String check_digit = null;
		
		
		for(int i = 0; i < barcode.length();i++)
		{
			long curr_char_val = 0; 
			curr_char_val = Long.parseLong(barcode.substring(i,i +1));
			Double z = new Double(String.valueOf(i + 1)) %2;
			if(z != 0.0)
				sum += (curr_char_val * 3);
			else
				sum += (curr_char_val * 1);
		}
		
		long remainder = sum % 10;
		check_digit = remainder == 0? String.valueOf(remainder): String.valueOf((10-remainder));
		return check_digit;
		
    }
   
   public static String pad_number(Long number,int required_num_length)
   {
	  String num = String.valueOf(number);
	  int pad_length = required_num_length - num.length();
	  String padded_num = "";
	  for(int i = 1; i <= pad_length; i++)
	  {
		 padded_num += "0";
		 
	  }
	  
	  return padded_num + num;
	  
   }
   
   
   public static String removeCharsFromRight(String word,int n_chars_to_remove)
   {
//	  if(word != null && word.trim()!= "")
//		 if(n_chars_to_remove < word.length())
//			return word.substring(0,word.length()- n_chars_to_remove);
//		 else
//			return word;
//	  else
		 return word;
   }
   
      public static String removeCharsFromRightPt(String word,int n_chars_to_remove)
   {
	  if(word != null && word.trim()!= "")
		 if(n_chars_to_remove < word.length())
			return word.substring(0,word.length()- n_chars_to_remove);
		 else
			return word;
	  else
		 return word;
   }
   
   
   public static String pad_number(Integer number,int required_num_length)
   {
	  String num = String.valueOf(number);
	  int pad_length = required_num_length - num.length();
	  String padded_num = "";
	  for(int i = 1; i <= pad_length; i++)
	  {
		 padded_num += "0";
		 
	  }
	  
	  return padded_num + num;
	  
	  
   }
   
   protected String getLoggedOnUsername()
   {
	  
	  return "";
	  
   }
   
   
   protected String getFormattedNowDate()
   {
	  
	  Date today;
	  DateFormat dateFormatter;
	  
	  dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.CANADA);
	  today = new Date();
	  return dateFormatter.format(today);
	  
   }
   
   
   public static void handle_exception(MessageInterface msg,String short_err,String long_err,String source,String mw_type,int error_code,
	  String production_run_code,Object stack_trace)
   {
        try {
            
            Logger.handleException(msg,short_err,long_err,source,mw_type,error_code,production_run_code,stack_trace);
        } catch(Exception e){
            e.printStackTrace();

        }
   }


   
   
   
    public static void handle_exception(MessageInterface msg,String short_err,String long_err,String source,String mw_type,int error_code,
	  String production_run_code,Object stack_trace,String ip)
   {
        try {
            
            Logger.handleException(msg,short_err,long_err,source,mw_type,error_code,production_run_code,stack_trace,ip);
        } catch(Exception e){
            e.printStackTrace();
        }
   }
   
   
	 public static void send_integration_record(String type,String record_id,String model_name) throws Exception
   {
	 
		 try
		{
			
			//-----------------------------------------------------------------------------------
			//Get the REST service url string from central configand append the method parameters
			//-----------------------------------------------------------------------------------
			Properties settings = MidwareConfig.getInstance().getSettings();
			String url_string = (String)settings.get ("integration_service_url");
			//append the parameters
			String params = "?type=" + type + "&record_id=" + record_id + "&model=" + model_name;
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
			String result = nodes.item(0).getTextContent();
			
			if(!result.toUpperCase().equals("OK"))
			   throw new Exception(result);
			
			
		} catch (Exception e)
		{
			String err = "Integration record of type: " + type + " and id: " + record_id + " could not be created";
		        //Logger.handleException(null,err,e.getMessage(),"Appservices.send_integration_record","integration",10000,"unknown",e.getStackTrace());
                        throw new Exception(err + " Reported error: " + e.getMessage().toString());
		
		}
		 
   }
   
    public static String get_bin(String bin_id,String run_id) throws Exception
   {
	 String result = "";
		 try
		{
			
			//-----------------------------------------------------------------------------------
			//Get the REST service url string from central configand append the method parameters
			//-----------------------------------------------------------------------------------
			Properties settings = MidwareConfig.getInstance().getSettings();
			String url_string = (String)settings.get ("bin_fetch_url");
			//append the parameters
			String params = "?bin_id=" + bin_id + "&run_id=" + run_id;
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
			String result_text = nodes.item(0).getTextContent();
			
			if(!result_text.toUpperCase().equals("OK"))
			   result = result_text;
               
			
			
		} catch (Exception e)
		{
			String err = "BIn with id: " + bin_id + "  for run with id: " + run_id + " could not be fetched from legacy store";
		    Logger.handleException(null,err,e.getMessage(),"Appservices.get_bin","integration",10001,"unknown",e.getStackTrace());
		    result = "bin fetch failed";
		}
         finally
         {
             return result;
         }
		 
		
   }
   
   
   
  
  
}
