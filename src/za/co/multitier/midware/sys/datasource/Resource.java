package za.co.multitier.midware.sys.datasource;

/**
 * Created by IntelliJ IDEA.
 * User: hans
 * Date: 5/10/12
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Resource {

     private String resource_type_code;
     private String attr1_name;
     private String attr2_name;
     private String attr3_name;
     private String attr4_name;
     private Long parent_id;
     private Long id;

    private String attr1_value;
    private String attr2_value;
    private String attr3_value;
    private String attr4_value;
    
    private Integer resource_number;




    public String getResource_type_code() {
        return resource_type_code;
    }

    public void setResource_type_code(String resource_type_code) {
        this.resource_type_code = resource_type_code;
    }

    public String getAttr1_name() {
        return attr1_name;
    }

    public void setAttr1_name(String attr1_name) {
        this.attr1_name = attr1_name;
    }

    public String getAttr2_name() {
        return attr2_name;
    }

    public void setAttr2_name(String attr2_name) {
        this.attr2_name = attr2_name;
    }

    public String getAttr3_name() {
        return attr3_name;
    }

    public void setAttr3_name(String attr3_name) {
        this.attr3_name = attr3_name;
    }

    public String getAttr4_name() {
        return attr4_name;
    }

    public void setAttr4_name(String attr4_name) {
        this.attr4_name = attr4_name;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getAttr1_value() {
        return attr1_value;
    }

    public void setAttr1_value(String attr1_value) {
        this.attr1_value = attr1_value;
    }

    public String getAttr2_value() {
        return attr2_value;
    }

    public void setAttr2_value(String attr2_value) {
        this.attr2_value = attr2_value;
    }

    public String getAttr3_value() {
        return attr3_value;
    }

    public void setAttr3_value(String attr3_value) {
        this.attr3_value = attr3_value;
    }

    public String getAttr4_value() {
        return attr4_value;
    }

    public void setAttr4_value(String attr4_value) {
        this.attr4_value = attr4_value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getResource_number() {
        return resource_number;
    }

    public void setResource_number(Integer resource_number) {
        this.resource_number = resource_number;
    }
}
