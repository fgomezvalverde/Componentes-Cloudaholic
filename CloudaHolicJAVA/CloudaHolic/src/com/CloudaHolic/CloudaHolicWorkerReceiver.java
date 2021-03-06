package com.CloudaHolic;


import java.io.IOException;

import javax.servlet.http.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.Database.Manager;
import com.Utiles.LogglyManager;


@SuppressWarnings("serial")
public class CloudaHolicWorkerReceiver extends HttpServlet {
	JSONParser parser = new JSONParser();
	Object obj;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {	
			String json_team = req.getParameter("json");
			obj = parser.parse(json_team);
			JSONObject jsonObj = (JSONObject) obj;
			Manager.GetInstance().AddSetData(jsonObj.get("NAME")+"", json_team);
			
			LogglyManager.GetIntance().SendLog("DB_ADD:CloudaHolicWorkerReceiver", "Se termino proceso de Save");
			System.out.println("DB_ADD:CloudaHolicWorkerReceiver,Agregando a DB");
		} catch (Exception e) {
			LogglyManager.GetIntance().SendLog("DB_ADD:CloudaHolicWorkerReceiver", e.getMessage());
			System.out.println("DB_ADD:CloudaHolicWorkerReceiver"+e.getMessage());
		}
		
	}

	
}
