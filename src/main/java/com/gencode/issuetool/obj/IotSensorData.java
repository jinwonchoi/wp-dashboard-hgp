package com.gencode.issuetool.obj;

public class IotSensorData extends Pojo {
	protected String createdDate;
	protected String deviceId;
	protected double humid;
	protected double smoke;
	protected double temperature;
	protected double coGas;
	protected String flare;
	
	public IotSensorData() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public IotSensorData(String createdDate, String deviceId, double humid, double smoke, double temperature,
			double coGas, String flare) {
		super();
		this.createdDate = createdDate;
		this.deviceId = deviceId;
		this.humid = humid;
		this.smoke = smoke;
		this.temperature = temperature;
		this.coGas = coGas;
		this.flare = flare;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public double getHumid() {
		return humid;
	}

	public void setHumid(double humid) {
		this.humid = humid;
	}

	public double getSmoke() {
		return smoke;
	}

	public void setSmoke(double smoke) {
		this.smoke = smoke;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getCoGas() {
		return coGas;
	}

	public void setCoGas(double coGas) {
		this.coGas = coGas;
	}

	public String getFlare() {
		return flare;
	}

	public void setFlare(String flare) {
		this.flare = flare;
	}
	
	
}
