package com.CloudaHolic;
import java.io.IOException;

import javax.servlet.http.*;

import com.Utiles.LogglyManager;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class CloudaHolicServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
		QueueFactory.getDefaultQueue().add(
		        TaskOptions.Builder.withUrl("/workers/WorkerReceiver")
		          .param("json", req.getParameter("json")));
		
		LogglyManager.GetIntance().SendLog("TASK_QUEUE:CloudaHolicServlet", "Se agrego a taskqueue");
		System.out.println("TASK_QUEUE:CloudaHolicServlet,Se agrego a taskqueue");
		resp.getWriter().println("BIEN");
	}
}
