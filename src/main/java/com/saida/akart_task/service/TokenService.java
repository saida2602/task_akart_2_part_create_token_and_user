package com.saida.akart_task.service;

import com.saida.akart_task.config.JwtTokenOperation;
import com.saida.akart_task.config.MessageGenerator;
import com.saida.akart_task.domain.User;
import com.saida.akart_task.dto.request.UserRequestDto;
import com.saida.akart_task.dto.response.TokenDto;
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

    public TokenDto createToken(UserRequestDto userRequestDto) {
        User user = repository.findByUsername(userRequestDto.getUsername())
        .orElseThrow(() -> new ServiceException(messageGenerator.getMessage(ErrorMessage.USER_NOT_FOUND)));
        // bir neceh eyni adli user ola bilermi
        System.out.println("user="+user);
        String token = tokenOperation.createToken(user);
        log.info("token {}", token);
        System.out.println("tokendto="+ tokenMapper.toTokenDto(token));
        return tokenMapper.toTokenDto(token);
    }

//    public TokenDto createToken(Long customerId) {
//        Customer customer = findCustomerById(customerId);
//        String token = tokenOperation.createToken(customer);
//        log.info("token {}", token);
//        System.out.println("tokendto="+ tokenMapper.toTokenDto(token));
//        return tokenMapper.toTokenDto(token);
//    }

//    public Customer findCustomerById(Long id) {
//        Customer customer = repository.findById(id)
//                .orElseThrow(() -> new CustomerNotFoundException(messageGenerator
//                        .getMessage(ErrorMessage.CUSTOMER_NOT_FOUND) + id));
//        return customer;
//    }
}
