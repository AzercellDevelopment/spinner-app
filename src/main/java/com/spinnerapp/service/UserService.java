package com.spinnerapp.service;

import com.spinnerapp.mapper.UserMapper;
import com.spinnerapp.model.dto.UserRequestDto;
import com.spinnerapp.model.dto.UserResponseDto;
import com.spinnerapp.model.entity.User;
import com.spinnerapp.repository.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepositoryCustom userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }


    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User user = userMapper.dtoToEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.addUser(user);
        return userMapper.entityToDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToDto(userRepository.getAllUsers());
    }

    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });
        return userMapper.entityToDto(user);
    }

    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        if (user != null) {
            user.setId(userId);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.updateUser(user);
        UserResponseDto responseDto = userMapper.entityToDto(user);
        return responseDto;


    }

    public UserResponseDto deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        userRepository.deleteUser(user);
        UserResponseDto userResponseDto = userMapper.entityToDto(user);
        return userResponseDto;
    }
}
