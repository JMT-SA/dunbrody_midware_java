/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author hans
 */
public class RmtSetup {

     //vals needed for bintip criteria check
        private String farm_code;
        
        private String commodity_code;
        private String variety_code;
        private String class_code;

        private int id;

       private Boolean mix_farm;
       private Boolean mix_class;
      private Boolean mix_delivery;

      private Integer delivery_id;
    
      private Integer delivery_number;
    
      private String variety_group_code;




    /**
     * @return the farm_code
     */
    public String getFarm_code() {
        return farm_code;
    }

    /**
     * @param farm_code the farm_code to set
     */
    public void setFarm_code(String farm_code) {
        this.farm_code = farm_code;
    }

    /**
     * @return the commodity_code
     */
    public String getCommodity_code() {
        return commodity_code;
    }

    /**
     * @param commodity_code the commodity_code to set
     */
    public void setCommodity_code(String commodity_code) {
        this.commodity_code = commodity_code;
    }

    /**
     * @return the variety_code
     */
    public String getVariety_code() {
        return variety_code;
    }

    /**
     * @param variety_code the variety_code to set
     */
    public void setVariety_code(String variety_code) {
        this.variety_code = variety_code;
    }

    /**
     * @return the class_code
     */
    public String getClass_code() {
        return class_code;
    }

    /**
     * @param class_code the class_code to set
     */
    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }








    public Boolean isMix_farm() {
        return mix_farm;
    }

    public void setMix_farm(Boolean mix_farm) {
        this.mix_farm = mix_farm;
    }

    public Boolean isMix_class() {
        return mix_class;
    }

    public void setMix_class(Boolean mix_class) {
        this.mix_class = mix_class;
    }

    public Boolean isMix_delivery() {
        return mix_delivery;
    }

    public void setMix_delivery(Boolean mix_delivery) {
        this.mix_delivery = mix_delivery;
    }

    public Integer getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(Integer delivery_id) {
        this.delivery_id = delivery_id;
    }

    public Integer getDelivery_number() {
        return delivery_number;
    }

    public void setDelivery_number(Integer delivery_number) {
        this.delivery_number = delivery_number;
    }

    public String getVariety_group_code() {
        return variety_group_code;
    }

    public void setVariety_group_code(String variety_group_code) {
        this.variety_group_code = variety_group_code;
    }
}
