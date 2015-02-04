package com.CloudaHolic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.Utiles.DatabaseQueue;

public class MyServletContextListener implements ServletContextListener {

    private DatabaseQueue myThread = null;
    private Thread thread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	
        if ((myThread == null)) {
            myThread = new DatabaseQueue();
            thread = new Thread(myThread);
        }

    	
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce){
        try {
            myThread.destroy();
        } catch (Exception ex) {
        	
        }
    }

	

	


}
