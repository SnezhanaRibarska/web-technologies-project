package com.webproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webproject.models.User;
import com.webproject.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	public List<User> findAll() {
        return userRepository.findAll();
    }
	
	public User validateUser(User u) {
		List<User> users = findAll();
		for (User user : users) {
			if(user.getUserName().equalsIgnoreCase(u.getUserName()) && 
					user.getPassword().equals(u.getPassword())) {
				return user;
			}
		}
		return  null;
	}

}
