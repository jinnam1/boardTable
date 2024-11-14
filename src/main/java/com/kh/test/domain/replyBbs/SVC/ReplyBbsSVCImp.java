package com.kh.test.domain.replyBbs.SVC;


import com.kh.test.domain.entity.ReplyBbs;
import com.kh.test.domain.replyBbs.DAO.ReplyBbsDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ReplyBbsSVCImp implements ReplyBbsSVC {

  private final ReplyBbsDAO replybbsDAO;

  // 레코드 반환

  @Override
  public ReplyBbs findById(Long rid) {
    return replybbsDAO.findById(rid);
  }

  // 댓글 작성
  @Override
  public Long addReply(ReplyBbs replybbs, Long bid) {
    return replybbsDAO.addReply(replybbs ,bid);
  }
  // 댓글 목록
  @Override
  public List<ReplyBbs> findAll(Long bid) {
    return replybbsDAO.findAll(bid);
  }
  // 댓글 삭제
  @Override
  public Long DeleteReply(Long rid) {
    return replybbsDAO.DeleteReply(rid);
  }
  //댓글 수정
  @Override
  public Long UpdateReply(Long rid,ReplyBbs replyBbs) {
    return replybbsDAO.UpdateReply(rid,replyBbs);
  }
}
