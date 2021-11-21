package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.gencode.issuetool.exception.MethodUnsupportableException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.obj.User;

public interface Dao<T> extends AbstractDao {

    long register(T t);
    Optional<T> load(long id);
    long delete(long id);
    long update(T t);
    Optional<List<T>> loadAll();
	Optional<List<T>> search(Map<String, String> map);
	Optional<PageResultObj<List<T>>> search(PageRequest req);
}