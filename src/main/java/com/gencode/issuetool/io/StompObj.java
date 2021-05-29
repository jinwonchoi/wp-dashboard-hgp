package com.gencode.issuetool.io;

import java.io.Serializable;

import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.obj.Pojo;

public class StompObj extends Pojo implements Serializable {
	String type;
	String sendDtm;
	String payload;
	
	public StompObj() {
		super();
	}
	
	public StompObj(String type, String t) {
		this.type = type;
		sendDtm = Utils.yyyyMMddHHmmssSSS();
		payload = t;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSendDtm() {
		return sendDtm;
	}
	public void setSendDtm(String sendDtm) {
		this.sendDtm = sendDtm;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
}
