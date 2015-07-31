package za.co.multitier.midware.sys.mwpl;

import za.co.multitier.midware.sys.datasource.FgSetup;

import java.beans.Introspector;
import java.lang.reflect.Method;
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

    public FgSetup fg_setup;

    public LabelFunction(String field_type, String separator, String variable1, String variable2,Map data_fields,FgSetup fg_setup) {
        this.field_type = field_type;
        this.separator = separator;
        this.variable1 = variable1;
        this.variable2 = variable2;
        this.data_fields = data_fields;
        this.fg_setup = fg_setup;
        value = getValue(field_type,separator,variable1,variable2,data_fields,fg_setup);
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

    public String getValue(String field_type, String separator, String variable1, String variable2,Map data_fields,FgSetup fg_setup){

        String value;
//      NB some data fields myt be functions which need to be calculated first... create method for
        if (field_type.equals("current_date")) {
            value = this.getFormattedNowDate();
        }
        else if (field_type.equals("empty_value")) {
            value = "";
        }
        else if (field_type.equals("static_variable")) {
            value = String.valueOf(variable1);
        }
        else if (field_type.equals("function")) {
            value = new StaticLabelFunction(String.valueOf(data_fields.get(variable1)),String.valueOf(variable2),data_fields,fg_setup).value;
        }
        else{
            String variable1_value =  String.valueOf(data_fields.get(variable1));
            String separator_value =  String.valueOf(separator);
            String variable2_value =  String.valueOf(data_fields.get(variable2));
            value = variable1_value + separator_value + variable2_value;
        }
        return value;
    }

//    private String getFunctionValue(String variable1_value, String function_method_name,Map data_fields,FgSetup fg_setup,StaticLabelFunction static_label_function){
//        String value ="";
//        try {
//            Class myClass = static_label_function.getClass();
//            Method[] methods = myClass.getMethods();
//            for (Method method:methods)
//            {
//                String method_name=method.getName();
//                if(method_name.equals(function_method_name))
//                {
//                    method.setAccessible(true);
//                    value = String.valueOf(String.valueOf(method.invoke(variable1_value,data_fields,fg_setup)== null? "":method.invoke(variable1_value,data_fields,fg_setup)));
//                }
//            }
//        } catch (Exception ex) {
//        }
//        return value;
//    }
}
