/**=========================================================================================
<overview>조회시 인자처리 객체
  </overview>
==========================================================================================*/
package com.gencode.issuetool.io;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.gencode.issuetool.etc.Utils;

public class ParamMapObj {
	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	String andStr;
	boolean isFirst = true;
	public ParamMapObj(Map<String, String> map) {
		generateStr(map,"");
	}
	public ParamMapObj(Map<String, String> map, String alias) {
		generateStr(map,alias);
	}

	public void generateStr(Map<String, String> map, String alias) {
		StringBuffer sb = new StringBuffer();
		if (map==null || map.size() == 0) {
			andStr = "";
		} else {
			sb.append(" and (");
			map.forEach((k,v)->{
				String unCamelK=Utils.unCamel(k);
				System.out.println(unCamelK+":"+v);
				if (isFirst)
					sb.append(String.format(" %s%s=:%s",alias, unCamelK, k));
				else 
					sb.append(String.format(" and %s%s=:%s",alias,unCamelK, k));
				this.namedParameters.addValue(k, v);
				isFirst = false;
			});
			sb.append(")");
			andStr = sb.toString();
		}
	}
	
	public MapSqlParameterSource params() {
		return namedParameters;
	}

	public Map<String, Object> map() {
		return namedParameters.getValues();
	}

	public String andQuery() {
		return andStr;
	}
}
