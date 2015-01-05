/*
 * PalletTemplateNotFoundException.java
 *
 * Created on February 23, 2007, 5:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.appservices;

/**
 *
 * @author Administrator
 */
public class PalletizingException extends Exception
{
	public String short_msg;
	public PalletizingException(String short_msg,String long_msg,za.co.multitier.mesware.messages.MessageInterface console,
		                        String skip_ip,String bay_num,String carton_scanned,String button_pressed)
	{
		super(long_msg);
		this.short_msg = short_msg;
		
		DeviceScan.handle_exception(console,"Palletizing action could not be processed(skip ip: " + skip_ip + ", bay number: " + bay_num + ", carton scanned: " + carton_scanned + ", button pressed: " + button_pressed.toString() + ") .Reported exception: " ,
				                       this.toString(),"PalletizingAction.ProcessAction",DeviceTypes.PALLETISING,0,"",this.getStackTrace());
	}
	
}
