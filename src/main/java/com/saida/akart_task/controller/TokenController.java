package com.saida.akart_task.controller;

import com.saida.akart_task.dto.request.UserRequestDto;
import com.saida.akart_task.dto.response.TokenDto;
import com.saida.akart_task.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping(value = "/createToken")
    public TokenDto createToken(@RequestBody UserRequestDto userRequestDto) {
        System.out.println("akarta sorgu geldi");
        return tokenService.createToken(userRequestDto);
    }
}
