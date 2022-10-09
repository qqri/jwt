package com.cos.jwt.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//스프링 시큐리티에서 저 UsernamePasswordAuthenticationFilter가 있음.
//login 요청을 해서 username , password 전송하면 (post)
//User ~~ 동작한다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    
    // login 요청을 하면 로그인 시도를 위해 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request , HttpServletResponse response)
        throws AuthenticationException {
        System.out.println("JwtAuthenticaionFilter : 로그인 시도중");
        //1 . username , password 받아서
        //2 . 정상인지 로그인 시도하는것.
        //3 . authenticationManager로 로그인 시도하면  PrincipleDetailsService가 호출됨
        // -> loadUserByUserName 함수 실행
        //4 . principledetails 세션에 담고 ( 권한 관리를 위해서 )
        //5 . JWT 토큰 만들어서 응답해준다.
        return super.attemptAuthentication(request, response);
    }
}
