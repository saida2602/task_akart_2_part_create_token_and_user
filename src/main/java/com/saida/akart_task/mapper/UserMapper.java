package com.saida.akart_task.mapper;


import com.saida.akart_task.domain.Authority;
import com.saida.akart_task.domain.User;
import com.saida.akart_task.dto.request.UserRequestDto;
import com.saida.akart_task.dto.response.AuthorityResponseDto;
import com.saida.akart_task.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "username",source = "userRequestDto.username")
    @Mapping(target = "accountNonExpired",source = "userRequestDto.accountNonExpired")
    @Mapping(target = "accountNonLocked",source = "userRequestDto.accountNonLocked")
    @Mapping(target = "credentialsNonExpired",source = "userRequestDto.credentialsNonExpired")
    @Mapping(target = "enabled",source = "userRequestDto.enabled")
    @Mapping(target = "authorities",source = "userRequestDto.authorities")
    @Mapping(target = "password",source = "password")
    User toUser(UserRequestDto userRequestDto,String password);

    @Mapping(target = "authorities",source = "user", qualifiedByName = "mapAuthority")
    UserResponseDto toUserResponseDto(User user);

    @Named("mapAuthority")
    default List<AuthorityResponseDto> mapAuthority(User user) {
        List<Authority> authorities = user.getAuthorities();
        List<AuthorityResponseDto> authorityResponseDtos = new ArrayList<>();
        for (Authority authority : authorities) {
            AuthorityResponseDto authorityResponseDto = new AuthorityResponseDto();
            authorityResponseDto.setId(authority.getId());
            authorityResponseDto.setAuthority(authority.getAuthority());
            authorityResponseDto.setUserId(user.getId());
            authorityResponseDtos.add(authorityResponseDto);
        }
         return   authorityResponseDtos;
    }
}
