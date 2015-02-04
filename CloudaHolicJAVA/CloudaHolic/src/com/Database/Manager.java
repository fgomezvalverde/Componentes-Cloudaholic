package com.Database;

public class Manager {
	
	private static Manager INSTANCE;
	
	public static Manager GetInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new Manager();
		}
		return INSTANCE;
	}
	
	public void AddSetData(String pKey,String pValue)
	{
		Connector.GetInstance().GetDatabase().set(pKey, pValue);
		Connector.GetInstance().DisconnectServer();
	}
	public String GetData(String pKey)
	{
		String result = Connector.GetInstance().GetDatabase().get(pKey);
		Connector.GetInstance().DisconnectServer();
		return result;
	}
	
}
