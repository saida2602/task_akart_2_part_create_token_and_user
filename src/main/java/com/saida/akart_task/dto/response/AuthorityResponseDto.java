package com.saida.akart_task.dto.response;

import lombok.Data;


@Data
public class AuthorityResponseDto {

    private Long id;
    private String authority;
    private Long userId;

}
