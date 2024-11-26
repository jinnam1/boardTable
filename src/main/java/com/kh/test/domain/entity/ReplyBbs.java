package com.kh.test.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class ReplyBbs {

  private Long rid;                // 댓글번호 rid NUMBER(10,0)
  private Long bid;                //  게시글번호 bid	NUMBER(10,0)
  private String content;             //  댓글내용 CONTENT	VARCHAR2(300 BYTE)
  private String userName;            //  유저명 USERNAME	VARCHAR2(30 BYTE)
  private LocalDateTime createdAt;    //  작성날짜 CREATED_AT	DATE
  private LocalDateTime updatedAt;    //  수정날짜 UPDATED_AT	DATE
  private Long memberId;                         //  MEMBER_ID	NUMBER

}
