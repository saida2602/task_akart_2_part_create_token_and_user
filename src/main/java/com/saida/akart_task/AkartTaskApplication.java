package com.saida.akart_task;

import com.saida.akart_task.config.JwtTokenOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class AkartTaskApplication {

    private final JwtTokenOperation jwtTokenOperation;

    public static void main(String[] args) {
        SpringApplication.run(AkartTaskApplication.class, args);
    }

}
