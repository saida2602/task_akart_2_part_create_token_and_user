package com.saida.akart_task.dto.request;

import com.saida.akart_task.domain.Authority;
import com.saida.akart_task.error.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDto {

    @NotNull(message = ErrorMessage.INVALID_USERNAME)
    private String username;
    @NotNull(message = ErrorMessage.INVALID_PASSWORD)
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<Authority> authorities;

}
