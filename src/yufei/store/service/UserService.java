package yufei.store.service;

import java.sql.SQLException;

import yufei.store.domain.User;

public interface UserService {
	public abstract void regist(User u)throws SQLException;

	public abstract User activeUser(String code)throws SQLException;

	public abstract User login(String username, String password)throws SQLException;
}
