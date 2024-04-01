package com.estsoft13.matdori.dto;

import com.estsoft13.matdori.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDto {
    private String username;
    private String email;
    private String password;

    public UserDto(User user) {
        this.username = user.getEnteredUsername();
    }
}
