package com.example.demo;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.Bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void findAll() {
		List<User> userList = userMapper.selectList(null);
		for (User user : userList) {
			System.out.println(user);
		}
	}

	@Test
	public void addUser() {
		User user = new User();
		user.setAge(23);
		user.setName("jyf");
		user.setEmail("yefanjiang@foxmail.com");
		userMapper.insert(user);
	}

}
