package com.gencode.issuetool.prototype.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.prototype.obj.MessageLog;

public interface PrtMessageLogDao extends PrtDao<MessageLog> {
	long updateByChatId(long chatId, String status);
}
