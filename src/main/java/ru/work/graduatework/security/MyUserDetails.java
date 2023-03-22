package ru.work.graduatework.security;

import lombok.Getter;
import ru.work.graduatework.Entity.Users;

import java.util.List;

@Getter
public class MyUserDetails extends org.springframework.security.core.userdetails.User{

    private final Long id;

    public MyUserDetails (Users user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.id=user.getId();
    }

    @Override
    public void eraseCredentials() {

    }

}
