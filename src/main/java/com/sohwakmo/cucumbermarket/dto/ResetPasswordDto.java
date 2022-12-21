package com.sohwakmo.cucumbermarket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordDto {
    private String email;
    private String password;
    private String emailKey;
}
