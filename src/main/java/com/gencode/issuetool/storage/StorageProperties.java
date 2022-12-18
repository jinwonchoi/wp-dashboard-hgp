/**=========================================================================================
<overview>파일처리 관련 설정정보
  </overview>
==========================================================================================*/
package com.gencode.issuetool.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {
	
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
