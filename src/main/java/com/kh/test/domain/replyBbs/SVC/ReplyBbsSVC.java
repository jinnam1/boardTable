package com.kh.test.domain.replyBbs.SVC;


import com.kh.test.domain.entity.ReplyBbs;

import java.util.List;

public interface ReplyBbsSVC {


  // 레코드 반환
  public ReplyBbs findById(Long rid);

  // 댓글 작성
  public Long addReply(ReplyBbs replybbs, Long bid);

  // 댓글 목록
  public List<ReplyBbs> findAll(Long bid);

  // 댓글 삭제
  public Long DeleteReply(Long rid);
  
  // 댓글 수정
  public Long UpdateReply(Long rid,ReplyBbs replyBbs);
}
