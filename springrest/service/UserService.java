package rest.service;

import java.util.List;

import rest.domain.User;

public interface UserService {
	public User findById(long uid);

	public List<User> findAllUsers();

	public boolean isUserExist(User user);

	public int saveUser(User user);

	public int updateUser(User user);

	public int deleteUserById(long uid);

	public int deleteAllUsers();
}
