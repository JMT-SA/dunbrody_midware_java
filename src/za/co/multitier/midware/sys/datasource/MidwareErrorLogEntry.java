/*
 * MidwareErrorLog.java
 *
 * Created on February 5, 2007, 11:18 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class MidwareErrorLogEntry
{
	
	//============
	//ERROR CODES
	//============
	public static final int SYSTEM_ERROR = 1;
	public static final int INVALID_BIN_REMOVAL = 1;
	public static final int INVALID_BIN_CANCELLED_WITH_VALID_BINSCAN = 1;
	
	
	/** Creates a new instance of MidwareErrorLog */
	public MidwareErrorLogEntry()
	{
	}

	private String object_id;

	private String production_schedule_name;

	private String production_run_code;

	private String line_code;

	private String farm_code;

	private String error_description;

	private java.sql.Timestamp error_date_time;

	private String authorisor_name;

	private int error_code;

	private String mw_type;

	public String getObject_id()
	{
		return object_id;
	}

	public void setObject_id(String object_id)
	{
		this.object_id = object_id;
	}

	public String getProduction_schedule_name()
	{
		return production_schedule_name;
	}

	public void setProduction_schedule_name(String production_schedule_name)
	{
		this.production_schedule_name = production_schedule_name;
	}

	public String getProduction_run_code()
	{
		return production_run_code;
	}

	public void setProduction_run_code(String production_run_code)
	{
		this.production_run_code = production_run_code;
	}

	public String getLine_code()
	{
		return line_code;
	}

	public void setLine_code(String line_code)
	{
		this.line_code = line_code;
	}

	public String getFarm_code()
	{
		return farm_code;
	}

	public void setFarm_code(String farm_code)
	{
		this.farm_code = farm_code;
	}

	public String getError_description()
	{
		return error_description;
	}

	public void setError_description(String error_description)
	{
		this.error_description = error_description;
	}

	public java.sql.Timestamp getError_date_time()
	{
		return error_date_time;
	}

	public void setError_date_time(java.sql.Timestamp error_date_time)
	{
		this.error_date_time = error_date_time;
	}

	public String getAuthorisor_name()
	{
		return authorisor_name;
	}

	public void setAuthorisor_name(String authorisor_name)
	{
		this.authorisor_name = authorisor_name;
	}

	public int getError_code()
	{
		return error_code;
	}

	public void setError_code(int error_code)
	{
		this.error_code = error_code;
	}

	public String getMw_type()
	{
		return mw_type;
	}

	public void setMw_type(String mw_type)
	{
		this.mw_type = mw_type;
	}

	private int production_run_id;

	public int getProduction_run_id()
	{
		return production_run_id;
	}

	public void setProduction_run_id(int production_run_id)
	{
		this.production_run_id = production_run_id;
	}

	private String stack_trace;

	public String getStack_trace()
	{
		return stack_trace;
	}

	public void setStack_trace(String stack_trace)
	{
		this.stack_trace = stack_trace;
	}

	private String device_ip;

	public String getDevice_ip()
	{
		return device_ip;
	}

	public void setDevice_ip(String device_ip)
	{
		this.device_ip = device_ip;
	}

	private String short_description;

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}
	
}
