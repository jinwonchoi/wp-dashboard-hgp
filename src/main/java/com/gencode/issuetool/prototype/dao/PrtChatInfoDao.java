package com.gencode.issuetool.prototype.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.prototype.obj.ChatInfo;

public interface PrtChatInfoDao extends PrtDao<ChatInfo> {
	void resetUnreadCnt(long chatId);
}
