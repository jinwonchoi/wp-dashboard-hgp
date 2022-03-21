package com.gencode.issuetool.prototype.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface PrtAbstractDao<T> {
//	long getLastInsertId();
    int getTotalCount(String query, MapSqlParameterSource namedParameters);
}