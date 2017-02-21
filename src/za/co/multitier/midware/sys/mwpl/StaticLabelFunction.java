
package za.co.multitier.midware.sys.mwpl;

import za.co.multitier.midware.sys.datasource.DataFieldValue;
import za.co.multitier.midware.sys.datasource.FgSetup;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miracle on 2015/07/30.
 */
public class StaticLabelFunction {

    public String language;
    public String value;
    public String function_method_name;
    public String variable1_value;
    public FgSetup fg_setup;
    public Map data_fields;

    public String getLanguage() {
        return language;
    }

    public String getValue() {
        return value;
    }

    public String getFunction_method_name() {
        return function_method_name;
    }

    public String getVariable1_value() {
        return variable1_value;
    }

    public StaticLabelFunction(String language,String variable1_value,String function_method_name,Map data_fields,FgSetup fg_setup) {
        this.language = language;
        this.variable1_value = variable1_value;
        this.function_method_name = function_method_name;
        this.data_fields = data_fields;
        this.fg_setup = fg_setup;
        value = getFunctionValue(function_method_name,this);
    }

    public String getFunctionValue(String function_method_name,StaticLabelFunction static_label_function){
        String value ="";
        try {
            Class myClass = static_label_function.getClass();
            Method[] methods = myClass.getMethods();
            for (Method method:methods)
            {
                String method_name=method.getName();
                if(method_name.equals(function_method_name))
                {
                    method.setAccessible(true);
                    value = String.valueOf(String.valueOf(method.invoke(static_label_function)== null? "":method.invoke(static_label_function)));
                }
            }
        } catch (Exception ex) {
        }
        return value;
    }

    public String countSizeSwapRule(){
        if(variable1_value.toUpperCase().equals("SC")) {
            value = String.valueOf(get_data_field_value_translation(fg_setup.getSize_count_code(),language) + "/" + get_data_field_value_translation(fg_setup.getSize_ref(), language));
        }else {
            value = String.valueOf(get_data_field_value_translation(fg_setup.getSize_ref(),language) + "/" + get_data_field_value_translation(fg_setup.getSize_count_code(),language));
        }
        return value;
    }

    public String getOrchard(){
        String orchard = fg_setup.getOrchard_code() == null? "mixed" : String.valueOf(fg_setup.getOrchard_code());
        value =  String.valueOf(get_data_field_value_translation(fg_setup.getPuc(),language) + "/" + get_data_field_value_translation(orchard,language));
        return value;
    }

    public String get_data_field_value_translation(String data_field_value,String language){
        String field_value;
        if (language.equals("english")) {
            field_value = data_field_value;
        }
        else{
            try {
                DataFieldValue data_field_value_translations = ProductLabelingDAO.getDataFieldValue(data_field_value.toLowerCase());
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
