package com.estsoft13.matdori.dto;

import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.util.Role;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;

    public UserDto(User user) {
        this.username = user.getEnteredUsername();
        this.role = user.getRole();
    }

    @Builder
    public UserDto(Role role, Long id, String username) {
       this.role= role;
       this.id = id;
       this.username = username;
    }

}
