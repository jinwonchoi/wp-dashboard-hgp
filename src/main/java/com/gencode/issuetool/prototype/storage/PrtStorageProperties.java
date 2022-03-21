package com.gencode.issuetool.prototype.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class PrtStorageProperties {
	
	private String location = "upload-dir";
	private String profileImgLocation = "img/profile";
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfileImgLocation() {
		return profileImgLocation;
	}

	public void setProfileImgLocation(String profileImgLocation) {
		this.profileImgLocation = profileImgLocation;
	}

	
}
