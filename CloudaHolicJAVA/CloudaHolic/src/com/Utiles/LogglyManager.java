package com.Utiles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;

public class LogglyManager {
	private static LogglyManager INSTANCE;

	public static LogglyManager GetIntance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new LogglyManager();
		}
		return INSTANCE;
	}
	
	public void SendLog(String pType,String pMesagge)
	{
		try{
			String url="http://logs-01.loggly.com/inputs/339868d6-cb7c-41c2-bcd2-36f0414041b6/tag/http/";
			URL object=new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
	
			JSONObject obj = new JSONObject();
	
	
			obj.put("type",pType);
			obj.put("message", pMesagge);
	
	
			OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
			wr.write(obj.toString());
			wr.flush();
	
			//display what returns the POST request
	
			StringBuilder sb = new StringBuilder();  
	
			int HttpResult =con.getResponseCode(); 
	
			if(HttpResult ==HttpURLConnection.HTTP_OK){
	
			    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));  
	
			    String line = null;  
	
			    while ((line = br.readLine()) != null) {  
			    sb.append(line + "\n");  
			    }  
	
			    br.close();  
			    System.out.println("RESPUESTA DE LOOGLY");
			    System.out.println(""+sb.toString());  
	
			}else{
			    System.out.println("ERROR LOGGLY\n"+con.getResponseMessage());  
			}  
		}
		catch(Exception ex)
		{
			System.out.println("ERROR EXCEPCION LOGGLY\n"+ex.getMessage());  
		}
	}
	
}
