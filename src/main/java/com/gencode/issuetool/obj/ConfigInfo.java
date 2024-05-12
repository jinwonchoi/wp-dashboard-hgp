/**=========================================================================================
<overview>공간기준정보관련 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

public class ConfigInfo extends Pojo {
	
	String logpressoApiKey;
	String logpressoWebsocketUrl;
	String cctvMediaServerUrl;
	String ketiServerPort;
	String dashboardPortForKeti;
	
	public ConfigInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConfigInfo(String logpressoApiKey, String logpressoWebsocketUrl, String cctvMediaServerUrl,
			String ketiServerPort, String dashboardPortForKeti) {
		super();
		this.logpressoApiKey = logpressoApiKey;
		this.logpressoWebsocketUrl = logpressoWebsocketUrl;
		this.cctvMediaServerUrl = cctvMediaServerUrl;
		this.ketiServerPort = ketiServerPort;
		this.dashboardPortForKeti = dashboardPortForKeti;
	}

	public String getLogpressoApiKey() {
		return logpressoApiKey;
	}

	public void setLogpressoApiKey(String logpressoApiKey) {
		this.logpressoApiKey = logpressoApiKey;
	}

	public String getLogpressoWebsocketUrl() {
		return logpressoWebsocketUrl;
	}

	public void setLogpressoWebsocketUrl(String logpressoWebsocketUrl) {
		this.logpressoWebsocketUrl = logpressoWebsocketUrl;
	}

	public String getCctvMediaServerUrl() {
		return cctvMediaServerUrl;
	}

	public void setCctvMediaServerUrl(String cctvMediaServerUrl) {
		this.cctvMediaServerUrl = cctvMediaServerUrl;
	}

	public String getKetiServerPort() {
		return ketiServerPort;
	}

	public void setKetiServerPort(String ketiServerPort) {
		this.ketiServerPort = ketiServerPort;
	}

	public String getDashboardPortForKeti() {
		return dashboardPortForKeti;
	}

	public void setDashboardPortForKeti(String dashboardPortForKeti) {
		this.dashboardPortForKeti = dashboardPortForKeti;
	}

}