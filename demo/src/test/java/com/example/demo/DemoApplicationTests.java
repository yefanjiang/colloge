package com.example.demo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Bean.User;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
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
		user.setName("222");
		user.setEmail("222@foxmail.com");
		//user.setId(6L);
		userMapper.insert(user);
	}

	@Test
	public void updateUser() {
		User user = userMapper.selectById(1315619263014305793L);
		user.setAge(118);
		user.setVersion(user.getVersion() - 1);
		userMapper.updateById(user);
	}

	@Test
	public void selectPage() {
		Page<User> page = new Page<>(1, 5);
		userMapper.selectPage(page, null);

		page.getRecords().forEach(System.out::println);
		System.out.println(page.getCurrent());
		System.out.println(page.getPages());
		System.out.println(page.getSize());
		System.out.println(page.getTotal());
		System.out.println(page.hasNext());
		System.out.println(page.hasPrevious());
	}

	@Test
	public void logicalDelete() {
		System.out.println(userMapper.deleteById(6L));
	}

}
