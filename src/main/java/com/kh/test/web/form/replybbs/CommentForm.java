package com.kh.test.web.form.replybbs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class CommentForm {

  private Long rid;                // 댓글번호 rid NUMBER(10,0)
  private Long bid;                //  게시글번호 bid	NUMBER(10,0)
  private String content;             //  댓글내용 CONTENT	VARCHAR2(300 BYTE)
  private String userName;            //  유저명 USERNAME	VARCHAR2(30 BYTE)
  private LocalDateTime createdAt;    //  작성날짜 CREATED_AT	DATE
  private LocalDateTime updatedAt;
  private Long memberId;                         //  MEMBER_ID	NUMBER
}
