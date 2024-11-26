package com.kh.test.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;

@Slf4j
@Component  // springBoot 구동시 객체가 자동으로 생성 되어 빈 컨테이너에서 관리

public class LoginCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    // 리다이텍트 Url
    String redirectUrl = null;

    // 요청 URI ex) GET http://localhost:9080/products?a=1&b=2
    String requestURI = request.getRequestURI();   //  /products
//    log.info("requestURI = {}", requestURI);      // /products
//    log.info("queryString= "+request.getQueryString());  // a=1&b=2
//    log.info("queryString= "+request.getRequestURL());  // 전체 url
//    log.info("queryString= "+request.getMethod());  // GET,POST


    // 요청 url에 쿼리스트링이 없는 경우
    if(request.getQueryString()==null){
      redirectUrl = requestURI;
    }else{
      // 요청 url에 쿼리 스트링이 있는 경우
      String queryString = URLEncoder.encode(request.getQueryString(), "UTF-8");   //  a=1&b=2
      StringBuffer str = new StringBuffer();
      redirectUrl = str.append(requestURI).append("?").append(queryString).toString();   // /product?a=1&b=2
    }


    // 세션 조회
    HttpSession session = request.getSession(false);
    
    // 세션이 없거나, loginMember 정보가 없으면 로그인 페이지로 리다이렉트
    if(session == null || session.getAttribute("loginMember") == null) {
      log.info("미인증 요청");
      response.sendRedirect("/?redirectUrl=" + redirectUrl);  // 302 GET http://localhost:9080/ + ?redirectUrl=...
    }
    return true;
  }
}
