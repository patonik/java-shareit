package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

@SpringBootTest
class ShareItTests {

	@Test
	void contextLoads() {
		UserRepository userRepository = Mockito.mock(UserRepository.class);
		UserMapper userMapper = Mockito.mock(UserMapper.class);
		UserService userService = new UserServiceImpl(userRepository, userMapper);
	}

}
