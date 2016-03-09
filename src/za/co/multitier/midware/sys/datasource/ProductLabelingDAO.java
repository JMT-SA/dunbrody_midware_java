/*
 * DevicesDAO.java
 *
 * Created on January 25, 2007, 1:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

import com.mindprod.common11.BigDate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class ProductLabelingDAO
{
   
	
	public enum MesObjectTypes {NONE,CARTON,REBIN,PALLET};
	

	public static FgSetup getFgSetup(int fg_setup_id) throws Exception
	{
		try
		{


            FgSetup fg_setup = (FgSetup) DataSource.getSqlMapInstance().queryForObject("getFgSetup", fg_setup_id);
			return fg_setup;
		} catch (SQLException ex)
		{
			throw new Exception("Fg setup be fetched. Reported exception: " + ex);
		}

	}


	public static List<LabelTemplateField> getLabelTemplateField(String template_name) throws Exception
	{
		try
		{

			List<LabelTemplateField> template_fields = (List) DataSource.getSqlMapInstance().queryForList("getLabelTemplateField", template_name);
			return template_fields;
		} catch (SQLException ex)
		{
			throw new Exception("Template Field be fetched. Reported exception: " + ex);
		}

	}

	public static List<PackhouseTreatment> getTreatments(String target_market,String treatment_type_code) throws Exception
	{
		try
		{
			HashMap params = new HashMap();
			params.put("target_market",target_market);
			params.put("treatment_type_code",treatment_type_code);

			List<PackhouseTreatment> treatment_codes = (List) DataSource.getSqlMapInstance().queryForList("getTreatments",params);
			return treatment_codes;
		} catch (SQLException ex)
		{
			throw new Exception("Reported exception: " + ex);
		}

	}

	public static List<PackhouseTreatment> getTreats(String target_market) throws Exception
	{
		try
		{
			HashMap params = new HashMap();
			params.put("target_market",target_market);
//			params.put("treatment_type_code",treatment_type_code);

			List<PackhouseTreatment> treatment_codes = (List) DataSource.getSqlMapInstance().queryForList("getTreats",params);
			return treatment_codes;
		} catch (SQLException ex)
		{
			throw new Exception("Reported exception: " + ex);
		}

	}

	public static DataFieldValue getDataFieldValue(String data_field_value) throws Exception
	{
		try
		{

			DataFieldValue data_field_value_translations = (DataFieldValue) DataSource.getSqlMapInstance().queryForObject("getDataFieldValue", data_field_value);
			return data_field_value_translations;
		} catch (SQLException ex)
		{
			throw new Exception("Data Field Value be fetched. Reported exception: " + ex);
		}

	}

    public static Resource getResource(int resource_id) throws Exception
    {
        try
        {


            Resource resource = (Resource) DataSource.getSqlMapInstance().queryForObject("getResource", resource_id);
            return resource;
        } catch (SQLException ex)
        {
            throw new Exception("Resource setup be fetched. Reported exception: " + ex);
        }

    }
	
	public static HashMap getShift(int line_id,String employment_type_code) throws Exception
	{
		try
		{
            int run_packhouse_id = getLinePackHouseId(line_id);

            HashMap params = new HashMap();
            params.put("employment_type",employment_type_code);
            params.put("packhouse_resource_id",run_packhouse_id);

			HashMap shift = (HashMap) DataSource.getSqlMapInstance().queryForObject("getShift", params);
			return shift;
		} catch (SQLException ex)
		{
			throw new Exception("Shift could not be fetched. Reported exception: " + ex);
		}

	}

    public static HashMap getShift(int line_id,String employment_type_code,String scanner_barcode) throws Exception
    {
        try
        {
            int run_packhouse_id = getLinePackHouseId(line_id);

            HashMap params = new HashMap();
            params.put("employment_type",employment_type_code);
            params.put("packhouse_resource_id",run_packhouse_id);

            HashMap shift = (HashMap) DataSource.getSqlMapInstance().queryForObject("getShift", params);


            if(shift != null)
            {
                shift.put("barcode_num",scanner_barcode) ;
                shift = (HashMap) DataSource.getSqlMapInstance().queryForObject("getShiftForEmployee", shift);
            }


            if(shift == null) //previous shift cartons?
            {
                shift = (HashMap) DataSource.getSqlMapInstance().queryForObject("getPreviousShift", params);
            }

            if(shift != null) //previous shift cartons for this worker?
            {
                shift.put("barcode_num",scanner_barcode) ;
                shift = (HashMap) DataSource.getSqlMapInstance().queryForObject("getShiftForEmployee", shift);
            }
            
            
            return shift;
        } catch (SQLException ex)
        {
            throw new Exception("Shift could not be fetched. Reported exception: " + ex);
        }

    }



	
	public static Integer getLinePackHouseId(int line_id) throws Exception
	{
		try
		{
			
			Integer  pack_house_id = (Integer) DataSource.getSqlMapInstance().queryForObject("getLinePackHouseId", line_id);
			return pack_house_id;
		} catch (SQLException ex)
		{
			throw new Exception("Packhouse id could not be fetched. Reported exception: " + ex);
		}
		
	}

    public static Double getCartonCalculatedMass(int fg_product_id) throws Exception
    {
        try
        {

            Double  mass = (Double) DataSource.getSqlMapInstance().queryForObject("getCartonCalculatedMass", fg_product_id);
            return mass;
        } catch (SQLException ex)
        {
            throw new Exception("Carton Calculated Mass  could not be fetched. Reported exception: " + ex);
        }

    }

	
	
	
	
	public static String getSummaryGtin()
	{
		
		return "list";
	}
	

	
	public static PUC getPUC(String puc_code) throws Exception
	{
		try
		{
			
			return (PUC)DataSource.getSqlMapInstance().queryForObject("getPUC", puc_code);
		} catch (SQLException ex)
		{
			throw new Exception("Eurogap could not be fetched. Reported exception: " + ex);
		}
	}





    public static Carton getCarton(Long carton_number) throws Exception
    {
        try
        {

            Carton carton = (Carton) DataSource.getSqlMapInstance().queryForObject("getCarton", carton_number);
            return carton;
        } catch (SQLException ex)
        {
            throw new Exception("Carton  could not be fetched. Reported exception: " + ex);
        }

    }


	public static Carton getCartonLabel(Long carton_number) throws Exception
	{
		try
		{

			Carton carton_label = (Carton) DataSource.getSqlMapInstance().queryForObject("getCartonLabel", carton_number);
			return carton_label;
		} catch (SQLException ex)
		{
			throw new Exception("Carton label  could not be fetched. Reported exception: " + ex);
		}

	}
        

	
	
	public static String getLinePhc (int line_id) throws Exception
	{
		try
		{
			
			String phc = (String) DataSource.getSqlMapInstance().queryForObject("getLinePhc",line_id);
			return phc;
		} catch (SQLException ex)
		{
			throw new Exception("Phc could not be fetched for line " + String.valueOf(line_id) + ". Reported exception: " + ex);
		}
		
	}

    public static String getFgSetupCode (int setup_id) throws Exception
    {
        try
        {

            String phc = (String) DataSource.getSqlMapInstance().queryForObject("getFgSetupCode",setup_id);
            return phc;
        } catch (SQLException ex)
        {
            throw new Exception("Fg setup could not be fetched for line " + String.valueOf(setup_id) + ". Reported exception: " + ex);
        }

    }

    public static String getFgProductCode (int product_id) throws Exception
    {
        try
        {

            String phc = (String) DataSource.getSqlMapInstance().queryForObject("getFgProductCode",product_id);
            return phc;
        } catch (SQLException ex)
        {
            throw new Exception("Fg product could not be fetched for line " + String.valueOf(product_id) + ". Reported exception: " + ex);
        }

    }
	

	
	public static String getCurrentIsoWeek () throws Exception
	{
		try
		{
			BigDate d = new BigDate(new java.util.Date(),TimeZone.getDefault());
		    int iso_week = d.getISOWeekNumber();
			//String iso_week_code = (String) DataSource.getSqlMapInstance().queryForObject("getCurrentIsoWeek");
			return String.valueOf(iso_week);
		} catch (Exception ex)
		{
			throw new Exception("Current iso week could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	public static String getResourceValue(String field_name,Resource resource)
    {
        if(resource.getAttr1_name() != null && resource.getAttr1_name().equals(field_name))
            return resource.getAttr1_value();
        else if (resource.getAttr2_name() != null && resource.getAttr2_name().equals(field_name))
            return resource.getAttr2_value();
        else if (resource.getAttr3_name() != null && resource.getAttr3_name().equals(field_name))
            return resource.getAttr3_value();
        else if (resource.getAttr4_name() != null && resource.getAttr4_name().equals(field_name))
            return resource.getAttr4_value();
        else
            return null;

    }
    
	public static Map getSeasonOrderQtyDetails(String order_number,String season_code) throws Exception
	{
		try
		{
			if(order_number == null || order_number.toUpperCase().equals("N.A."))
                            return null;
                        
            HashMap params = new HashMap();
			params.put("season_code",season_code);
			params.put("order_number",order_number);
                       
			Map order_details = (Map) DataSource.getSqlMapInstance().queryForObject("getSeasonOrderQtyDetails", params);
			return order_details;
		} catch (SQLException ex)
		{
			throw new Exception("Order details could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	
	public synchronized static Long getNextMesObjectId(MesObjectTypes object_type) throws Exception
	{
		MesControlFile ctl = (MesControlFile) DataSource.getSqlMapInstance().queryForObject("getMesCtlSequence", object_type.ordinal());
		ctl.setSequence_number(ctl.getSequence_number()+1);
		DataSource.getSqlMapInstance().update("setMesCtlSequence",ctl);
		return ctl.getSequence_number();
		
	}
	
	
	public static String getTemplateFileName(String template_name)  throws Exception
	{
		return   (String) DataSource.getSqlMapInstance().queryForObject("getTemplateFileName", template_name);
	}


	
	public static Account getMarketerAccountCodeForFarm(String farm_code,String marketing_org_short_descr) throws Exception
	{
		try
		{
			HashMap params = new HashMap();
			params.put("org_short_description",marketing_org_short_descr);
			params.put("farm_code",farm_code);
		
			
			Account account = (Account) DataSource.getSqlMapInstance().queryForObject("getAccountCodeForFarmAndMarketer", params);
			return account;
		} catch (SQLException ex)
		{
			throw new Exception("Account code could not be fetched. Reported exception: " + ex);
		}
		
	}
	

    //getEmployeeId

    public static Integer getEmployeeId (String employee_barcode_num) throws Exception
    {
        try
        {

            Integer employee_id = (Integer) DataSource.getSqlMapInstance().queryForObject("getEmployeeId", employee_barcode_num);
            return employee_id;
        } catch (SQLException ex)
        {
            throw new Exception("Employee id could not be fetched. Reported exception: " + ex);
        }

    }
	
	public static ProductionRun getProductionRun (int id) throws Exception
	{
		try
		{
			
			ProductionRun run = (ProductionRun) DataSource.getSqlMapInstance().queryForObject("getProductionRun", id);
			return run;
		} catch (SQLException ex)
		{
			throw new Exception("Production run could not be fetched. Reported exception: " + ex);
		}
		
	}
	


	
	
        public static void updateCartonRunStats(FgSetup new_carton) throws Exception
        {
            //-------------------------------------------------------------------------------------
            //Method created to overcome deadlock problem when multiple labeling instances tries to
            //update the production_runs record
            //--------------------------------------------------------------
              DataSource.getSqlMapInstance().update("updateCartonStats",new_carton);
	      //DataSource.getSqlMapInstance().update("addCartonWeight",new_carton);
        }
        
        
	public static void createCarton(FgSetup carton_template) throws Exception
	{
		try
		{
			
			DataSource.getSqlMapInstance().insert("createCarton",carton_template);
                        //updateRunStats(new_carton,null);
                        //updateCartonRunStats(new_carton);
                       // DataSource.getSqlMapInstance().update("incrementCartonsPrinted",new_carton);
		       //DataSource.getSqlMapInstance().update("addCartonWeight",new_carton);
			
		} catch (SQLException ex)
		{
			throw new Exception("Carton could not be created. Reported exception: " + ex);
		}
		
	}


	public static void createCartonLabel(FgSetup carton_template) throws Exception
	{
		try
		{

			DataSource.getSqlMapInstance().insert("createCartonLabel",carton_template);
			//updateRunStats(new_carton,null);
			//updateCartonRunStats(new_carton);
			// DataSource.getSqlMapInstance().update("incrementCartonsPrinted",new_carton);
			//DataSource.getSqlMapInstance().update("addCartonWeight",new_carton);

		} catch (SQLException ex)
		{
			throw new Exception("Carton Label could not be created. Reported exception: " + ex);
		}

	}


	public static void createCartonFromLabel(Carton label) throws Exception
	{
		try
		{

			DataSource.getSqlMapInstance().insert("createCartonFromLabel",label);
			//updateRunStats(new_carton,null);
			//updateCartonRunStats(new_carton);
			// DataSource.getSqlMapInstance().update("incrementCartonsPrinted",new_carton);
			//DataSource.getSqlMapInstance().update("addCartonWeight",new_carton);

		} catch (SQLException ex)
		{
			throw new Exception("Carton could not be created from label. Reported exception: " + ex);
		}

	}
	
        public synchronized static void updateRunStats(FgSetup carton,Bin bin) throws Exception
        {


//
//            System.out.println("W:" + carton.getWeight().toString());
//            System.out.println("CW:" + carton.getCalculated_mass().toString());

            if(carton != null)
            {
                if(carton.getCalculated_mass() == null)
                    carton.setCalculated_mass(0.00);

                if(carton.getWeight() == null)
                    carton.setWeight(0.00);


               if (carton.getWeight()== null||carton.getWeight() < 1.00)
                   carton.setWeight(carton.getCalculated_mass());

                ProductLabelingDAO.updateCartonRunStats(carton);
            }


            else if(bin != null)
                BinTippingDAO.updateBinRunStats(bin);
        }

    //getCartonCalculatedMass
        

        

        
        public static void updateCartonWeight(Long carton_number,Double weight) throws Exception
	{
		try
		{

                        HashMap params = new HashMap();
			params.put("carton_fruit_nett_mass_actual",weight);
			params.put("carton_number",carton_number);
			DataSource.getSqlMapInstance().update("updateCartonWeight",params);
   

		} catch (SQLException ex)
		{
			throw new Exception("Carton could not be updated. Reported exception: " + ex);
		}

	}


	
	
	
}
