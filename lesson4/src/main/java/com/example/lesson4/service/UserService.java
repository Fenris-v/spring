package com.example.lesson4.service;

import com.example.lesson4.dto.request.UserRequest;
import com.example.lesson4.dto.response.UserDto;
import com.example.lesson4.exception.EntityNotFoundException;
import com.example.lesson4.model.User;
import com.example.lesson4.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public Page<UserDto> getUsers(int page, int limit) {
        Page<User> users = userRepository.findAllByOrderById(PageRequest.of(page, limit));
        return users.map((element) -> modelMapper.map(element, UserDto.class));
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public UserDto createUser(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        user = userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public UserDto editUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(userRequest, user);
        user = userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
    }
}
