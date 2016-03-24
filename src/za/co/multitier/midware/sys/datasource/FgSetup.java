package za.co.multitier.midware.sys.datasource;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hans
 * Date: 4/7/12
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class FgSetup {

    private Integer id;
    private Integer fg_product_id;
    private String organization_code; //inc
    private Integer gtin_id;
    private String packed_tm_group_code; //inc
    private String inventory_code; //inc
    private String pick_reference; //inc
    private String sell_by_code; //inc
    private String channel; //inc
    private String puc; //inc
    private String product_chars; //inc
    private String remarks; //inc
    private String batch_code; //inc
    private String gtin_code; //inc
    private String mark_code; //inc
    private String original_depot;  //inc
    private String season;
    private Integer iso_week;
    private String packer_barcode;
    private Integer packer_incentivized_employee_id;
    private String account_code;
    private Integer drop_resource_id;
    private Integer production_run_id;
    private Integer shift_id;
    private String egap;
    private Long carton_number;
    private java.sql.Timestamp pack_date_time;
    private Double weight;
    private java.sql.Timestamp created_on;
    private Integer fg_setup_id;
    private String farm_code;

    private String commodity_code;
    private String variety_description;
    private String commodity_description;
    private String grade_code;
    private String pack_code;


    private String gap;
    private String size_ref;
    private String size_count_code;
    private String orchard_code;



    private Double calculated_mass;

    //MM032016
    private List Concat_treatments;
    private List Concat_waxs;
    private List custom_label_fields;

    public List getCustom_label_fields() {
        return custom_label_fields;
    }

    public void setCustom_label_fields(List custom_label_fields) {
        this.custom_label_fields = custom_label_fields;
    }

    public List getConcat_treatments() {
        return Concat_treatments;
    }

    public void setConcat_treatments(List concat_treatments) {
        Concat_treatments = concat_treatments;
    }

    public List getConcat_waxs() {
        return Concat_waxs;
    }

    public void setConcat_waxs(List concat_waxs) {
        Concat_waxs = concat_waxs;
    }

    //MM032016


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFg_product_id() {
        return fg_product_id;
    }

    public void setFg_product_id(Integer fg_product_id) {
        this.fg_product_id = fg_product_id;
    }

    public String getOrganization_code() {
        return organization_code;
    }

    public void setOrganization_code(String organization_code) {
        this.organization_code = organization_code;
    }

    public Integer getGtin_id() {
        return gtin_id;
    }

    public void setGtin_id(Integer gtin_id) {
        this.gtin_id = gtin_id;
    }

    public String getPacked_tm_group_code() {
        return packed_tm_group_code;
    }

    public void setPacked_tm_group_code(String packed_tm_group_code) {
        this.packed_tm_group_code = packed_tm_group_code;
    }

    public String getInventory_code() {
        return inventory_code;
    }

    public void setInventory_code(String inventory_code) {
        this.inventory_code = inventory_code;
    }

    public String getPick_reference() {
        return pick_reference;
    }

    public void setPick_reference(String pick_reference) {
        this.pick_reference = pick_reference;
    }

    public String getSell_by_code() {
        return sell_by_code;
    }

    public void setSell_by_code(String sell_by_code) {
        this.sell_by_code = sell_by_code;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPuc() {
        return puc;
    }

    public void setPuc(String puc) {
        this.puc = puc;
    }

    public String getProduct_chars() {
        return product_chars;
    }

    public void setProduct_chars(String product_chars) {
        this.product_chars = product_chars;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBatch_code() {
        return batch_code;
    }

    public void setBatch_code(String batch_code) {
        this.batch_code = batch_code;
    }

    public String getGtin_code() {
        return gtin_code;
    }

    public void setGtin_code(String gtin_code) {
        this.gtin_code = gtin_code;
    }

    public String getMark_code() {
        return mark_code;
    }

    public void setMark_code(String mark_code) {
        this.mark_code = mark_code;
    }

    public String getOriginal_depot() {
        return original_depot;
    }

    public void setOriginal_depot(String original_depot) {
        this.original_depot = original_depot;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getIso_week() {
        return iso_week;
    }

    public void setIso_week(Integer iso_week) {
        this.iso_week = iso_week;
    }

    public String getPacker_barcode() {
        return packer_barcode;
    }

    public void setPacker_barcode(String packer_barcode) {
        this.packer_barcode = packer_barcode;
    }

    public Integer getPacker_incentivized_employee_id() {
        return packer_incentivized_employee_id;
    }

    public void setPacker_incentivized_employee_id(Integer packer_incentivized_employee_id) {
        this.packer_incentivized_employee_id = packer_incentivized_employee_id;
    }

    public String getAccount_code() {
        return account_code;
    }

    public void setAccount_code(String account_code) {
        this.account_code = account_code;
    }

    public Integer getDrop_resource_id() {
        return drop_resource_id;
    }

    public void setDrop_resource_id(Integer drop_resource_id) {
        this.drop_resource_id = drop_resource_id;
    }

    public Integer getProduction_run_id() {
        return production_run_id;
    }

    public void setProduction_run_id(Integer production_run_id) {
        this.production_run_id = production_run_id;
    }

    public Integer getShift_id() {
        return shift_id;
    }

    public void setShift_id(Integer shift_id) {
        this.shift_id = shift_id;
    }

    public String getEgap() {
        return egap;
    }

    public void setEgap(String egap) {
        this.egap = egap;
    }

    public Long getCarton_number() {
        return carton_number;
    }

    public void setCarton_number(Long carton_number) {
        this.carton_number = carton_number;
    }

    public Timestamp getPack_date_time() {
        return pack_date_time;
    }

    public void setPack_date_time(Timestamp pack_date_time) {
        this.pack_date_time = pack_date_time;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public Integer getFg_setup_id() {
        return fg_setup_id;
    }

    public void setFg_setup_id(Integer fg_setup_id) {
        this.fg_setup_id = fg_setup_id;
    }

    public String getFarm_code() {
        return farm_code;
    }

    public void setFarm_code(String farm_code) {
        this.farm_code = farm_code;
    }

    public Double getCalculated_mass() {
        return calculated_mass;
    }

    public void setCalculated_mass(Double calculated_mass) {
        this.calculated_mass = calculated_mass;
    }

    public String getCommodity_code() {
        return commodity_code;
    }

    public void setCommodity_code(String commodity_code) {
        this.commodity_code = commodity_code;
    }

    public String getVariety_description() {
        return variety_description;
    }

    public void setVariety_description(String variety_description) {
        this.variety_description = variety_description;
    }

    public String getCommodity_description() {
        return commodity_description;
    }

    public void setCommodity_description(String commodity_description) {
        this.commodity_description = commodity_description;
    }

    public String getGrade_code() {
        return grade_code;
    }

    public void setGrade_code(String grade_code) {
        this.grade_code = grade_code;
    }

    public String getPack_code() {
        return pack_code;
    }

    public void setPack_code(String pack_code) {
        this.pack_code = pack_code;
    }

    public String getGap() {
        return gap;
    }

    public void setGap(String gap) {
        this.gap = gap;
    }

    public String getSize_ref() {
        return size_ref;
    }

    public void setSize_ref(String size_ref) {
        this.size_ref = size_ref;
    }

    public String getSize_count_code() {
        return size_count_code;
    }

    public void setSize_count_code(String size_count_code) {
        this.size_count_code = size_count_code;
    }

    public String getOrchard_code() {
        return orchard_code;
    }

    public void setOrchard_code(String orchard_code) {
        this.orchard_code = orchard_code;
    }
}
