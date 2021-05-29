package com.gencode.issuetool.io;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.gencode.issuetool.etc.Utils;

public class SearchMapByOrObj {
	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	String orStr;
	boolean isFirst = true;
	public SearchMapByOrObj(Map<String, String> map) {
		generateStr(map,"");
	}
	public SearchMapByOrObj(Map<String, String> map, String alias) {
		generateStr(map,alias);
	}

	public void generateStr(Map<String, String> map, String alias) {
		StringBuffer sb = new StringBuffer();
		if (map==null || map.size() == 0) {
			orStr = "";
		} else {
			sb.append(" and (");
			map.forEach((k,v)->{
				String unCamelK=Utils.unCamel(k);
				System.out.println(unCamelK+":"+v);
				if (isFirst)
					sb.append(String.format(" %s%s like :%s",alias, unCamelK, k));
				else 
					sb.append(String.format(" or %s%s like :%s",alias,unCamelK, k));
				this.namedParameters.addValue(k, "%" + v + "%");
				isFirst = false;
			});
			sb.append(")");
			orStr = sb.toString();
		}
	}
	
	public MapSqlParameterSource params() {
		return namedParameters;
	}

	public Map<String, Object> map() {
		return namedParameters.getValues();
	}

	public String orQuery() {
		return orStr;
	}
}
