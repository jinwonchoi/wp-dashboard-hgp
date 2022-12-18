/**=========================================================================================
<overview>공통 Pojo 객체 정의
  </overview>
==========================================================================================*/
package com.gencode.issuetool.obj;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gencode.issuetool.etc.Utils;

public class Pojo {

	public String toString() {
		try {
			return Utils.objToStr(this);
		} catch (JsonProcessingException e) {
			return "N/A";
		}
	}
}
