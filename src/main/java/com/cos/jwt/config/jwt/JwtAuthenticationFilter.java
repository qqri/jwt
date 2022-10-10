package com.cos.jwt.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private final AuthenticationManager authenticationManager;

    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 로그인 중");
        //1. username, password 받아서
        /*
        x-www-form으로 요청하는 경우
        try {
            BufferedReader br = request.getReader();

            String input = null;
            while( (input = br.readLine()) != null ) System.out.println(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        try{
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream() , User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername() , user.getPassword());

            //PrincipalDetailsService의 loadUserByUsername 함수가 실행된다.
            //authentication에 내 로그인 정보가 담김.
            //session에 저장됨.
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);
            //session 영역에저장된 principal이 출력된다 -> 로그인이 된다는 의미.
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            //System.out.println(principalDetails.getUser().getUsername());

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("=========================");

        return null;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되면 successfulAuthenticaion함수 실행
    // JWT 토큰 만들어서 req 요청한 사용자에게 JWT 토큰을 response 해주면 된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨. 인증이 완료되었다는 의미");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        //RSA방식 아니고 Hash암호방식
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+864000000))
                .withClaim("id", principalDetails.getUser().getId()) //비공개 클레임값
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("cos"));



        System.out.println("jwt 토큰 : "+ "Bearer "+ jwtToken);

        response.addHeader("Authorization", "Bearer "+jwtToken);

        System.out.println(response.getHeader("Authorization"));
        //super.successfulAuthentication(request, response, chain, authResult);
    }

    

}