/**=========================================================================================
<overview>상위 추상 로그프레소 접근처리 DAO 구현처리
  </overview>
==========================================================================================*/
package com.gencode.issuetool.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class AbstractLogpressoDaoImpl<T> {
	protected final static Logger logger = LoggerFactory.getLogger(AbstractLogpressoDaoImpl.class);
	
	public AbstractLogpressoDaoImpl() {
	}
}
