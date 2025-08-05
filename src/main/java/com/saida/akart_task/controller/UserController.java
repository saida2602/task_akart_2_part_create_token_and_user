package com.saida.akart_task.controller;

import com.saida.akart_task.dto.request.UserRequestDto;
import com.saida.akart_task.dto.response.UserResponseDto;
import com.saida.akart_task.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/createUser")
    public UserResponseDto createToken(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @GetMapping(value = "/getUser/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
