package com.Model;

public class User {
	public String NAME;
    public User(String nAME, int fILES_UPLOADED, int hOME_WORKS,
			int rEGISTRED_HOUR, int cHAT_HOURS, String dATE, String mONTH,
			int tYPE) {
		NAME = nAME;
		FILES_UPLOADED = fILES_UPLOADED;
		HOME_WORKS = hOME_WORKS;
		REGISTRED_HOUR = rEGISTRED_HOUR;
		CHAT_HOURS = cHAT_HOURS;
		DATE = dATE;
		MONTH = mONTH;
		TYPE = tYPE;
	}

	

	public int FILES_UPLOADED ;    // bit bucket

    public int HOME_WORKS;       //base camp

    public int REGISTRED_HOUR; //togl

    public int CHAT_HOURS;    //hipchat

    public String DATE ;

    public String MONTH;
    
    public int TYPE ;

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public int getFILES_UPLOADED() {
		return FILES_UPLOADED;
	}

	public void setFILES_UPLOADED(int fILES_UPLOADED) {
		FILES_UPLOADED = fILES_UPLOADED;
	}

	public int getHOME_WORKS() {
		return HOME_WORKS;
	}

	public void setHOME_WORKS(int hOME_WORKS) {
		HOME_WORKS = hOME_WORKS;
	}

	public int getREGISTRED_HOUR() {
		return REGISTRED_HOUR;
	}

	public void setREGISTRED_HOUR(int rEGISTRED_HOUR) {
		REGISTRED_HOUR = rEGISTRED_HOUR;
	}

	public int getCHAT_HOURS() {
		return CHAT_HOURS;
	}

	public void setCHAT_HOURS(int cHAT_HOURS) {
		CHAT_HOURS = cHAT_HOURS;
	}

	public String getDATE() {
		return DATE;
	}

	public void setDATE(String dATE) {
		DATE = dATE;
	}

	public String getMONTH() {
		return MONTH;
	}

	public void setMONTH(String mONTH) {
		MONTH = mONTH;
	}

	public int getTYPE() {
		return TYPE;
	}

	public void setTYPE(int tYPE) {
		TYPE = tYPE;
	}
    
    
}
