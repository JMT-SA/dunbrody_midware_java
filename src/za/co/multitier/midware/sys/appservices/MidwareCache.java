/*
 * BinTippingCache.java
 *
 * Created on January 30, 2007, 7:40 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.appservices;

import java.util.HashMap;
/**
 *
 * @author Administrator
 */

public class MidwareCache
{
	
	private HashMap palletizing_long_transaction_store;
        private HashMap palletizing_transaction_store;
        private HashMap rebin_transaction_store;
        
	private static final MidwareCache instance;
	
	static
	{
		
		instance = new MidwareCache();
	}
	public synchronized static MidwareCache getDevicesCache() {return instance;}
        
	public MidwareCache()
	{
		
		palletizing_long_transaction_store = new HashMap();
                palletizing_transaction_store = new HashMap();
		rebin_transaction_store = new HashMap();
	}
        
        
        
        //==========
        //REBINNING
        //==========
        
        public synchronized boolean otherBusyRebinTransaction(String station_code,String print_number) throws Exception
        {
            
            String key = station_code + "|" + print_number;
            if(rebin_transaction_store.get(key)== null)
            {
                synchronized(rebin_transaction_store)
                {
                    rebin_transaction_store.put(key,true);
                }
                return false;
            }
            else
                return true;
        }
        
         public synchronized void rebinTransactionDone(String station_code,String print_number) throws Exception
        {
            String key = station_code + "|" + print_number;
            
            synchronized(rebin_transaction_store)
            {
		rebin_transaction_store.remove(key);
            }
        }
        
        //============
        //PALLETIZING 
        //============
        
        //---------------------------------------------------------------------------------------------
        //This method checks whether another palletizing transaction is active on a given bay for a given
        //skip. Only one transaction(palletizing instance) can be active on a bay. To ensure this, every
        //transaction sets a flag on the shared memory store('palletizing_transaction_store') at the start of
        //'process_transaction' method and removes the flag at the end of the method. When attempting to
        //set the flag, a check is done to see whether the bay is occupied by another transaction. If so
        //the 'otherBusyTransaction' method returns false- in which case the palletizing instance must notify
        //the client oct that another transation is still busy ('transaction busy')
        //---------------------------------------------------------------------------------------------
        public synchronized boolean otherBusyTransaction(String skip_ip,String bay_num) throws Exception
        {
            String key = skip_ip + "|" + bay_num;
            if(palletizing_transaction_store.get(key)== null)
            {
                synchronized(palletizing_transaction_store)
                {
                    palletizing_transaction_store.put(key,true);
                }
                return false;
            }
            else
                return true;
        }
        
         public synchronized void transactionDone(String skip_ip,String bay_num) throws Exception
        {
            String key = skip_ip + "|" + bay_num;
            synchronized(palletizing_transaction_store)
            {
		palletizing_transaction_store.remove(key);
            }
        }
        
        
	 
	
	public synchronized Object getDeviceState(String skip_ip,String bay_num) throws Exception
	{
		
		String key = skip_ip + "|" + bay_num;
		
		return palletizing_long_transaction_store.get(key);
		
	}
	
	public synchronized void clearDeviceState(String skip_ip,String bay_num) throws Exception
	{
		
		String key = skip_ip + "|" + bay_num;
		  synchronized(palletizing_long_transaction_store)
		  {
			palletizing_long_transaction_store.remove(key);
		  }
			//throw new Exception("Bay: " + bay_num + " on Skip: " + skip_ip + " could not be found in memory");
		
	}
	
	
	
	public synchronized void setDeviceState(String skip_ip,String bay_num,Object state) throws Exception
	{  
		try
		{
			
		  String key = skip_ip + "|" + bay_num;
		  synchronized(palletizing_long_transaction_store)
		  {
			palletizing_long_transaction_store.put(key,state);
		  }
		
		
		}
		catch(Exception e)
		{
			throw new Exception("Palletizing state could not be set for skip with ip: " + skip_ip + " and bay: " + bay_num
				                + ". Reported exception: " + e);
		}
		
	}
	
	 

	
	
}
