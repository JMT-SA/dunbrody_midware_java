package za.co.multitier.midware.sys.mwpl;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by miracle on 2015/07/28.
 */
public class LabelFunction {

    public String field_type;
    public String separator;
    public String variable1;
    public String variable2;
    public String value;
    public Map data_fields;

    public LabelFunction(String field_type, String separator, String variable1, String variable2,Map data_fields) {
//        field_type = field_type;
//        separator = separator;
//        variable1 = variable1;
//        variable2 = variable2;
//        data_fields = data_fields;
        value = getValue(field_type,separator,variable1,variable2,data_fields);
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

    protected String getFormattedNowDate()
    {

        Date today;
        DateFormat dateFormatter;

        dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA);
        today = new Date();
        return dateFormatter.format(today);

    }

    public String getValue(String field_type, String separator, String variable1, String variable2,Map data_fields){

        String value;
//      NB some data fields myt be functions which need to be calculated first... create method for
        if (field_type.equals("current_date")) {
            value = this.getFormattedNowDate();
        }
        else if (field_type.equals("empty_value")) {
            value = "";
        }
        else{
            String variable1_value =  String.valueOf(data_fields.get(variable1));
            String separator_value =  String.valueOf(separator);
            String variable2_value =  String.valueOf(data_fields.get(variable2));
            value = variable1_value + separator_value + variable2_value;
        }
        return value;
    }

}
