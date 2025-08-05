package com.saida.akart_task.controller;

import com.saida.akart_task.dto.request.UserRequestDto;
import com.saida.akart_task.dto.response.TokenResponseDto;
import com.saida.akart_task.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping(value = "/createToken")
    public TokenResponseDto createToken(@RequestBody UserRequestDto userRequestDto) {
        return tokenService.createToken(userRequestDto);
    }
}
