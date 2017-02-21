/*****************************************************************************
 * Class: 		SysProtocol
 * Description: Application protocol
 *
 * @author 	 	Dr. J. Fourie
 */
 package	za.co.multitier.midware.sys.protocol;

public interface SysProtocol
{
    public static final int		CONCHK              = -1;

	public static final int		CONTROLCREATE		= 1;
	public static final int		CONTROLSHUTDOWN		= 2;
	public static final int		CONTROLON			= 3;
	public static final int		CONTROLOFF			= 4;
	public static final int		CONTROLTOOLTIP		= 5;
	public static final int		CONTROLNAME			= 6;
	public static final int		CONTROLLABEL		= 7;
	public static final int		CONTROLTEXT			= 8;
	public static final int		CONTROLBGCOLOR		= 9;
	public static final int		CONTROLADDEVENT		= 10;
	public static final int		CONTROLEVENT		= 11;

	public static final int		MESDASHBOARD		= 1;
	public static final int		MESMODULE			= 2;
	public static final int		MESPERIPHERAL		= 3;
	public static final int		MESROBOT			= 4;
	public static final int		MESSERVICE			= 5;

 	public static final byte[]	EOL =
    {
		(byte)'\r', (byte)'\n'
	};

	// ************************************************************************
	// System exchange protocol Constants...
	// ************************************************************************
	public static final int		SLICCHK 			= 38;
	//public static final String	XLICCHK 			= "<Mesware ";
	public static final String	TLICCHK				= "<Mesware" + " PID=\"" + SLICCHK + "\" ";

	public static final int		SSTARTED 			= 39;
	//public static final String	XSTARTED 			= "<Started ";
	public static final String	TSTARTED			= "<Started" + " PID=\"" + SSTARTED + "\" ";
	
	public static final int		SLOGON 				= 40;
	//public static final String	XLOGON 				= "<Logon ";
	public static final String	TLOGON				= "<Logon" + " PID=\"" + SLOGON + "\" ";

	public static final int		SLOGONSTATUS		= 41;
	//public static final String	XLOGONSTATUS		= "<LogonStatus ";
	public static final String	TLOGONSTATUS		= "<LogonStatus" + " PID=\"" + SLOGONSTATUS + "\" ";

	public static final int		SLOGOFF				= 42;
	//public static final String	XLOGOFF 			= "<Logoff ";
	public static final String	TLOGOFF				= "<Logoff" + " PID=\"" + SLOGOFF + "\" ";

	public static final int		SLOGOFFSTATUS		= 43;
	//public static final String	XLOGOFFSTATUS		= "<LogoffStatus ";
	public static final String	TLOGOFFSTATUS		= "<LogoffStatus" + " PID=\"" + SLOGOFFSTATUS + "\" ";

	public static final int		SREBOOT				= 44;
	//public static final String	XREBOOT 			= "<Reboot/>";
	public static final String	TREBOOT 			= "<Reboot" + " PID=\"" + SREBOOT + "\" />";

	public static final int		SSTANDBY			= 45;
	//public static final String	XSTANDBY 			= "<Standby/>";
	public static final String	TSTANDBY 			= "<Standby" + " PID=\"" + SSTANDBY + "\" />";

	public static final int		SOVERRIDE			= 46;
	//public static final String	XOVERRIDE 			= "<OverrideOk/>";
	public static final String	TOVERRIDE 			= "<OverrideOk" + " PID=\"" + SOVERRIDE + "\" />";

	public static final int		SCHGPWD 			= 47;
	//public static final String	XCHGPWD 			= "<ChangePassword ";
	public static final String	TCHGPWD				= "<ChangePassword" + " PID=\"" + SCHGPWD + "\" ";

	public static final int		SCHGPWDSTATUS		= 48;
	//public static final String	XCHGPWDSTATUS		= "<ChangePasswordStatus ";
	public static final String	TCHGPWDSTATUS		= "<ChangePasswordStatus" + " PID=\"" + SCHGPWDSTATUS + "\" ";
	/**
	 * Data = Command: The string contains data for a mode/state already activated...
	 */
	public static final int		SJUSTDATA			= 50;
	//public static final String	XJUSTDATA			= "<Data>";
	public static final String	TJUSTDATA			= "<Data PID=\"" + SJUSTDATA + "\" />";
	/**
	 * ReceiveFile = Command:	Data in filename to be transmitted to receiver...
	 *							Overwrite tag = Overwrite file
	 *
	 * Transmit EOF/EOT command at the end of transmission....
	 */
	public static final int		SHTTPRECEIVEFILE	= 51;
	//public static final String	XHTTPRECEIVEFILE	= "PUT /";
	public static final String	THTTPRECEIVEFILE	= "PUT /" + "PID=\"" + SHTTPRECEIVEFILE + "\" ";

	public static final int		SRECEIVEFILE		= 52;
	public static final int		SRECEIVEFILEA		= 52;
	public static final int		SRECEIVEFILEO		= 53;
	//public static final String	XRECEIVEFILE		= "<ReceiveFile";
	public static final String	TRECEIVEFILE		= "<ReceiveFile" + " PID=\"" + SRECEIVEFILE + "\" ";

	public static final int		SRECEIVEOBJECT		= 54;
	//public static final String	XRECEIVEOBJECT		= "<ReceiveObject";
	public static final String	TRECEIVEOBJECT		= "<ReceiveObject" + " PID=\"" + SRECEIVEOBJECT + "\" ";

	public static final int		SRECEIVETABLE		= 55;
	//public static final String	XRECEIVETABLE		= "<ReceiveTable";
	public static final String	TRECEIVETABLE		= "<ReceiveTable" + " PID=\"" + SRECEIVETABLE + "\" ";
	/**
	 * TransmitFile = Command:	Transmit the data in given filename to the requester
	 *							Delete tag = Delete file after transmit
	 *
	 * Transmit EOF/EOT command at the end of transmission (or empty file)....
	 */
	public static final int		SHTTPTRANSMITFILE	= 56;
	//public static final String	XHTTPTRANSMITFILE	= "GET /";
	public static final String	THTTPTRANSMITFILE	= "GET /" + "PID=\"" + SHTTPTRANSMITFILE + "\" ";

	public static final int		STRANSMITFILE		= 57;
	//public static final String	XTRANSMITFILE		= "<TransmitFile";
	public static final String	TTRANSMITFILE		= "<TransmitFile" + " PID=\"" + STRANSMITFILE + "\" ";

	public static final int		STRANSMITOBJECT		= 58;
	//public static final String	XTRANSMITOBJECT		= "<TransmitObject";
	public static final String	TTRANSMITOBJECT		= "<TransmitObject" + " PID=\"" + STRANSMITOBJECT + "\" ";

	public static final int		STRANSMITTABLE		= 59;
	//public static final String	XTRANSMITTABLE		= "<TransmitTable";
	public static final String	TTRANSMITTABLE		= "<TransmitTable" + " PID=\"" + STRANSMITTABLE + "\" ";
	/**
	 * ReloadFile = Command: Load the data in the given filename
	 */
	public static final int		SRELOADFILE			= 64;
	//public static final String	XRELOADFILE 		= "<ReloadFile ";
	public static final String	TRELOADFILE			= "<ReloadFile" + " PID=\"" + SRELOADFILE + "\" ";
	/**
	 * ReloadData = Command: Reload the system data
	 */
	public static final int		SRELOADDATA			= 68;
	//public static final String	XRELOADDATA 		= "<ReloadData/>";
	public static final String	TRELOADDATA 		= "<ReloadData" + " PID=\"" + SRELOADDATA + "\" />";
	/**
	 * SetDateTime = Command: Set the date and time of the receiver...
	 *		yyyy 		= year 				(note space before yyyy after '>')
	 *		mm	 		= month (1-12)
	 *		dd			= day (1-31)
	 *		hh			= hour (0-23)
	 *		min			= minutes (0-59)
	 *		ss			= seconds (0-59) 	(note space behind ss before '<')
	 */
	public static final int		SSETDATETIME		= 70;
	//public static final String	XSETDATETIME		= "<SetDateTime";
	public static final String	TSETDATETIME		= "<SetDateTime" + " PID=\"" + SSETDATETIME + "\" ";
	/**
	 * ProductBaseCount = Command: Set the product base count of the receiver...
	 */
	public static final int		SSETBASECOUNT		= 80;
	//public static final String	XSETBASECOUNT		= "<SetProductCount";
	public static final String	TSETBASECOUNT		= "<SetProductCount" + " PID=\"" + SSETBASECOUNT + "\" ";
	/**
	 * ResetProductCount = Command: Reset the product base count of the receiver (i.e. Start from 'base')...
	 */
	public static final int		SRESETPRODCOUNT 	= 81;
	//public static final String	XRESETPRODCOUNT		= "<ResetProductCount/>";
	public static final String	TRESETPRODCOUNT		= "<ResetProductCount" + " PID=\"" + SRESETPRODCOUNT + "\" />";
	/**
	 * Mesware modules and peripherals checking in from an applet as soon as connection opens...
	 */
	public static final int		SSYSTEMCHECKIN		= 85;
	//public static final String	XSYSTEMCHECKIN		= "<SystemCheckIn";
	public static final String	TSYSTEMCHECKIN		= "<SystemCheckIn" + " PID=\"" + SSYSTEMCHECKIN + "\" ";

	public static final int		SSYSTEMCHECKOUT		= 86;
	//public static final String	XSYSTEMCHECKOUT		= "<SystemCheckOut";
	public static final String	TSYSTEMCHECKOUT		= "<SystemCheckOut" + " PID=\"" + SSYSTEMCHECKOUT + "\" ";
	/**
	 * Container movement...
	 */
	public static final int		SCONTAINERMOVE		= 200;
	//public static final String	XCONTAINERMOVE		= "<ContainerMove";
	public static final String	TCONTAINERMOVE		= "<ContainerMove" + " PID=\"" + SCONTAINERMOVE + "\" ";

	public static final int		SCONTAINERMOVERF	= 201;
	//public static final String	XCONTAINERMOVERF	= "<ContainerMoveRF";
	public static final String	TCONTAINERMOVERF	= "<ContainerMoveRF" + " PID=\"" + SCONTAINERMOVERF + "\" ";
	/**
	 * Container number allocation...
	 */
	public static final int		SCONTAINERNUMBER	= 202;
	//public static final String	XCONTAINERNUMBER	= "<ContainerNumber";
	public static final String	TCONTAINERNUMBER	= "<ContainerNumber" + " PID=\"" + SCONTAINERNUMBER + "\" ";

	public static final int		SCONTAINERNUMBERRF	= 203;
	//public static final String	XCONTAINERNUMBERRF	= "<ContainerNumberRF";
	public static final String	TCONTAINERNUMBERRF	= "<ContainerNumberRF" + " PID=\"" + SCONTAINERNUMBERRF + "\" ";
 	/**
	 * Reworks...
	 */
	public static final int		SPRODUCTMOVE		= 205;
	//public static final String	XPRODUCTMOVE		= "<ProductMove";
	public static final String	TPRODUCTMOVE		= "<ProductMove" + " PID=\"" + SPRODUCTMOVE + "\" ";

	public static final int		SPRODUCTMOVERF		= 206;
	//public static final String	XPRODUCTMOVERF		= "<ProductMoveRF";
	public static final String	TPRODUCTMOVERF		= "<ProductMoveRF" + " PID=\"" + SPRODUCTMOVERF + "\" ";

	public static final int		SPRODUCTMOVEBOOT	= 207;
	//public static final String	XPRODUCTMOVEBOOT    = "<ProductMoveBoot";
	public static final String	TPRODUCTMOVEBOOT	= "<ProductMoveBoot" + " PID=\"" + SPRODUCTMOVEBOOT + "\" ";
	/**
	 * Container fill/refill...
	 */
	public static final int		SCONTAINERFILL		= 210;
	//public static final String	XCONTAINERFILL		= "<ContainerFill";
	public static final String	TCONTAINERFILL		= "<ContainerFill" + " PID=\"" + SCONTAINERFILL + "\" ";

	public static final int		SCONTAINERFILLRF	= 211;
	//public static final String	XCONTAINERFILLRF	= "<ContainerFillRF";
	public static final String	TCONTAINERFILLRF	= "<ContainerFillRF" + " PID=\"" + SCONTAINERFILLRF + "\" ";

	public static final int		SCONTAINERFILLBOOT	= 212;
	//public static final String	XCONTAINERFILLBOOT  = "<ContainerFillBoot";
	public static final String	TCONTAINERFILLBOOT	= "<ContainerFillBoot" + " PID=\"" + SCONTAINERFILLBOOT + "\" ";
	/**
	 * Cold storage...
	 */
	public static final int		SCOLDSTORAGE		= 215;
	//public static final String	XCOLDSTORAGE		= "<ColdStorage";
	public static final String	TCOLDSTORAGE		= "<ColdStorage" + " PID=\"" + SCOLDSTORAGE + "\" ";

	public static final int		SCOLDSTORAGEBOOT	= 216;
	//public static final String	XCOLDSTORAGEBOOT	= "<ColdStorageBoot";
	public static final String	TCOLDSTORAGEBOOT	= "<ColdStorageBoot" + " PID=\"" + SCOLDSTORAGEBOOT + "\" ";
	/**
	 * Product sorting...
	 */
	public static final int		SPRODUCTSORT		= 220;
	//public static final String	XPRODUCTSORT		= "<ProductSort";
	public static final String	TPRODUCTSORT		= "<ProductSort" + " PID=\"" + SPRODUCTSORT + "\" ";

	public static final int		SPRODUCTSORTRF		= 221;
	//public static final String	XPRODUCTSORTRF		= "<ProductSortRF";
	public static final String	TPRODUCTSORTRF		= "<ProductSortRF" + " PID=\"" + SPRODUCTSORTRF + "\" ";

	public static final int		SPRODUCTSORTBOOT	= 222;
	//public static final String	XPRODUCTSORTBOOT	= "<ProductSortBoot";
 	public static final String	TPRODUCTSORTBOOT	= "<ProductSortBoot" + " PID=\"" + SPRODUCTSORTBOOT + "\" ";
	/**
	 * Product labelling 'on demand'...
	 */
	public static final int		SPRODUCTLABEL		= 223;
	//public static final String	XPRODUCTLABEL		= "<ProductLabel";
	public static final String	TPRODUCTLABEL		= "<ProductLabel" + " PID=\"" + SPRODUCTLABEL + "\" ";

	public static final int		SPRODUCTLABELRF		= 224;
	//public static final String	XPRODUCTLABELRF		= "<ProductLabelRF";
	public static final String	TPRODUCTLABELRF		= "<ProductLabelRF" + " PID=\"" + SPRODUCTLABELRF + "\" ";

	public static final int		SPRODUCTLABELLAN	= 225;
	//public static final String	XPRODUCTLABELLAN	= "<ProductLabelLAN";
	public static final String	TPRODUCTLABELLAN	= "<ProductLabelLAN" + " PID=\"" + SPRODUCTLABELLAN + "\" ";

	public static final int		SPRODUCTLABELBOOT	= 226;
	//public static final String	XPRODUCTLABELBOOT	= "<ProductLabelBoot";
	public static final String	TPRODUCTLABELBOOT	= "<ProductLabelBoot" + " PID=\"" + SPRODUCTLABELBOOT + "\" ";
	/**
	 * Product labelling 'batch'...
	 */
    public static final int		SPRODUCTLABELB		= 228;
	//public static final String	XPRODUCTLABELB		= "<ProductLabelBatch";
	public static final String	TPRODUCTLABELB		= "<ProductLabelBatch" + " PID=\"" + SPRODUCTLABELB + "\" ";

    public static final int		SPRODUCTLABELBRF	= 229;
	//public static final String	XPRODUCTLABELBRF	= "<ProductLabelBatchRF";
	public static final String	TPRODUCTLABELBRF	= "<ProductLabelBatchRF" + " PID=\"" + SPRODUCTLABELBRF + "\" ";
 	/**
	 * Palletizing...
	 */
	public static final int		SPALLETIZE          = 230;
	//public static final String	XPALLETIZE          = "<Palletize";
	public static final String	TPALLETIZE			= "<Palletize" + " PID=\"" + SPALLETIZE + "\" ";

	public static final int		SPALLETIZERF        = 231;
	//public static final String	XPALLETIZERF        = "<PalletizeRF";
	public static final String	TPALLETIZERF		= "<PalletizeRF" + " PID=\"" + SPALLETIZERF + "\" ";

	public static final int		SPALLETIZEBOOT      = 232;
	//public static final String	XPALLETIZEBOOT		= "<PalletizeBoot";
	public static final String	TPALLETIZEBOOT		= "<PalletizeBoot" + " PID=\"" + SPALLETIZEBOOT + "\" ";
	/**
	 * Number request & allocation...
	 */
	public static final int		SREQUESTSERVER		= 233;
	//public static final String	XREQUESTSERVER		= "<RequestServer";
	public static final String	TREQUESTSERVER		= "<RequestServer" + " PID=\"" + SREQUESTSERVER + "\" ";

	public static final int		SREQUESTSERVERRF	= 234;
	//public static final String	XREQUESTSERVERRF	= "<RequestServerRF";
	public static final String	TREQUESTSERVERRF	= "<RequestServerRF" + " PID=\"" + SREQUESTSERVERRF + "\" ";
	/**
	 * Quality control...
	 */
	public static final int		SQUALITYCONTROL		= 235;
	//public static final String	XQUALITYCONTROL		= "<QualityControl";
	public static final String	TQUALITYCONTROL		= "<QualityControl" + " PID=\"" + SQUALITYCONTROL + "\" ";

	public static final int		SQUALITYCONTROLRF	= 236;
	//public static final String	XQUALITYCONTROLRF	= "<QualityControlRF";
	public static final String	TQUALITYCONTROLRF	= "<QualityControlRF" + " PID=\"" + SQUALITYCONTROLRF + "\" ";
	/**
	 * Number request & allocation...
	 */
	public static final int		SDOCUMENTSERVER		= 238;
	//public static final String	XDOCUMENTSERVER		= "<DocumentServer";
	public static final String	TDOCUMENTSERVER		= "<DocumentServer" + " PID=\"" + SDOCUMENTSERVER + "\" ";

	public static final int		SDOCUMENTSERVERRF	= 239;
	//public static final String	XDOCUMENTSERVERRF	= "<DocumentServerRF";
	public static final String	TDOCUMENTSERVERRF	= "<DocumentServerRF" + " PID=\"" + SDOCUMENTSERVERRF + "\" ";
 	/**
	 * Drillsite...
	 */
	public static final int		SDRILLSITE          = 280;
	//public static final String	XDRILLSITE          = "<Drillsite";
	public static final String	TDRILLSITE			= "<Drillsite" + " PID=\"" + SDRILLSITE + "\" ";

	public static final int		SDRILLSITERF        = 281;
	//public static final String	XDRILLSITERF        = "<DrillsiteRF";
	public static final String	TDRILLSITERF		= "<DrillsiteRF" + " PID=\"" + SDRILLSITERF + "\" ";
    /**
	 * Remote stand alone printing...
	 */
	public static final int		SPRINTER            = 300;
	//public static final String	XPRINTER            = "<Printer";
	public static final String	TPRINTER			= "<Printer" + " PID=\"" + SPRINTER + "\" ";

	public static final int		SPRINTERRF			= 301;
	//public static final String	XPRINTERRF          = "<PrinterFormat";
 	public static final String	TPRINTERRF			= "<PrinterFormat" + " PID=\"" + SPRINTERRF + "\" ";
    /**
	 * Scale definition...
	 */
	public static final int		SSCALE              = 310;
	//public static final String	XSCALE              = "<Scale";
	public static final String	TSCALE				= "<Scale" + " PID=\"" + SSCALE + "\" ";
	/**
	 * Scale transactions...
	 */
	public static final int		SSCALEDATA			= 311;
	//public static final String	XSCALEDATA			= "<ScaleData ";
	public static final String	TSCALEDATA			= "<ScaleData" + " PID=\"" + SSCALEDATA + "\" ";
    /**
	 * Scanner definition...
	 */
	public static final int		SSCANNER            = 312;
	//public static final String	XSCANNER            = "<Scanner";
	public static final String	TSCANNER			= "<Scanner" + " PID=\"" + SSCANNER + "\" ";
	/**
	 * Scanner transactions...
	 */
	public static final int		SSCANNERDATA        = 313;
	//public static final String	XSCANNERDATA		= "<ScannerData ";
	public static final String	TSCANNERDATA		= "<ScannerData" + " PID=\"" + SSCANNERDATA + "\" ";
	/**
	 * RF SCanner definition...
	 */
	public static final int		SSCANNERRF          = 316;
	//public static final String	XSCANNERRF          = "<ScannerRF";
	public static final String	TSCANNERRF			= "<ScannerRF" + " PID=\"" + SSCANNERRF + "\" ";

	public static final int		SPDTRF				= 317;
	//public static final String	XPDTRF              = "<PDTRF";
	public static final String	TPDTRF				= "<PDTRF" + " PID=\"" + SPDTRF + "\" ";

	public static final int		SSCANNERRFBOOT      = 318;
	//public static final String	XSCANNERRFBOOT      = "<ScannerRFBoot";
	public static final String	TSCANNERRFBOOT		= "<ScannerRFBoot" + " PID=\"" + SSCANNERRFBOOT + "\" ";
	/**
	 * E-Mail...
	 */
	public static final int		SMAIL				= 500;
	public static final String	XMAILSTART			= "<MailStart>";
	public static final String	XMAILLINE			= "<MailLine";		// <MailLine Line="" />
	public static final String	XMAILEND			= "</MailEnd>";
	/**
	 * SMS's...
	 */
	public static final int		SSMS				= 501;
	//public static final String	XSMS				= "<Sms";
	public static final String	TSMS				= "<Sms" + " PID=\"" + SSMS + "\" ";
	/**
	 * EDI's...
	 */
	public static final int		SEDI				= 510;
	//public static final String	XEDI				= "<Edi";
	public static final String	TEDI				= "<Edi" + " PID=\"" + SEDI + "\" ";
	/**
	 * Messages's...
	 */
	public static final int		SMSG				= 601;
	//public static final String	XMSG				= "<DisplayMessage";
	public static final String	TMSG				= "<DisplayMessage" + " PID=\"" + SMSG + "\" ";

	public static final int		STIMEOUT			= 602;
	//public static final String	XTIMEOUT			= "<TimeoutMessage";
	public static final String	TTIMEOUT			= "<TimeoutMessage" + " PID=\"" + STIMEOUT + "\" ";

	public static final int		SSYSTEMFORMAT		= 603;
	//public static final String	XSYSTEMFORMAT		= "<SystemFormat";
	public static final String	TSYSTEMFORMAT		= "<SystemFormat" + " PID=\"" + SSYSTEMFORMAT + "\" ";

	public static final int		STIO				= 640;
	//public static final String	XTIO 				= "<Tio/>";
	public static final String	TTIO				= "<Tio PID=\"" + STIO + "\" />";

	public static final int		STIMESYNC			= 680;
	//public static final String	XTIMESYNC 			= "<TimeSync/>";
	public static final String	TTIMESYNC			= "<TimeSync PID=\"" + STIMESYNC + "\" />";

	public static final int		SRELOADCONFIG		= 700;
	//public static final String	XRELOADCONFIG 		= "<ReloadConfig/>";
	public static final String	TRELOADCONFIG		= "<ReloadConfig PID=\"" + SRELOADCONFIG + "\" />";

	public static final int		SMENUSELECT         = 800;
	//public static final String	XMENUSELECT 		= "<MenuSelect";
	public static final String	TMENUSELECT			= "<MenuSelect" + " PID=\"" + SMENUSELECT + "\" ";
	/**
	 * Shutdown
	 */
    public static final int		SSHUTDOWN 			= 998;
	//public static final String	XSHUTDOWN 			= "<ShutDown/>";
	public static final String	TSHUTDOWN			= "<ShutDown PID=\"" + SSHUTDOWN + "\" />";
	/**
	 * Personnel...
	 */
	public static final int		SDBMS				= 1000;
	//public static final String	XDBMS				= "<DBMS";
	public static final String	TDBMS				= "<DBMS" + " PID=\"" + SDBMS + "\" ";
	
	// *************************************************************************
	// Web transactions
	// *************************************************************************

	public static final int		SDATATAG			= 12000;
	public static final String	XDATATAG			= "<Data>";

	public static final int		SDATATAGA			= 12001;
	//public static final String	XDATATAGA			= "<Data";
	public static final String	TDATATAGA			= "<Data" + " PID=\"" + SDATATAGA + "\" ";
	/**
	 * Button definition...
	 */
	public static final int		SBUTTON				= 12011;
	//public static final String	XBUTTON				= "<Button ";
	public static final String	TBUTTON				= "<Button" + " PID=\"" + SBUTTON + "\" ";
	/**
	 * Button transactions...
	 */
	public static final int		SBUTTONDATA			= 12012;
	//public static final String	XBUTTONDATA			= "<ButtonData ";
	public static final String	TBUTTONDATA			= "<ButtonData" + " PID=\"" + SBUTTONDATA + "\" ";
	/**
	 * Sensor definition...
	 */
	public static final int		SSENSOR				= 12013;
	//public static final String	XSENSOR				= "<Sensor ";
	public static final String	TSENSOR				= "<Sensor" + " PID=\"" + SSENSOR + "\" ";
	/**
	 * Sensor transactions...
	 */
	public static final int		SSENSORDATA         = 12014;
	//public static final String	XSENSORDATA         = "<SensorData ";
	public static final String	TSENSORDATA			= "<SensorData" + " PID=\"" + SSENSORDATA + "\" ";
	/**
	 * Probe definition...
	 */
	public static final int		SPROBE				= 12015;
	//public static final String	XPROBE				= "<Probe ";
	public static final String	TPROBE				= "<Probe" + " PID=\"" + SPROBE + "\" ";
	/**
	 * Probe transactions...
	 */
	public static final int		SPROBEDATA			= 12016;
	//public static final String	XPROBEDATA			= "<ProbeData ";
	public static final String	TPROBEDATA			= "<ProbeData" + " PID=\"" + SPROBEDATA + "\" ";
   /**
	 * Relay definition...
	 */
	public static final int		SRELAY				= 12017;
	public static final String	XRELAY				= "<Relay ";
	public static final String	TRELAY				= "<Relay" + " PID=\"" + SRELAY + "\" ";
   /**
	 * Relay transactions...
	 */
	public static final int		SRELAYDATA          = 12018;
	//public static final String	XRELAYDATA          = "<RelayData ";
	public static final String	TRELAYDATA			= "<RelayData" + " PID=\"" + SRELAYDATA + "\" ";

	// ************************************************************************
	// Exchange protocol Constants...
	// ************************************************************************
	public static final int		SNUL				= 20000;
//	public static final String	XNUL 				= "<Nul/>";
	public static final String	TNUL				= "<Nul PID=\"" + SNUL + "\" />";

	public static final int		SACK				= 20001;
//	public static final String	XACK 				= "<Ack/>";
	public static final String	TACK				= "<Ack PID=\"" + SACK + "\" />";

	public static final int		SEOF				= 20002;
//	public static final String	XEOF 				= "<Eof/>";
	public static final String	TEOF				= "<Eof PID=\"" + SEOF + "\" />";

	public static final int		SETX				= 20003;
//	public static final String	XETX 				= "<Etx/>";
	public static final String	TETX				= "<Etx PID=\"" + SETX + "\" />";

	public static final int		SEOT				= 20004;
//	public static final String	XEOT 				= "<Eot/>";
	public static final String	TEOT				= "<Eot PID=\"" + SEOT + "\" />";

	public static final int		SNAK				= 20005;
//	public static final String	XNAK 				= "<Nak/>";
	public static final String	TNAK				= "<Nak PID=\"" + SNAK + "\" />";

	public static final int		SSOH				= 20006;
//	public static final String	XSOH 				= "<Soh/>";
	public static final String	TSOH				= "<Soh PID=\"" + SSOH + "\" />";

	public static final int		SSTX				= 20007;
//	public static final String	XSTX 				= "<Stx/>";
	public static final String	TSTX				= "<Stx PID=\"" + SSTX + "\" />";
} // SysProtocol
