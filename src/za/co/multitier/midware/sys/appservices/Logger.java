/*
 * Logger.java
 *
 * Created on January 30, 2007, 9:58 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.appservices;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.midware.sys.datasource.MidwareErrorLogEntry;
import za.co.multitier.midware.sys.datasource.UtilsDAO;

/**
 *
 * @author Administrator
 */
public class Logger {

    public static String display_errors = null;
    public static Properties settings = MidwareConfig.getInstance().getSettings();

    static {
        if (settings.containsKey("display_errors")) {
            display_errors = (String) settings.get("display_errors");
        }
    }

    public static void appendToFile(String fileName, String line) {

        BufferedWriter br = null;

        try {


            br = new BufferedWriter(new FileWriter(fileName, true));
            synchronized (br) {
                br.write(line);
                br.write(System.getProperty("line.separator"));
                br.close();
            }

        } catch (IOException iO) {
            //System.out.println("The file could not be created/opened/closed");
        }

    }

    public static String getFormattedTime() {
        Calendar today = new GregorianCalendar();
        String time = "";
        int hour = today.get(Calendar.HOUR_OF_DAY);
        int minute = today.get(Calendar.MINUTE);
        int second = today.get(Calendar.SECOND);

        String minute_str = String.valueOf(minute);
        minute_str = minute_str.length() == 1 ? "0" + minute_str : minute_str;

        String sec_str = String.valueOf(second);
        sec_str = sec_str.length() == 1 ? "0" + sec_str : sec_str;

        time = String.valueOf(hour) + "h" + minute_str + ":" + sec_str;
        return time;
    }

    public static String getFormattedTodayDate() {
        Calendar today = new GregorianCalendar();
        String date = "";
        int day = today.get(Calendar.DAY_OF_MONTH);
        int month = today.get(Calendar.MONTH) + 1;
        int year = today.get(Calendar.YEAR);

        date = String.valueOf(day) + "_" + String.valueOf(month) + "_" + String.valueOf(year);
        return date;
    }

    public static void handleException(MessageInterface msg, String short_err, String long_err, String source, String mw_type, int error_code,
            String production_run_code, Object stack_trace, String ip) {
        try {

            StackTraceElement[] trace = (StackTraceElement[]) stack_trace;

            StringBuilder trace_text = new StringBuilder();
            for (StackTraceElement st : trace) {
                trace_text.append(st.toString() + "\n");
            }


            if (display_errors != null) {
                System.out.println("==============================================");
                System.out.println("The following exception occurred in midware: ");
                System.out.println("==============================================");
                System.out.println("date: " + getFormattedTodayDate() + " time: " + getFormattedTime());

                System.out.println("description: " + long_err);
                System.out.println("--------------");
                System.out.println("Stack_trace: ");
                System.out.println("-------------");
                System.out.println(trace_text.toString());

                if (msg != null) {
                    msg.sysMsg("bold+red", long_err);
                    msg.sysMsg("==============================================");
                    msg.sysMsg("The following exception occurred in midware: ");
                    msg.sysMsg("==============================================");
                    msg.sysMsg("date: " + getFormattedTodayDate() + " time: " + getFormattedTime());

                    msg.sysMsg("description: " + long_err);
                    msg.sysMsg("--------------");
                    msg.sysMsg("Stack_trace: ");
                    msg.sysMsg("-------------");
                    msg.sysMsg(trace_text.toString());
                }

            }


            //log error
            MidwareErrorLogEntry error = new MidwareErrorLogEntry();
            int err_code = error_code;
            if (err_code == 0) {
                err_code = MidwareErrorLogEntry.SYSTEM_ERROR;
            }
            error.setError_code(err_code);
            error.setDevice_ip(ip);
            error.setError_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));
            error.setProduction_run_code(production_run_code);
            error.setError_description(long_err);
            error.setMw_type(mw_type);
            error.setShort_description(short_err);
            error.setStack_trace(trace_text.toString());
            UtilsDAO.createErrorLogEntry(error);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (msg != null) {
                msg.sysMsg("Exception handler failed. Reported Exception: " + ex.toString());
            }

        }

    }

    public static void handleException(MessageInterface msg, String short_err, String long_err, String source, String mw_type, int error_code,
            String production_run_code, Object stack_trace) {
        try {

            StackTraceElement[] trace = (StackTraceElement[]) stack_trace;

            StringBuilder trace_text = new StringBuilder();
            for (StackTraceElement st : trace) {
                trace_text.append(st.toString() + "\n");
            }

              if (display_errors != null) {
                System.out.println("==============================================");
                System.out.println("The following exception occurred in midware: ");
                System.out.println("==============================================");
                System.out.println("date: " + getFormattedTodayDate() + " time: " + getFormattedTime());

                System.out.println("description: " + long_err);
                System.out.println("--------------");
                System.out.println("Stack_trace: ");
                System.out.println("-------------");
                System.out.println(trace_text.toString());

                if (msg != null) {
                    msg.sysMsg("bold+red", long_err);
                    msg.sysMsg("==============================================");
                    msg.sysMsg("The following exception occurred in midware: ");
                    msg.sysMsg("==============================================");
                    msg.sysMsg("date: " + getFormattedTodayDate() + " time: " + getFormattedTime());

                    msg.sysMsg("description: " + long_err);
                    msg.sysMsg("--------------");
                    msg.sysMsg("Stack_trace: ");
                    msg.sysMsg("-------------");
                    msg.sysMsg(trace_text.toString());
                }

            }
            //log error
            MidwareErrorLogEntry error = new MidwareErrorLogEntry();
            int err_code = error_code;
            if (err_code == 0) {
                err_code = MidwareErrorLogEntry.SYSTEM_ERROR;
            }
            error.setError_code(err_code);
            error.setError_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));
            error.setProduction_run_code(production_run_code);
            error.setError_description(long_err);
            error.setMw_type(mw_type);
            error.setShort_description(short_err);
            error.setStack_trace(trace_text.toString());
            UtilsDAO.createErrorLogEntry(error);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (msg != null) {
                msg.sysMsg("Exception handler failed. Reported Exception: " + ex.toString());
            }

        }
    }
}




