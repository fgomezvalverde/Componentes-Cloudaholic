package com.CloudaHolic;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.Database.Manager;
import com.Model.User;
import com.Utiles.Doc;
import com.Utiles.LogglyManager;

public class CloudaHolicWorkerDatabase extends HttpServlet {
	
	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			JSONParser parser = new JSONParser();
			
			String team_name = req.getParameter("team");
			
			String data = Manager.GetInstance().GetData(team_name);
			
			Object obj;
		
			obj = parser.parse(data);
			JSONObject jsonObject = (JSONObject) obj;
			
			JSONArray msg = (JSONArray) jsonObject.get("USERS");
			
			Doc.CopiarTabla(msg);
			LogglyManager.GetIntance().SendLog("DB_GET&SAVE:CloudaHolicWorkerDatabase", "Se termino proceso de GDrive");
			System.out.println("DB_GET&SAVE:CloudaHolicWorkerDatabase,Se termino proceso de GDrive");
		} catch (Exception e) {
			LogglyManager.GetIntance().SendLog("ERROR-Exc:CloudaHolicServletDatabase", e.getMessage());
			System.out.println("ERROR-Exc:CloudaHolicServletDatabase"+ e.getMessage());
			
		}
		
		
		
	}
	
}