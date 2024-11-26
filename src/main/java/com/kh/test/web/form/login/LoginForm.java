package com.kh.test.web.form.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class LoginForm {
//  @NotBlank(message = "이메일은 필수!")
//  @Email(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$" , message = "이메일 형식에 맞지 않습니다.")
//  @Size(min=7,max=50,message = "이메일 크기는 7~50자 사이 여야합니다.")
  private String email;
//  @NotBlank(message = "비밀번호는 필수!")
  private String passwd;
}

