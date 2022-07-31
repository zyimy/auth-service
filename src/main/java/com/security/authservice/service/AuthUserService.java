package com.security.authservice.service;

import com.security.authservice.dto.AuthUserDto;
import com.security.authservice.dto.TokenDto;
import com.security.authservice.entity.AuthUser;
import com.security.authservice.repository.AuthUserRepository;
import com.security.authservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthUser save(AuthUserDto authUserDto){
        Optional<AuthUser>user = authUserRepository.findByUserName(authUserDto.getUserName());

        if (user.isPresent())
            return null;
        String password  = passwordEncoder.encode(authUserDto.getPassword());
          AuthUser authUser = AuthUser.builder()
                .userName(authUserDto.getUserName())
                .password(authUserDto.getPassword())
                .build();
          return authUserRepository.save(authUser);

    }

    public TokenDto login(AuthUserDto authUserDto){
        Optional<AuthUser>user = authUserRepository.findByUserName(authUserDto.getUserName());

        if (!user.isPresent())
            return null;
        if (passwordEncoder.matches(authUserDto.getPassword(),user.get().getPassword()))
            return new TokenDto(jwtProvider.createToken(user.get()));
        return null;

    }

    public TokenDto validate(String token){
        if (!jwtProvider.validate(token))
            return null;
        String userName = jwtProvider.getUserNameFromToken(token);
        if (!authUserRepository.findByUserName(userName).isPresent())
            return null;
        return new TokenDto(token);
    }
}
