package bkdn.pbl6.main.services;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import bkdn.pbl6.main.models.User;

@Service
public class UserServiceImpl implements UserService {

	private ArrayList<User> users = new ArrayList<User>();
	private AtomicInteger atomicInteger = new AtomicInteger();

	@Override
	public User add(User user) {
		for (User u : users) {
			if (user.getEmail().equals(u.getEmail())) {
				return null;
			}
		}
		user.setId(atomicInteger.incrementAndGet());
		users.add(user);
		System.out.println(users.toString());
		return user;
	}

	@Override
	public User findById(Integer id) {
		for (int i = 0; i < users.size(); ++i) {
			if (users.get(i).getId() == id) {
				System.out.println("Thu tu: " + i + ", Id: " + id);
				return users.get(i);
			}
		}
		return null;
	}
	
	@Override
	public User findByEmail(String email) {
		for (User user : users) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}

}
