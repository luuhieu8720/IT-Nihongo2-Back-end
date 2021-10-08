package bkdn.pbl6.main.services;

import bkdn.pbl6.main.models.User;

public interface UserService {

	public User add(User user);

	public User findById(Integer id);

	public User findByEmail(String email);
}
