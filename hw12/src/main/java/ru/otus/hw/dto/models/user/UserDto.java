package ru.otus.hw.dto.models.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;

    private String username;

    private String password;

//    UserAccountDetails toUserAccount(UserAccount user);

}
