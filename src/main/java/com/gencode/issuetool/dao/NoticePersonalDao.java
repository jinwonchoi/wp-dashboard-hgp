package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.obj.NoticePersonal;

public interface NoticePersonalDao extends Dao<NoticePersonal> {
	long deleteByNoticeId(long noticeId);
	long deleteByUserId(long userId);
	long forceDelete(NoticePersonal noticePersonal);
	long insertAll(long noticeId);
}
