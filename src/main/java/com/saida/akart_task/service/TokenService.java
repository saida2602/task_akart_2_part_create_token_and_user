package com.saida.akart_task.service;

import com.saida.akart_task.config.JwtTokenOperation;
import com.saida.akart_task.config.MessageGenerator;
import com.saida.akart_task.domain.User;
import com.saida.akart_task.dto.request.UserRequestDto;
import com.saida.akart_task.dto.response.TokenResponseDto;
import com.saida.akart_task.error.ErrorMessage;
import com.saida.akart_task.error.ServiceException;
import com.saida.akart_task.mapper.TokenMapper;
import com.saida.akart_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository repository;
    private final JwtTokenOperation tokenOperation;
    private final TokenMapper tokenMapper;
    private final MessageGenerator messageGenerator;

    public TokenResponseDto createToken(UserRequestDto userRequestDto) {
        User user = repository.findByUsername(userRequestDto.getUsername())
        .orElseThrow(() -> new ServiceException(messageGenerator.getMessage(ErrorMessage.USER_NOT_FOUND)));
        String token = tokenOperation.createToken(user);
        log.info("token {}", token);
        return tokenMapper.toTokenDto(token);
    }

}
