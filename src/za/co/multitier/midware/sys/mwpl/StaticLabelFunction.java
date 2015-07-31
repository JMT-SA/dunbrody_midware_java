package za.co.multitier.midware.sys.mwpl;

import za.co.multitier.midware.sys.datasource.FgSetup;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miracle on 2015/07/30.
 */
public class StaticLabelFunction {

    public String value;
    public String function_method_name;
    public String variable1_value;
    public FgSetup fg_setup;
    public Map data_fields;

    public String getValue() {
        return value;
    }

    public String getFunction_method_name() {
        return function_method_name;
    }

    public String getVariable1_value() {
        return variable1_value;
    }

    public StaticLabelFunction(String variable1_value,String function_method_name,Map data_fields,FgSetup fg_setup) {
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
            value = String.valueOf(fg_setup.getSize_count_code() + "/" + fg_setup.getSize_ref());
        }else {
            value = String.valueOf(fg_setup.getSize_ref() + "/" + fg_setup.getSize_count_code());
        }
        return value;
    }
}
