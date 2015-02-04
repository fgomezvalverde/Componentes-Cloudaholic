package com.Utiles;

import com.Model.Team;
import com.Model.User;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

import java.net.URL;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Doc {
	private static String USERNAME="fafa.gttm91@gmail.com";
	private static String PASSWORD="gomezvalverde5";
	private static int TIMEOUT = 90000;
	
	
	public static void CopiarTabla(JSONArray pTeam){
		//CrearSpreadSheet("DOTEROS");
		String[] columnas = new String[]{"NAME","COMMITS","TASKS","WORKED","CHAT","MONTH","DATE"};
		String[] tupla;
		//RenombrarWorkSheet(pTeam.NAME,pTeam.size(),columnas.length);
		
		//CrearEncabezado(columnas, 0);
		try{
			for(int cont=0;cont < pTeam.size();cont++){
				JSONObject jsonObject= null;
				jsonObject = (JSONObject) pTeam.get(cont);
				tupla = new String[]{jsonObject.get("NAME")+"",jsonObject.get("FILES_UPLOADED")+"",jsonObject.get("HOME_WORKS")+"",jsonObject.get("REGISTRED_HOURS")+"min",
						jsonObject.get("CHAT_HOURS")+"msg",jsonObject.get("MONTH")+"",jsonObject.get("DATE")+""};
				AgregarTupla(columnas, tupla,0);
				
				
				LogglyManager.GetIntance().SendLog("GDrive:Doc", "Se guardo archivo");
				System.out.println("GDrive:Doc, Se guardo archivo");
			}
		}
		catch(Exception e)
		{
			LogglyManager.GetIntance().SendLog("GDrive:Doc", e.getMessage());
			System.out.println("GDrive:Doc"+e.getMessage());
		}
	}
	
	private static void RenombrarWorkSheet(String nombretabla,int filas, int columnas){
		try{
		    SpreadsheetService  service = new SpreadsheetService ("MyDocumentsListIntegration-v1");
		    service.setUserCredentials(USERNAME, PASSWORD);
		    service.setConnectTimeout(TIMEOUT);
		    // Define the URL to request.  This should never change.
		    URL SPREADSHEET_FEED_URL = new URL(
		        "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		    
		 // Make a request to the API and get all spreadsheets.
		    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
		        SpreadsheetFeed.class);
		    List<SpreadsheetEntry> spreadsheets = feed.getEntries();		    

		    // TODO: Choose a spreadsheet more intelligently based on your
		    // app's needs.		    
		    SpreadsheetEntry spreadsheet = spreadsheets.get(0);		    
		    
		    WorksheetFeed worksheetFeed = service.getFeed(spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		    List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		    WorksheetEntry worksheet = worksheets.get(0);

		    // Update the local representation of the worksheet.
		    worksheet.setTitle(new PlainTextConstruct(nombretabla));
		    worksheet.setColCount(columnas);
		    worksheet.setRowCount(filas+1);

		    // Send the local representation of the worksheet to the API for
		    // modification.
		    worksheet.update();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void CrearWorkSheet(String nombretabla,int filas, int columnas){
		try{
		    SpreadsheetService  service = new SpreadsheetService ("MyDocumentsListIntegration-v1");
		    service.setUserCredentials(USERNAME, PASSWORD);
		    service.setConnectTimeout(TIMEOUT);
		    // Define the URL to request.  This should never change.
		    URL SPREADSHEET_FEED_URL = new URL(
		        "https://spreadsheets.google.com/feeds/spreadsheets/private/full");

		    // Make a request to the API and get all spreadsheets.
		    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,SpreadsheetFeed.class);
		    List<SpreadsheetEntry> spreadsheets = feed.getEntries();
		    
		    if (spreadsheets.size() == 0) {
		      // TODO: There were no spreadsheets, act accordingly.
		    }

		    // TODO: Choose a spreadsheet more intelligently based on your
		    // app's needs.
		    SpreadsheetEntry spreadsheet = spreadsheets.get(0);		    
		    //System.out.println(spreadsheet.getTitle().getPlainText());

		    // Create a local representation of the new worksheet.
		    WorksheetEntry worksheet = new WorksheetEntry();
		    worksheet.setTitle(new PlainTextConstruct(nombretabla));
		    worksheet.setColCount(columnas);
		    worksheet.setRowCount(filas+1);

		    // Send the local representation of the worksheet to the API for
		    // creation.  The URL to use here is the worksheet feed URL of our
		    // spreadsheet.
		    URL worksheetFeedUrl = spreadsheet.getWorksheetFeedUrl();
		    service.insert(worksheetFeedUrl, worksheet);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void AgregarTupla(String[] nombrecols,String[] tupla,int numtabla){		
		try{
			SpreadsheetService  service = new SpreadsheetService ("MyDocumentsListIntegration-v1");
		    service.setUserCredentials(USERNAME, PASSWORD);
		    service.setConnectTimeout(TIMEOUT);
		    URL SPREADSHEET_FEED_URL = new URL(
		            "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		    
		 // Make a request to the API and get all spreadsheets.
		    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
		        SpreadsheetFeed.class);
		    List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		    if (spreadsheets.size() == 0) {
		      // TODO: There were no spreadsheets, act accordingly.
		    }

		    // TODO: Choose a spreadsheet more intelligently based on your
		    // app's needs.
		    SpreadsheetEntry spreadsheet = spreadsheets.get(0);
		    //System.out.println(spreadsheet.getTitle().getPlainText());

		    // Get the first worksheet of the first spreadsheet.
		    // TODO: Choose a worksheet more intelligently based on your
		    // app's needs.
		    WorksheetFeed worksheetFeed = service.getFeed(
		        spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		    List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		    WorksheetEntry worksheet = worksheets.get(numtabla);

		    // Fetch the list feed of the worksheet.
		    URL listFeedUrl = worksheet.getListFeedUrl();
		    ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

		    // Create a local representation of the new row.
		    ListEntry row = new ListEntry();		 
		    for(int i=0;i<nombrecols.length;i++){
		    	row.getCustomElements().setValueLocal(nombrecols[i], tupla[i]);
		    }		    		   

		    // Send the new row to the API for insertion.
		    row = service.insert(listFeedUrl, row);

		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private static void CrearEncabezado(String[] nombrecol,int numtabla){
		
		try{
			SpreadsheetService  service = new SpreadsheetService ("MyDocumentsListIntegration-v1");
		    service.setUserCredentials(USERNAME, PASSWORD);
		    service.setConnectTimeout(TIMEOUT);
		    URL SPREADSHEET_FEED_URL = new URL(
		            "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		    
		    SpreadsheetFeed resultFeed= service.getFeed (SPREADSHEET_FEED_URL,
		    SpreadsheetFeed.class);

		    List <SpreadsheetEntry> spreadsheets= resultFeed.getEntries ();
		    SpreadsheetEntry spreadsheetEntry= spreadsheets.get(0);

		    URL worksheetFeedUrl= spreadsheetEntry.getWorksheetFeedUrl ();
		    //log.severe (worksheetFeedUrl.toString ());
		    WorksheetFeed worksheetFeed= service.getFeed (
		    worksheetFeedUrl, WorksheetFeed.class);

		    List <WorksheetEntry> worksheetEntrys= worksheetFeed.getEntries ();
		    WorksheetEntry worksheetEntry= worksheetEntrys.get(numtabla);

		    // Write header line into Spreadsheet
		    URL cellFeedUrl= worksheetEntry.getCellFeedUrl ();
		    CellFeed cellFeed= service.getFeed (cellFeedUrl,
		    CellFeed.class);
		    	  
		    for(int i=1;i<=nombrecol.length;i++){
		    	CellEntry cellEntry= new CellEntry (1, i, nombrecol[i-1]);
			    cellFeed.insert (cellEntry);
		    }		    	  

		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private static String CrearSpreadSheet(String nombre){
		try{			
		    DocsService  service = new DocsService ("MyDocumentsListIntegration-v1");
		    service.setProtocolVersion(DocsService.Versions.V3);
		    service.setUserCredentials(USERNAME, PASSWORD);
		    service.setConnectTimeout(TIMEOUT);
		    // Define the URL to request.  This should never change.
		    URL SPREADSHEET_FEED_URL = new URL(
		    		"https://docs.google.com/feeds/default/private/full/");

		    String title = nombre+"-"+new Date().toString();
		    com.google.gdata.data.docs.SpreadsheetEntry  newEntry = new com.google.gdata.data.docs.SpreadsheetEntry ();
		    newEntry.setTitle(new PlainTextConstruct(title));
		    
		    com.google.gdata.data.docs.SpreadsheetEntry spreadSheet =service.insert(SPREADSHEET_FEED_URL, newEntry);
		    return title;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	
}
