package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Optional;

public interface LoginUserDao {
	void register(String t);
	Optional<String> load(String t);
	boolean contains(String t);
	void delete(String t);
	Optional<List<String>> loadAll();
}
