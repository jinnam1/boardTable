package com.kh.test.web.form.boardtable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SaveForm {
  @NotBlank(message = "제목은 필수로 입력해야 합니다")
  @Size(max = 20, message = "제목은 한글로 20자를 넘어가면 안됩니다.")
  private String title;               //  제목 TITLE	VARCHAR2(60 BYTE)

  @NotBlank(message = "내용은 필수로 입력해야 합니다")
  private String content;             //  내용 CONTENT	VARCHAR2(300 BYTE)

  @NotBlank(message = "작성자는 필수로 입력해야 합니다")
  @Size(max = 10, message = "작성자는 한글로 20자를 넘어가면 안됩니다.")
  private String userName;            //  유저명 USERNAME	VARCHAR2(30 BYTE)

  private Long memberId;                         //  MEMBER_ID	NUMBER
}
