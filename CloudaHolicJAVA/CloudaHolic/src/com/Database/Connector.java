package com.Database;

import redis.clients.jedis.Jedis;

public class Connector {

	private String HOST = "pub-redis-12847.us-east-1-2.5.ec2.garantiadata.com";
	private int PORT = 12847;
	private String PASSWORD = "1234zxcv";
	
	private static Connector INSTANCE;
	private Jedis DATABASE;
	private Connector()
	{
		DATABASE = new Jedis(HOST,PORT);
		DATABASE.auth(PASSWORD);
		
		DATABASE.connect();
	}
	
	protected static Connector GetInstance()
	{
		
		INSTANCE = new Connector();
		
		return INSTANCE;
	}
	
	protected Jedis GetDatabase()
	{
		if(DATABASE == null)
		{
			// MANDAR A LOOGLY
			DATABASE = new Jedis(HOST,PORT);
			DATABASE.auth(PASSWORD);
			DATABASE.connect();
		}
		return DATABASE;
	}
	
	protected void DisconnectServer()
	{
		DATABASE.disconnect();
	}
}
