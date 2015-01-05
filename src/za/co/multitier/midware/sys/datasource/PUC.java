/*
 * PUC.java
 *
 * Created on 31 December 2007, 11:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Hans
 */
public class PUC {
    
    /** Creates a new instance of PUC */
    public PUC() {
    }

    private String eurogap_code;

    private String puc_code;

    private String nature_choice_certificate_code;

    public String getEurogap_code() {
        return eurogap_code;
    }

    public void setEurogap_code(String eurogap_code) {
        this.eurogap_code = eurogap_code;
    }

    public String getPuc_code() {
        return puc_code;
    }

    public void setPuc_code(String puc_code) {
        this.puc_code = puc_code;
    }

    public String getNature_choice_certificate_code() {
        return nature_choice_certificate_code;
    }

    public void setNature_choice_certificate_code(String nature_choice_certificate_code) {
        this.nature_choice_certificate_code = nature_choice_certificate_code;
    }
    
}
