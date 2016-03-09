package za.co.multitier.midware.sys.datasource;

/**
 * Created by miracle on 2016/03/03.
 */
public class PackhouseTreatment {

    private String treatment_type_code;
    private String treatment_code;
    private String treatment_description;
    private String target_market;

    public String getTreatment_type_code() {
        return treatment_type_code;
    }

    public void setTreatment_type_code(String treatment_type_code) {
        this.treatment_type_code = treatment_type_code;
    }

    public String getTreatment_code() {
        return treatment_code;
    }

    public void setTreatment_code(String treatment_code) {
        this.treatment_code = treatment_code;
    }

    public String getTreatment_description() {
        return treatment_description;
    }

    public void setTreatment_description(String treatment_description) {
        this.treatment_description = treatment_description;
    }

    public String getTarget_market() {
        return target_market;
    }

    public void setTarget_market(String target_market) {
        this.target_market = target_market;
    }

}
