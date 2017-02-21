/** *********************** J&J Multi-Tier (Pty) Ltd.************************
 * Class: 		SysStart
 *
 * @author: 	Dr. J. Fourie
 * @version	 	Rev 1.00
 *
 * Date: 		Sept 2005
 ** *************************************************************************/
package	za.co.multitier.midware.sys;

import java.io.File;
import java.util.*;

/*import za.co.multitier.mesware.factories.*;
import za.co.multitier.mesware.sys.mwcm.Cm;
import za.co.multitier.mesware.sys.mwcsm.Csm;
import za.co.multitier.mesware.sys.mwpl.Pl;
import za.co.multitier.mesware.sys.mwpt.Pt;
//import za.co.multitier.mesware.sys.mwpl.ClmPl;
import za.co.multitier.mesware.sys.mwserver.Server;
import za.co.multitier.mesware.sys.mwrobot.Robot;
import za.co.multitier.sys.SysInterface;    */

/**
 * Description:	J&J Mesware Startup for the mesware systems
 */
public class SysStart
{
	// ************************************************************************
	//
	// Run: $BaseName d:\JmtMesWare mwserver ism clmpl mw(cm cf ps pl pt sz).xml
	//
	// ************************************************************************

	static  String Args[]	= { null, null, null, "false" };


	public static void main(String[] bargs)
	{
//		Map	sysTypeMap		= new HashMap();
//		boolean	oK			= false;
//		boolean unixOS		= false;
//
//                String [] args = {"C:/jmt","mwserver","true"};
//                args[0] = "C:/jmt";
//                args[1] = "mwserver";
//                args[2] = "true";
//
//
//		if (args.length < 2)									// Checking the arguments provided
//		{
//			String[] sysType = MapFactory.getStringArrayOrderedIndex(sysTypeMap);
//			int l = sysType.length;
//
//			System.out.println("System parameters required:");
//			System.out.println("    HomeDir  SystemType true/false");
//			System.out.println("    Where:");
//			System.out.println("        HomeDir:            Pathname to main System folder  (Mandatory)");
//
//			System.out.print(  "        System Types:  		(Mandatory)");
//			for (int i = 0; i < l; i++)
//				System.out.println("          " + sysType[i]);
//
//			System.out.println("        true/false:         Display/ignore sys messages during start (Optional)");
//			System.exit(0);
//		}
//
//        args[1].toLowerCase();
//		Args[0] = args[0] + "/" + args[1];                      // Home/root directory...
//		Args[1] = Args[0] + "/config/" + args[1] + ".xml";		// Configuration file name...
//		Args[2] = args[1];										// System type...
//
//		if (args.length == 3)									// Messages on/off boollean...
//		    Args[3] = args[2];
//
//		if (!MapFactory.retrieveMap(sysTypeMap, Args[0] + "/config/" + "SystemTypes.properties", false, ""))
//			System.exit(1);										// Properties file not found
//
//		if (sysTypeMap.isEmpty())
//		{
//			System.out.println("The file 'Systems/SystemTypes.properties' contains no valid entries...");
//			System.exit(1);
//		}
//
//        if (!sysTypeMap.containsKey(args[1]))                   // Check out the system type...
//        {
//            System.out.println(
//                "Invalid system type (" + args[1] + ") provided... ");
//            System.exit(1);
//        }
//		//
//		// Check OS Detail
//		//
//		String osName = System.getProperty("os.name");
//
//		if (osName.equals("SunOS") ||			// Unix
//			osName.equals("Solaris") ||
//			osName.equals("Linux"))
//		{
//			unixOS = true;
//		}
//
//		System.out.println("Operating system: " + osName);
//	   	System.out.println("Parameters: " +
//	    		Args[0] + " " +
//				Args[1] + " " +
//	    		Args[2] + " " +
//				Args[3]);
//		//
//		// Check the config file and then try to start the J&J MesWare system
//		//
//		File d = new File(Args[0]);
//		if (d.exists())
//		{
//			File f = new File(Args[1]);
//			if (f.exists())
//			{
//				SysInterface fs = null;
//
//                if (args[1].equals("mwserver"))		            // Server system = "mwserver"...
//                {
//                    fs = new Server();
//                }
//                else if (args[1].equals("mwrobot"))             // Server system = "mwrobot"...
//                {
//                    fs = new Robot();
//                }
//				else if (args[1].equals("mwcm"))		        // Container movement = "mwcm"...
//                {
//					fs = new Cm();
//                }
//				else if (args[1].equals("mwcf"))		        // Container re-fill = "mwcf"...
//                {
//                }
//				else if (args[1].equals("mwcsm"))		        // Cold Storage = "mwcsm"...
//                {
//					fs = new Csm();
//                }
//				else if (args[1].equals("mwps"))		        // Product sorting = "mwps"...
//                {
//					if (unixOS)									// Unix
//					{
//						System.out.println("Application for Windows only: " + osName);
//						System.exit(1);
//					}
//
//                    // fs = new Ps();
//                }
//                else if (args[1].equals("mwpl"))				// Product labelling = "mwpl" (new system)...
//                {
//                    fs = new Pl();
//                }
//
//                else if (args[1].equals("mwpt"))                // Palletizing = "mwpt"...
//                {
//					if (unixOS)									// Unix
//					{
//						System.out.println("Application for Windows only: " + osName);
//						System.exit(1);
//					}
//
//                    fs = new Pt();
//                }
//                else
//                {
//                    String s = "System Type (" + args[2] + ") not recognized...";
//                    System.out.println(s);
//                }
//
//				if (fs != null)
//				{
//					oK = true;
//					if (fs.setUp(args, osName))
//					{
//						//oK = fs.runSystem();	// 27 June 2010... implemented Threading...
//					}
//					//else
//					//	fs.shutDown(false);		// 27 June 2010...
//				}
//			}
//			else
//				System.out.println(
//					"The configuration file (" + args[1] + ") does not exist...");
//		}
//		else
//			System.out.println(
//				"The system directory (" + args[0] + ") does not exist...");
//
//		if (oK)
//			System.out.println("System startup completed...");
//		else
//			System.out.println("System startup not completed...");
//
//		System.gc();
	} // main()
	// ************************************************************************
} // SysStart

/* ****************************************************************************
 * @author    	classes and interfaces only, required
 * @version   	classes and interfaces only, required
 * @since
 * @see			classes, variable & Methods - Hyperlinks to other classes
 * @param  		methods and constructors only
 * @return 		methods only
 * @exception
 * @throws
 * @deprecated
 * ****************************************************************************/
