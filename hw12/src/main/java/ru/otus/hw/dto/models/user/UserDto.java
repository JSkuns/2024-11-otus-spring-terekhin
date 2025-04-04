package ru.otus.hw.dto.models.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    UserAccountDetails toUserAccount(UserAccount user);

}
