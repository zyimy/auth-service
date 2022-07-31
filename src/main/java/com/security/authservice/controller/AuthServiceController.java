package com.security.authservice.controller;

import com.security.authservice.dto.AuthUserDto;
import com.security.authservice.dto.TokenDto;
import com.security.authservice.entity.AuthUser;
import com.security.authservice.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthServiceController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> validate(@RequestBody AuthUserDto authUserDto){
        TokenDto tokenDto = authUserService.login(authUserDto);
        if (tokenDto == null){
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.ok(tokenDto);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token){
        TokenDto tokenDto = authUserService.validate(token);
        if (tokenDto== null){
            return ResponseEntity.badRequest().build();
        }else {
          return   ResponseEntity.ok(tokenDto);
        }
    }

    public ResponseEntity<AuthUser>create(@RequestBody AuthUserDto dto){
        AuthUser authUser = authUserService.save(dto);
        if (authUser == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(authUser);
        }
    }


}
