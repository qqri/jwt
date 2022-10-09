package com.cos.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
/*
*   토큰 : cos 이걸 만들어 줘야한다. id, pw 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어 주고 그걸 응답한다.
*   요청 할때마다 header 에 Authorizaion 에 value값으로 토큰을 가지고 온다.
*   그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증하면된다. (RSA , HS256)
*/
        req.setCharacterEncoding("UTF-8");
        if(req.getMethod().equals("POST")) {
            System.out.println("POST 요청 됨");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);
            if(headerAuth.equals("cos")) {
                chain.doFilter(req, res);
            } else{
                System.out.println("필터1");
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }

        //System.out.println("필터1");
        //chain.doFilter(req, res);//끝나지말고 계속 프로세스 진행하려면 필터 넘겨야됨

    }

}
