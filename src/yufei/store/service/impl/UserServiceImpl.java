package yufei.store.service.impl;

import java.sql.SQLException;

import yufei.store.dao.UserDao;
import yufei.store.dao.impl.UserDaoImpl;
import yufei.store.domain.User;
import yufei.store.service.UserService;
import yufei.store.utils.BeanFactory;

public class UserServiceImpl implements UserService {
	UserDao dao = (UserDao)BeanFactory.getBean("UserDao");

	@Override
	public void regist(User u) throws SQLException {
		dao.regist(u);
	}

	@Override
	public User activeUser(String code) throws SQLException {
		return dao.activeUser(code);

	}

	@Override
	public User login(String username, String password) throws SQLException {
		User u = dao.login(username, password);
		if (u == null) {
			throw  new RuntimeException("用户名或密码有误!");
		} else if (0 == (u.getState())) {
			throw  new RuntimeException("账户未激活!");
		}else {
			return u;
		}

	}

}
