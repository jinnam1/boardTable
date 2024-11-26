package com.kh.test.web;

import com.kh.test.domain.entity.Member;
import com.kh.test.domain.member.svc.MemberSVC;
import com.kh.test.web.form.login.LoginForm;
import com.kh.test.web.form.login.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class HomeController {

  private final MemberSVC memberSVC;

  // 홈페이지 로딩
  @GetMapping
  public String home(Model model){
    model.addAttribute("loginForm",new LoginForm());

    return "/index";
  }

  // 홈에서 로그인 처리
  @PostMapping("/login")
  public String login(@Valid LoginForm loginForm,
                      BindingResult bindingResult,
                      @RequestParam(name = "redirectUrl",defaultValue = "/") String redirectUrl,
                      HttpServletRequest request){

    log.info("loginForm = {}",loginForm);

    // 회원 여부 체크 어노테이션 단계에서 마치고 
    // 실질적인 비즈니스 오류 단계
    // 에러기 있을 경우
    // 1) 이메일의 존재 여부
    if(!memberSVC.isExist(loginForm.getEmail())){
      bindingResult.rejectValue("email","invalidMember");
      return "/index";
    }

    // 2) 비밀번호 일치 여부 체크
    Optional<Member> optionalMember = memberSVC.findByEmail(loginForm.getEmail());
    Member inputMember = optionalMember.get();
    log.info("inputMember = {}", inputMember);

    if(!loginForm.getPasswd().equals(inputMember.getPasswd())){
      bindingResult.rejectValue("passwd","invalidMember");
      return "/index";
    }

    // 3) 세션 만들기

    HttpSession session = request.getSession(true);

    // 세션에 저장할 회원정보 생성
    LoginMember loginMember = new LoginMember(
            inputMember.getMemberId(),
            inputMember.getEmail(),
            inputMember.getNickname());

    session.setAttribute("loginMember", loginMember);


    return "redirect:"+redirectUrl;  // 초기화면으로 이동
  }

  // 홈에서 로그 아웃 처리
  @GetMapping("/logout")
  public String logout(HttpServletRequest request){
    // 세션 정보 가져오기
    HttpSession session = request.getSession(false);
    // 세션 제거
    session.invalidate();

    return "redirect:/";
  }
}
