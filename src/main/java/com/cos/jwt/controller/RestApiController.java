package com.cos.jwt.controller;

import com.cos.jwt.dto.UserSaveRequestDto;
import com.cos.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final UserRepository userRepository;

    private final  BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("token")
    public String token() {
        return "<h1>token</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        userSaveRequestDto.setPassword(bCryptPasswordEncoder.encode(userSaveRequestDto.getPassword()));
        userRepository.save(userSaveRequestDto.toEntity());
        return "회원가입 완료";
    }

    //user , manager , admin 만 접근가능
    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    // manager , admin 만 가능
    @GetMapping("/api/v1/manager")
    public String manager() {
        return "manager";
    }

    // admin 만 가능
    @GetMapping("/api/v1/admin")
    public String admin() {
        return "admin";
    }
}
