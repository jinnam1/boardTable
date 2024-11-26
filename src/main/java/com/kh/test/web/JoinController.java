package com.kh.test.web;

import com.kh.test.domain.entity.Member;
import com.kh.test.domain.member.svc.MemberSVC;
import com.kh.test.web.form.join.JoinForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/join")

public class JoinController {

  private final MemberSVC memberSVC;

  
  // 가입 화면이동
  @GetMapping
  public String joinForm(Model model){

    model.addAttribute("joinForm", new JoinForm());

    return "/join/joinForm";
  }

  // 가입처리
  @PostMapping
  public String memberJoin(@Valid JoinForm joinForm,
                           BindingResult bindingResult,
                           Model model) {

    log.info("joinForm = {}", joinForm);

    if (bindingResult.hasErrors()){
      log.info("bindingResult = {}", bindingResult);
      model.addAttribute("joinForm",joinForm);
      return "/join/joinForm";

    }
    Member member = new Member();
    BeanUtils.copyProperties(joinForm, member);

    Member joinMember = memberSVC.insertMember(member);

    return "redirect:/";  // 초기화면
  }
}
