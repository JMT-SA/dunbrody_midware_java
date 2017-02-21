package za.co.multitier.midware.sys.datasource;

/**
 * Created by miracle on 2015/07/23.
 */
public class LabelTemplateField {

    private String field_name;
    private String field_type;
    private String variable1;
    private String variable2;
    private String separator;
    private Integer position;
    private String template_name;
    private String template_file_name;
    private String language;

//    MM032016 - label_template_field_language
    private String label_template_field_language;

    public String getLabel_template_field_language() {
        return label_template_field_language;
    }

    public void setLabel_template_field_language(String label_template_field_language) {
        this.label_template_field_language = label_template_field_language;
    }

    //    MM032016 - label_template_field_language

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public String getVariable1() {
        return variable1;
    }

    public void setVariable1(String variable1) {
        this.variable1 = variable1;
    }

    public String getVariable2() {
        return variable2;
    }

    public void setVariable2(String variable2) {
        this.variable2 = variable2;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getTemplate_file_name() {
        return template_file_name;
    }

    public void setTemplate_file_name(String template_file_name) {
        this.template_file_name = template_file_name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


}
