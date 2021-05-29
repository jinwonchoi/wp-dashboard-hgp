package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.exception.TooManyRowException;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.obj.User;

public interface UserDao extends Dao<User>{
	Optional<User> login(String userid);
	Optional<User> load(String userid);
	void activate(User user);
	void delete(String userid);
}
