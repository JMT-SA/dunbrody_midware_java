package za.co.multitier.midware.sys.datasource;

/**
 * Created by IntelliJ IDEA.
 * User: hans
 * Date: 4/2/12
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActiveRunResource {

    private int id;
    private String resource_code;
    private String resource_type_code;
    private int resource_id;
    private int product_setup_id;
    private int setup_detail_id;
    private int production_run_id;
    private String production_run_code;


    private String line_code;
    
    private int line_id;
    
    private String day_line_batch_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResource_code() {
        return resource_code;
    }

    public void setResource_code(String resource_code) {
        this.resource_code = resource_code;
    }

    public String getResource_type_code() {
        return resource_type_code;
    }

    public void setResource_type_code(String resource_type_code) {
        this.resource_type_code = resource_type_code;
    }

    public int getResource_id() {
        return resource_id;
    }

    public void setResource_id(int resource_id) {
        this.resource_id = resource_id;
    }

    public int getProduct_setup_id() {
        return product_setup_id;
    }

    public void setProduct_setup_id(int product_setup_id) {
        this.product_setup_id = product_setup_id;
    }

    public int getProduction_run_id() {
        return production_run_id;
    }

    public void setProduction_run_id(int production_run_id) {
        this.production_run_id = production_run_id;
    }

    public String getLine_code() {
        return line_code;
    }

    public void setLine_code(String line_code) {
        this.line_code = line_code;
    }

    public String getDay_line_batch_number() {
        return day_line_batch_number;
    }

    public void setDay_line_batch_number(String day_line_batch_number) {
        this.day_line_batch_number = day_line_batch_number;
    }

    public String getProduction_run_code() {
        return production_run_code;
    }

    public void setProduction_run_code(String production_run_code) {
        this.production_run_code = production_run_code;
    }

    public int getSetup_detail_id() {
        return setup_detail_id;
    }

    public void setSetup_detail_id(int setup_detail_id) {
        this.setup_detail_id = setup_detail_id;
    }

    public int getLine_id() {
        return line_id;
    }

    public void setLine_id(int line_id) {
        this.line_id = line_id;
    }
}
