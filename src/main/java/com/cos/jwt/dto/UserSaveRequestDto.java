package com.cos.jwt.dto;

import com.cos.jwt.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String username;
    @Setter
    private String password;

    @Builder
    public UserSaveRequestDto(String username , String password) {
        this.username = username;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .roles("USER_ROLE")
                .build();
    }
}
