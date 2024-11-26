package com.kh.test.web.req;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class ReqSave {

  @NotNull
  @Positive
  @Min(value=1L)
  @Max(value=9_999_999_999L)
  private Long bid;                //  게시글번호 bid	NUMBER(10,0)

  @NotBlank
  private String content;             //  댓글내용 CONTENT	VARCHAR2(300 BYTE)

  @NotBlank
  @Size(min = 1,max = 10)
  private String userName;            //  유저명 USERNAME	VARCHAR2(30 BYTE)

  private Long memberId;                         //  MEMBER_ID	NUMBER
}
