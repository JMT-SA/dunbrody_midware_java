/*
 * DevicesDAO.java
 *
 * Created on January 25, 2007, 1:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.common.resources.Resources;

import java.io.Reader;
import java.io.IOException;
import java.sql.SQLException;

public class UtilsDAO
{
	

	public static void createErrorLogEntry(MidwareErrorLogEntry logEntry) throws Exception
	{
		try
		{
			DataSource.getSqlMapInstance().endTransaction(); //end transaction of original business transaction
                        //that failed, so that error logging will not be rolled back by global end_transaction call in fially
                        //clause of main business object instance
                        DataSource.getSqlMapInstance().startTransaction();
			DataSource.getSqlMapInstance().insert("logMidwareError", logEntry);
                        DataSource.getSqlMapInstance().commitTransaction();
			
		} catch (SQLException ex)
		{
			throw new Exception("Error could not be logged. Reported exception: " + ex);
		}
                
                finally
                {
                    DataSource.getSqlMapInstance().endTransaction();
                }
		
	}
	
}
