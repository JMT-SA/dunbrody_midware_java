package za.co.multitier.midware.sys.datasource;

/**
 * Created by IntelliJ IDEA.
 * User: hans
 * Date: 4/7/12
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Carton {

    private Long id;
    private Integer fg_product_id;
    private String organization_code;
    private Integer gtin_id;
    private String packed_tm_group;
    private String inventory_code;
    private String pick_reference;
    private String sell_by_date;
    private String channel;
    private String puc;
    private String product_characteristics;
    private String remarks;
    private String batch_code;
    private String gtin_code;
    private String mark_code;
    private String original_depot;
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
    private String farm_code;
    private String puc_code;
    private Double carton_fruit_nett_mass;
    
    private Double calculated_mass;



    private String commodity_code;
    private String variety_description;
    private String commodity_description;
    private String grade_code;
    private String pack_code;


    private String farm_gap;
    private String size_ref;
    private String size_count_code;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPacked_tm_group() {
        return packed_tm_group;
    }

    public void setPacked_tm_group(String packed_tm_group) {
        this.packed_tm_group = packed_tm_group;
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

    public String getSell_by_date() {
        return sell_by_date;
    }

    public void setSell_by_date(String sell_by_date) {
        this.sell_by_date = sell_by_date;
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

    public String getProduct_characteristics() {
        return product_characteristics;
    }

    public void setProduct_characteristics(String product_characteristics) {
        this.product_characteristics = product_characteristics;
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

    public String getFarm_code() {
        return farm_code;
    }

    public void setFarm_code(String farm_code) {
        this.farm_code = farm_code;
    }

    public String getPuc_code() {
        return puc_code;
    }

    public void setPuc_code(String puc_code) {
        this.puc_code = puc_code;
    }

    public Double getCarton_fruit_nett_mass() {
        return carton_fruit_nett_mass;
    }

    public void setCarton_fruit_nett_mass(Double carton_fruit_nett_mass) {
        this.carton_fruit_nett_mass = carton_fruit_nett_mass;
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


    public String getFarm_gap() {
        return farm_gap;
    }

    public void setFarm_gap(String farm_gap) {
        this.farm_gap = farm_gap;
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
}
