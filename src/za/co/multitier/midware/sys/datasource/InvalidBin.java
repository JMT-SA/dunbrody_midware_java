/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author hans
 */
import java.sql.Date;

public class InvalidBin {

    private String bin_id;
        private String error_description;


	private String production_schedule_name;

	private String production_run_code;

	private String line_code;

	private java.sql.Timestamp tipped_date_time;

	private java.sql.Timestamp error_date_time;

	private String authorisor_name;

	private int production_run_id;

	//---------
	//ACCESSORS
	//---------
	public String getBin_id()
	{
		return bin_id;
	}

	public void setBin_id(String bin_id)
	{
		this.bin_id = bin_id;
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

	public java.sql.Timestamp getTipped_date_time()
	{
		return tipped_date_time;
	}

	public void setTipped_date_time(java.sql.Timestamp tipped_date_time)
	{
		this.tipped_date_time = tipped_date_time;
	}

	public String getAuthorisor_name()
	{
		return authorisor_name;
	}

	public void setAuthorisor_name(String authorisor_name)
	{
		this.authorisor_name = authorisor_name;
	}

	public int getProduction_run_id()
	{
		return production_run_id;
	}

	public void setProduction_run_id(int production_run_id)
	{
		this.production_run_id = production_run_id;
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

	private int error_code;

	public int getError_code()
	{
		return error_code;
	}

	public void setError_code(int error_code)
	{
		this.error_code = error_code;
	}

	private Integer id;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	private Double weight;

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	private Double bin_weight;

	public Double getBin_weight() {
		return bin_weight;
	}

	public void setBin_weight(Double bin_weight) {
		this.bin_weight = bin_weight;
	}

	private String farm_code;

	private String delivery_no;

	private String track_indicator_code;

	private String class_description;

	private java.sql.Timestamp bin_receive_datetime;

	public java.sql.Timestamp getBin_receive_datetime() {
		return bin_receive_datetime;
	}

	public void setBin_receive_datetime(java.sql.Timestamp bin_receive_datetime) {
		this.bin_receive_datetime = bin_receive_datetime;
	}

	public String getClass_description() {
		return class_description;
	}

	public void setClass_description(String class_description) {
		this.class_description = class_description;
	}

	public String getDelivery_no() {
		return delivery_no;
	}

	public void setDelivery_no(String delivery_no) {
		this.delivery_no = delivery_no;
	}

	public String getFarm_code() {
		return farm_code;
	}

	public void setFarm_code(String farm_code) {
		this.farm_code = farm_code;
	}

	public String getTrack_indicator_code() {
		return track_indicator_code;
	}

	public void setTrack_indicator_code(String track_indicator_code) {
		this.track_indicator_code = track_indicator_code;
	}

	

}
