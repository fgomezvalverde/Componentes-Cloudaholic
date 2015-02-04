package com.CloudaHolic;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Utiles.LogglyManager;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class CloudaHolicServletDatabase extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String team_name =req.getParameter("name");
		QueueFactory.getDefaultQueue().add(
		        TaskOptions.Builder.withUrl("/workers/WorkerDatabase").param("team", team_name));
		LogglyManager.GetIntance().SendLog("TASK_QUEUE:CloudaHolicServletDatabase", "Se agrego a taskqueue");
		System.out.println("Se agrego a taskqueue");
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
	}
}
