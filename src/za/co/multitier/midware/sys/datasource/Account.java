/*
 * Account.java
 *
 * Created on 12 December 2007, 10:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Hans
 */
public class Account {
    
    /** Creates a new instance of Account */
    public Account() {
    }

    private String puc_code;

    private String account_code;

    public String getPuc_code() {
        return puc_code;
    }

    public void setPuc_code(String puc_code) {
        this.puc_code = puc_code;
    }

    public String getAccount_code() {
        return account_code;
    }

    public void setAccount_code(String account_code) {
        this.account_code = account_code;
    }
    
}
