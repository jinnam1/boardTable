package com.kh.test.web.form.boardtable;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter

public class FindForm {
  private Long bid;                //  유저ID bid	NUMBER(10,0)
  private String title;               //  제목 TITLE	VARCHAR2(60 BYTE)
  private String content;             //  내용 CONTENT	VARCHAR2(300 BYTE)
  private String userName;            //  유저명 USERNAME	VARCHAR2(30 BYTE)
  private LocalDateTime createdAt;    //  작성날짜 CREATED_AT	DATE
  private LocalDateTime updatedAt;     //  수정날짜 UPDATED_AT	DATE
  private Long memberId;                         //  MEMBER_ID	NUMBER

}
