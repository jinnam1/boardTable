package com.kh.test.web.form.boardtable;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class UpdateForm {
  @NotNull(message = "예상치 못한 오류로 게시판고유번호가 입력되지않았습니다.")
  @Min(value = 1,message = "문제가 생겨 수동 입력시 1이상의 숫자를 입력해주세요")
  @Positive(message = "문제가 생겨 수동입력시 0이상의 숫자만 넣어주세요")
  private Long bid;                //  유저ID bid	NUMBER(10,0)

  @NotBlank(message = "제목은 필수 입력 사항입니다.")
  @Size(max = 20, message = "제목은 한글로 20자를 넘어가면 안됩니다.")
  private String title;               //  제목 TITLE	VARCHAR2(60 BYTE)

  @NotBlank(message = "내용은 필수 입력 사항입니다.")
  private String content;             //  내용 CONTENT	VARCHAR2(300 BYTE)

  @NotBlank(message = "작성자는 필수 입력 사항입니다.")
  @Size(max = 10, message = "작성자는 한글로 20자를 넘어가면 안됩니다.")
  private String userName;            //  유저명 USERNAME	VARCHAR2(30 BYTE)

  @NotNull(message = "예상치 못한 오류로 작성날짜가 입력되지않았습니다.")
  @PastOrPresent(message = "작성날짜는 현재 혹은 과거의 값만 들어올수 있습니다.")
  private LocalDateTime createdAt;    //  작성날짜 CREATED_AT	DATE

  @NotNull(message = "예상치 못한 오류로 수정날짜가 입력되지않았습니다.")
  private LocalDateTime updatedAt;     //  수정날짜 UPDATED_AT	DATE
}
