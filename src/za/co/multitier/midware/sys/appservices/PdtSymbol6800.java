/*
 * PdtSymbol6800.java
 *
 * Created on June 22, 2007, 3:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.appservices;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;

import za.co.multitier.midware.sys.protocol.SysProtocol;

/**
 *
 * @author Administrator
 */
public class PdtSymbol6800
{


      public static String encode_url(String raw_url)
    {
        try{
                String encodedurl = URLEncoder.encode(raw_url,"UTF-8");
                //System.out.println(encodedurl);
                return encodedurl;
          }catch(UnsupportedEncodingException uee){
           System.err.println(uee);
           return "";
          }

    }
    
     public static String remote_call(String mode,String transaction_type,String ip, String user,String screen_xml)
   {
	      String url_string = "";
          String result = "";
           InputStream instream = null;
          
		 try
		{
			
			//-----------------------------------------------------------------------------------
			//Get the RES  T service url string from central configand append the method parameters
			//-----------------------------------------------------------------------------------
			Properties settings = MidwareConfig.getInstance().getSettings();
			String base_url = (String)settings.get ("pdt_server");
			 url_string = base_url + "/services/pdt/handle_request";
			//append the parameters
			 
                   
                        
			String params = "?mode=" + mode + "&trans_type=" + transaction_type + "&user=" + user + "&ip=" + ip + "&input=" + screen_xml;
			
			   
			url_string += params;
			
			//-----------------------------------
			//Make the REST service call
			//-----------------------------------
			URL url = new URL(encode_url(url_string));
			instream = url.openStream();

			//----------------------------------------------------
			//Parse the xml result string for the call return value
			//----------------------------------------------------
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
			DocumentBuilder builder = factory.newDocumentBuilder ();
			org.w3c.dom.Document doc = builder.parse(instream);
			NodeList nodes = doc.getElementsByTagName("result");
		       result = nodes.item(0).getTextContent();
			
	        return result;
			
			
		} catch (Exception e)
		{
			result =
						SysProtocol.TPDTRF +
                        " Status=\"true\""  + 
                        " Type=\""          + transaction_type + 
                        " Status=\"true\" Msg=\"Transaction OK\" " +
                        " LCD1=\"Remote call failed\" LCD2=\"Good girl...\" LCD7=\"Next please...\" " +
                        " Input1=\" \" Input1Enable=\"false\" " +
                        " Input2=\" \" Input2Enable=\"false\" " +
                        " Input3=\" \" Input3Enable=\"false\" />";
                        
		    Logger.handleException(null,"remote call exception",e.getMessage(),"Appservices.PdtSymbol6800","PdtSymbol6800",20000,"unknown",e.getStackTrace(),ip);
		  
			return result; 
		}

        finally
        {
            try {
                if(instream != null)
                instream.close();
            } catch (IOException ex) {
                //Logger.getLogger(PdtSymbol6800.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
          
            
		 
	}
    
    public static String handle_request(String mode,String transaction_type,String scancode1, String scancode2,String mass,String ip)
   {
	  String url_string = "";
      InputStream instream = null;
		 try
		{
			
			//-----------------------------------------------------------------------------------
			//Get the RES  T service url string from central configand append the method parameters
			//-----------------------------------------------------------------------------------
			Properties settings = MidwareConfig.getInstance().getSettings();
			String base_url = (String)settings.get ("base_services_url");
			 url_string = base_url + "symbol_pdt6800/handle_request";
			//append the parameters
			 
                         if(!mode.equals("4"))
			  scancode1 = DeviceScan.removeCharsFromRightPt(scancode1,1);
                        
			String params = "?mode=" + mode + "&trans_type=" + transaction_type + "&scancode1=" + scancode1;
			if(scancode2 != null)
			{
			   scancode2 = DeviceScan.removeCharsFromRightPt(scancode2,1);
			   params += "&scancode2=" + scancode2; 
			}
			
			if(mass != null)
			   params += "&mass=" + mass; 
			   
			   
			url_string += params;
			
			//-----------------------------------
			//Make the REST service call
			//-----------------------------------
			URL url = new URL(url_string);
			instream = url.openStream();

			//----------------------------------------------------
			//Parse the xml result string for the call return value
			//----------------------------------------------------
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
			DocumentBuilder builder = factory.newDocumentBuilder ();
			org.w3c.dom.Document doc = builder.parse(instream);
			NodeList nodes = doc.getElementsByTagName("result");
			String result = nodes.item(0).getTextContent();
			
	        return result;
			
			
		} catch (Exception e)
		{
			String err = "Pdt_symbol_6800 transaction failed. Service url was: " + url_string;
		    Logger.handleException(null,err,e.getMessage(),"Appservices.PdtSymbol6800","PdtSymbol6800",20000,"unknown",e.getStackTrace(),ip);
		    String instruction = "<%s Status=\"%s\" LCD1=\"%s\" LCD2=\"%s\" LCD3=\"%s\"  LCD4=\"%s\"  LCD5=\"%s\" LCD6=\"%s\" />";
			
		    String result = String.format(instruction, transaction_type, "true", "UNEXPECTED CLIENT", "EXCEPTION OCCURRED", "CONTACT IT", "", "", "");
		  int i = 5;
			return result; 
		}

        finally
        {
            try {
                if(instream != null)
                instream.close();
            } catch (IOException ex) {
                //Logger.getLogger(PdtSymbol6800.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
          
            
		 
	}
   
   /** Creates a new instance of PdtSymbol6800 */
   public PdtSymbol6800()
   {
   }
   
}
