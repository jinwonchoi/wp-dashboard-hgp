package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface AbstractDao<T> {
//	long getLastInsertId();
    int getTotalCount(String query, MapSqlParameterSource namedParameters);
}