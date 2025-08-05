package com.saida.akart_task.mapper;

import com.saida.akart_task.dto.response.TokenResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TokenMapper {

    TokenResponseDto toTokenDto(String token);

}
