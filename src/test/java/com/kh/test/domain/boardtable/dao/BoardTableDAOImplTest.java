package com.kh.test.domain.boardtable.dao;

import com.kh.test.domain.entity.BoardTable;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class BoardTableDAOImplTest {

  @Autowired
  BoardTableDAO boardTableDAO;

  @Test
  @DisplayName("게시글 목록")
  void findAll() {
    List<BoardTable> list = boardTableDAO.findAll();

    for (BoardTable boardTable : list) {
      log.info("boardTable : {}", boardTable);
    }
  }

  @Test
  @DisplayName("게시글 상세 보기")
  void findById() {
    Long bid = 1L;
    BoardTable boardTable = boardTableDAO.findById(bid);

    log.info("boardTable = {}", boardTable);

    Assertions.assertThat(boardTable.getBid()).isEqualTo(1);
    Assertions.assertThat(boardTable.getUserName()).isEqualTo("윈터");
    Assertions.assertThat(boardTable.getTitle()).isEqualTo("일기");

  }

  @Test
  @DisplayName("게시글 작성")
  void AddBoardTable(){
    BoardTable boardTable = new BoardTable();
    boardTable.setTitle("수필");
    boardTable.setContent("오늘은 콘서트하는 날이다");
    boardTable.setUserName("지젤");

    Long findId = boardTableDAO.AddBoardTable(boardTable);

    log.info("findId = {}" , findId);

    BoardTable chkBoardTable = boardTableDAO.findById(findId);

    log.info("chkBoardTable = {}", chkBoardTable);

    Assertions.assertThat(chkBoardTable.getTitle()).isEqualTo("수필");
    Assertions.assertThat(chkBoardTable.getContent()).isEqualTo("오늘은 콘서트하는 날이다");
    Assertions.assertThat(chkBoardTable.getUserName()).isEqualTo("지젤");

  }
  
  
  @Test
  @DisplayName("게시글 삭제")
  void DeleteBoardTable(){
    Long bid = 23L;

    Long rows = boardTableDAO.DeleteBoardTable(bid);

    Assertions.assertThat(rows).isEqualTo(1);

  }


  @Test
  @DisplayName("게시글 수정")
  void UpdateBoardTable(){

    Long bid = 50L;
    BoardTable boardTable = new BoardTable();
    boardTable.setBid(bid);
    boardTable.setTitle("수정되었습니다");
    boardTable.setContent("수정되었습니다");
    boardTable.setUserName("수정되었습니다");

    Long rows = boardTableDAO.UpdateBoardTable(bid, boardTable);

    log.info("rows = {}",rows);

    BoardTable chkBT = boardTableDAO.findById(bid);

    log.info("chkBT = {}" ,chkBT);

    Assertions.assertThat(chkBT.getTitle()).isEqualTo("수정되었습니다");
    Assertions.assertThat(chkBT.getContent()).isEqualTo("수정되었습니다");
    Assertions.assertThat(chkBT.getUserName()).isEqualTo("수정되었습니다");

  }


  @Test
  @DisplayName("게시글 더미데이터 복수 작성")
  void AddBoardTables(){

    for (int i = 0; i < 142; i++) {

      BoardTable boardTable = new BoardTable();
      boardTable.setTitle("Dummy title"+i);
      boardTable.setContent("Dummy Content"+i);
      boardTable.setUserName("DummyUser");
      boardTable.setMemberId(21L);

      Long findId = boardTableDAO.AddBoardTable(boardTable);

      log.info("findId = {}" , findId);
    }


  }



  @Test
  @DisplayName("게시글 요철 목록")
  void findAllreq() {

    int reqPage = 2; // 요청페이지
    int reqRec = 10; // 한ㅍ페이지당 보여줄 레코드 수
    List<BoardTable> list = boardTableDAO.findAll(reqPage,reqRec);

    for (BoardTable boardTable : list) {
      log.info("boardTable : {}", boardTable);
    }
  }

  @Test
  @DisplayName("총 레코드 건수")
  void getTotalRecord(){

    int totalRecords = boardTableDAO.getTotalRecords();

    log.info("totalRecords = {}",totalRecords);
  }

}