package com.kh.test.web;


import com.kh.test.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor

public class AppConfig implements WebMvcConfigurer {

  private final LoginCheckInterceptor loginCheckInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 인증 체크
    registry.addInterceptor(loginCheckInterceptor)
            .order(1)     // 인터셉터의 실행 순서 지정
            .addPathPatterns("/**")  // 루트로 부터 하위경로 모두 인터셉터에 포함
            .excludePathPatterns(    // 제외패턴
                    "/",             // 초기화면
                    "/login",
                    "/logout",
                    "/members/join",
                    "/css/**",
                    "/js/**",
                    "/img/**",
                    "/api/**",
                    "/test/**",
                    "/error/**",
                    "/join/**"
            );
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
            .allowedOrigins("http://192.168.0.47:5500")
            .allowedMethods("*")
            .maxAge(3000);
  }
}
