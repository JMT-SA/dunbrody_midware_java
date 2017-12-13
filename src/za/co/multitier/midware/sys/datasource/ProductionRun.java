/*
 * ProductionRun.java
 *
 * Created on February 6, 2007, 5:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class ProductionRun
{
	
	/** Creates a new instance of ProductionRun */
	public ProductionRun()
	{
	}

	private int id;

    private String season;

	private int day_line_batch_number;

	private int line_id;

	private String line_code;



	private String production_run_status;

	private int production_run_number;

	private String farm_code;

	private String account_code;

	private String puc_code;

	private String production_run_code;

	private String orchard_code;

	private String gap;

	private String gln;



	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}



	public int getDay_line_batch_number()
	{
		return day_line_batch_number;
	}

	public void setDay_line_batch_number(int day_line_batch_number)
	{
		this.day_line_batch_number = day_line_batch_number;
	}

	public int getLine_id()
	{
		return line_id;
	}

	public void setLine_id(int line_id)
	{
		this.line_id = line_id;
	}

	public String getLine_code()
	{
		return line_code;
	}

	public void setLine_code(String line_code)
	{
		this.line_code = line_code;
	}



	public String getProduction_run_status()
	{
		return production_run_status;
	}

	public void setProduction_run_status(String production_run_status)
	{
		this.production_run_status = production_run_status;
	}

	public int getProduction_run_number()
	{
		return production_run_number;
	}

	public void setProduction_run_number(int production_run_number)
	{
		this.production_run_number = production_run_number;
	}

	public String getFarm_code()
	{
		return farm_code;
	}

	public void setFarm_code(String farm_code)
	{
		this.farm_code = farm_code;
	}

	public String getAccount_code()
	{
		return account_code;
	}

	public void setAccount_code(String account_code)
	{
		this.account_code = account_code;
	}

	public String getPuc_code()
	{
		return puc_code;
	}

	public void setPuc_code(String puc_code)
	{
		this.puc_code = puc_code;
	}

	public String getProduction_run_code()
	{
		return production_run_code;
	}

	public void setProduction_run_code(String production_run_code)
	{
		this.production_run_code = production_run_code;
	}





	private String batch_code;

	public String getBatch_code()
	{
		return batch_code;
	}

	public void setBatch_code(String batch_code)
	{
		this.batch_code = batch_code;
	}


    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

	public String getOrchard_code() {
		return orchard_code;
	}

	public void setOrchard_code(String orchard_code) {
		this.orchard_code = orchard_code;
	}

	public String getGap() {
		return gap;
	}

	public void setGap(String gap) {
		this.gap = gap;
	}

	public String getGln() {
		return gln;
	}

	public void setGln(String gln) {
		this.gln = gln;
	}
}
