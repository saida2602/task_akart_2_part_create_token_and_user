package com.saida.akart_task.service;

import com.saida.akart_task.config.MessageGenerator;
import com.saida.akart_task.domain.Authority;
import com.saida.akart_task.domain.User;
import com.saida.akart_task.dto.request.UserRequestDto;
import com.saida.akart_task.dto.response.UserResponseDto;
import com.saida.akart_task.error.ErrorMessage;
import com.saida.akart_task.error.ServiceException;
import com.saida.akart_task.mapper.UserMapper;
import com.saida.akart_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MessageGenerator messageGenerator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(messageGenerator.
                        getMessage(ErrorMessage.USER_NOT_FOUND)));
        return user;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        if (userRepository.existsUserByUsername(username)) {
            throw new ServiceException(messageGenerator.getMessage(ErrorMessage.DUPLICATE_USERNAME) + username);
        }
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        User user = userMapper.toUser(userRequestDto, password);
        if (user.getAuthorities() != null) {
            for (Authority authority : user.getAuthorities()) {
                authority.setUser(user);
            }
        }
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(messageGenerator.
                getMessage(ErrorMessage.USER_NOT_FOUND) + userId));
        return userMapper.toUserResponseDto(user);
    }
}
