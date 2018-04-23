
package za.co.multitier.midware.sys.mwpl;

import za.co.multitier.midware.sys.datasource.DataFieldValue;
import za.co.multitier.midware.sys.datasource.FgSetup;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.*;

/**
 * Created by miracle on 2015/07/28.
 */
public class LabelFunction {
    public String language;
    public String field_name;
    public String field_type;
    public String separator;
    public String variable1;
    public String variable2;
    public String value;
    public Map data_fields;

    public FgSetup fg_setup;

    public LabelFunction(String language,String field_name,String field_type, String separator, String variable1, String variable2,Map data_fields,FgSetup fg_setup) {
        this.language = language;
        this.field_name = field_name;
        this.field_type = field_type;
        this.separator = separator;
        this.variable1 = variable1;
        this.variable2 = variable2;
        this.data_fields = data_fields;
        this.fg_setup = fg_setup;
        value = getValue(language,field_name,field_type,separator,variable1,variable2,data_fields,fg_setup);
    }

    public String getField_name() {
        return field_name;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
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

    public FgSetup getFg_setup() {
        return fg_setup;
    }

    public void setFg_setup(FgSetup fg_setup) {
        this.fg_setup = fg_setup;
    }

    protected String getFormattedNowDate()
    {

        Date today;
        DateFormat dateFormatter;

        dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA);
        today = new Date();
        return dateFormatter.format(today);

    }

    public String getValue(String language,String field_name,String field_type, String separator, String variable1, String variable2,Map data_fields,FgSetup fg_setup){

        String value;
//      NB some data fields myt be functions which need to be calculated first... create method for
//      if label field data type is not "data_variable" => call new class  & method e.g. LabelFunction.new(function_name,separator,variable1,variable2).value
        if (field_type.equals("data_variable")) {
            value = get_data_field_value_translation(String.valueOf(data_fields.get(field_name)), String.valueOf(language));
        }else if (field_type.equals("current_date")) {
            value = this.getFormattedNowDate();
        }
        else if (field_type.equals("empty_value")) {
            value = "";
        }
        else if (field_type.equals("static_variable")) {
            value = get_data_field_value_translation(String.valueOf(variable1), String.valueOf(language));
        }
        else if (field_type.equals("function")) {
            value = new StaticLabelFunction(String.valueOf(language),String.valueOf(data_fields.get(variable1)),String.valueOf(variable2),data_fields,fg_setup).value;
        }
        else{
            String variable1_value =  get_data_field_value_translation(String.valueOf(data_fields.get(variable1)), String.valueOf(language));
            String separator_value =  String.valueOf(separator); //get_data_field_value_translation(String.valueOf(separator), String.valueOf(language));
            String variable2_value =  get_data_field_value_translation(String.valueOf(data_fields.get(variable2)), String.valueOf(language));
            value = variable1_value + separator_value + variable2_value;
        }
        return value;
    }

    public String get_data_field_value_translation(String data_field_value,String language){
        String field_value;
        if (language.equals("english")) {
            field_value = data_field_value;
        }
        else{
            try {
                DataFieldValue data_field_value_translations = ProductLabelingDAO.getDataFieldValue(data_field_value);
//                field_value = String.valueOf(data_field_value_translations.get(language));
                if (language.equals("indian")) {
                    field_value = String.valueOf(data_field_value_translations.getIndian());
                }else if (language.equals("russian")) {
                    field_value = String.valueOf(data_field_value_translations.getRussian());
                }else{
                    field_value = data_field_value;
                }
            } catch (Exception ex) {
                field_value = data_field_value;
            }
        }
        return field_value;
    }

}
