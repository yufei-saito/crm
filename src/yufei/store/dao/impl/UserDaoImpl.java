package yufei.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import yufei.store.dao.UserDao;
import yufei.store.domain.User;
import yufei.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void regist(User u) throws SQLException {
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql, u.getUid(),u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),u.getTelephone(),u.getBirthday(),u.getSex(),u.getState(),u.getCode());
	}

	@Override
	public User activeUser(String code) throws SQLException {
		String sql="select * from user where code=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User u =(User)qr.query(sql, new BeanHandler<User>(User.class),code);
		if(u!=null) {
			String sql2 = "update user set code=null,state=1 where uid=?";
			qr.update(sql2,u.getUid());
		}
		return u;
	}

	@Override
	public User login(String username, String password) throws SQLException {
		String sql="select * from user where username=? and password=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User u =(User)qr.query(sql, new BeanHandler<User>(User.class),username,password);
		return u;
	}
	
}
