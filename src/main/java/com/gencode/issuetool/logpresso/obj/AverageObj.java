/**=========================================================================================
<overview>로그프레소 연동 화재지수정보 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.logpresso.obj;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.obj.Pojo;

public class AverageObj extends Pojo {
	String flag;
	String time;
	int nCount;
	int fireIdx; //*10 한 값
	int maxFireIdx;
	
	public AverageObj() {
		super();
	}

	public boolean isNew(String flagCmp) {
		return (!flagCmp.equals(this.flag))&&(nCount>0);
	}
	public void add(String flagCmp, String _time, String fireIdx, String maxFireIdx) {
		if (!flagCmp.equals(this.flag)) {
			this.flag = flagCmp;
			nCount=0;
			this.fireIdx=0;
			this.maxFireIdx=0;
		}
		if (!_time.equals(this.time)) {
			this.time = _time;
		}
		nCount++;
		this.fireIdx+=Double.parseDouble(fireIdx)*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val();
		this.maxFireIdx+=Double.parseDouble(maxFireIdx)*Constant.DASHBOARD_VALUE_DECIAML_SIZE.val();
	}
	public String getFlag() {
		return flag;
	}
	public String getTime() {
		return time;
	}
	public int getFireIdx() {
		return (fireIdx/nCount);
	}
	public int getMaxFireIdx() {
		return (maxFireIdx/nCount);
	}
	public String getFireIdxStr() {
		return new BigDecimal(
				(((double)(fireIdx/nCount))
				/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val())).setScale(1, RoundingMode.HALF_UP).toString();
	}
	public String getMaxFireIdxStr() {
		return new BigDecimal(
				(((double)(maxFireIdx/nCount))
				/Constant.DASHBOARD_VALUE_DECIAML_SIZE.val())).setScale(1, RoundingMode.HALF_UP).toString();
	}
}
