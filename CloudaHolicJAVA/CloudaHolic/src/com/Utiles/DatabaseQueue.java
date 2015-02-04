package com.Utiles;

import javax.servlet.http.HttpServlet;

import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import java.net.*; 
import java.io.*; 
public class DatabaseQueue extends HttpServlet implements Runnable  {

	private int SLEEP_THREAD = 50000;
	private static String[] team = new String[]{"DOTEROS"};
	//private static Queue queue = QueueFactory.getDefaultQueue();
	@Override
	public void run() {
		//Queue queue = QueueFactory.getDefaultQueue();
		for(;;)
		{
			try {
			for(String temp : team)
			{
				
				  URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
			      URL dataURL = new URL("http://localhost:8888/cloudaholic/ServletDatabase?name="+temp );
			      HTTPRequest fetchreq = new HTTPRequest(dataURL);
			      fetcher.fetch(fetchreq);
			}
			
			
			
				System.out.println("Durmiendo thread");
				Thread.sleep(SLEEP_THREAD);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
