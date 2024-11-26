package com.kh.test.domain.boardtable.dao;

import com.kh.test.domain.entity.ReplyBbs;
import com.kh.test.domain.replyBbs.DAO.ReplyBbsDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j

class replyBbsDAOImpTest {
  @Autowired
  ReplyBbsDAO replyBbsDAO;


  @Test
  @DisplayName("댓글확인")
  void findById() {
    Long rid = 1L;

    ReplyBbs byId = replyBbsDAO.findById(rid);

    log.info("byId = {}",byId);
  }

  @Test
  @DisplayName("댓글작성")
  void addReply() {
    ReplyBbs replybbs = new ReplyBbs();
    replybbs.setContent("작성되는내용");
    replybbs.setUserName("작성되는사람");
    Long bid = 3L;


    Long rid = replyBbsDAO.addReply(replybbs, bid);

    log.info("rid = {}",rid);

    ReplyBbs finded = replyBbsDAO.findById(rid);

    log.info("finded = {}",finded);
  }


  @Test
  @DisplayName("더미 댓글 작성")
  void addReplyDummy() {

    for (int i = 0; i < 125; i++) {

      ReplyBbs replybbs = new ReplyBbs();
      replybbs.setContent("Dummy Content"+i);
      replybbs.setUserName("DummyUser");
      replybbs.setMemberId(21L);
      Long bid = 162L;


      Long rid = replyBbsDAO.addReply(replybbs, bid);

      log.info("rid = {}",rid);
    }

  }

  @Test
  @DisplayName("댓글목록")
  void findAll(){
    Long bid = 3L;
    List<ReplyBbs> list = replyBbsDAO.findAll(bid);

    for (ReplyBbs replyBbs : list) {
      log.info("replyBbs = {}", replyBbs);
    }
  }

  @Test
  @DisplayName("댓글삭제")
  void DeleteReply(){
    Long rid = 81L;
    Long rows = replyBbsDAO.DeleteReply(rid);

    log.info("rows= {}", rows);
  }

  @Test
  @DisplayName("댓글 수정")
  void UpdateReply(){
    ReplyBbs replyBbs = new ReplyBbs();
    replyBbs.setUserName("수정자입니다.");
    replyBbs.setContent("수정된내용입니다.");

    Long rid = 41L;

    Long rows = replyBbsDAO.UpdateReply(rid, replyBbs);

    log.info("rows = {}", rows);

    ReplyBbs UpdatedReply = replyBbsDAO.findById(rid);

    log.info("UpdatedReply = {}", UpdatedReply);

  }
}