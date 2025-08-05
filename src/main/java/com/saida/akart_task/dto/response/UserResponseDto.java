package com.saida.akart_task.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {

    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<AuthorityResponseDto> authorities;
}
