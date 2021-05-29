package com.gencode.issuetool.io;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.gencode.issuetool.etc.Utils;

public class SearchMapObj {
	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	String andStr;
	boolean useLike = true;
	String alias;
	
	public SearchMapObj(Map<String, String> map) {
		this.alias = "";
		generateStr(map);
	}

	public SearchMapObj(Map<String, String> map, String alias) {
		this.alias = alias;
		generateStr(map);
	}

	public SearchMapObj(Map<String, String> map, boolean useLike) {
		this.alias = "";
		this.useLike = useLike;
		generateStr(map);
	}

	public SearchMapObj(Map<String, String> map, String alias, boolean useLike) {
		this.alias = alias;
		this.useLike = useLike;
		generateStr(map);
	}
	
	public void generateStr(Map<String, String> map) {

		StringBuffer sb = new StringBuffer();
		if (map == null || map.size()==0) {
			andStr = "";
		} else {
			map.forEach((k,v)->{
				String unCamelK=Utils.unCamel(k);
				System.out.println(unCamelK+":"+v);
				if (useLike) {
					sb.append(String.format(" and %s%s like :%s",alias,unCamelK, k));
					this.namedParameters.addValue(k, "%" + v + "%");
				} else {
					sb.append(String.format(" and %s%s = :%s",alias,unCamelK, k));
					this.namedParameters.addValue(k, v);
				}
			});
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
