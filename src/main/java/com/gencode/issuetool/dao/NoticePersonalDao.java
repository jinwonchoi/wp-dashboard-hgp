package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.obj.NoticePersonal;

public interface NoticePersonalDao extends Dao<NoticePersonal> {
	void deleteByNoticeId(long noticeId);
	void deleteByUserId(long userId);
	void forceDelete(NoticePersonal noticePersonal);
	long insertAll(long noticeId);
}
