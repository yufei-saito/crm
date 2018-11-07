package yufei.store.dao;

import java.sql.SQLException;

import yufei.store.domain.User;

public interface UserDao {
	public abstract void regist(User u)throws SQLException;

	public abstract User activeUser(String code)throws SQLException;

	public abstract User login(String username, String password)throws SQLException;
}
