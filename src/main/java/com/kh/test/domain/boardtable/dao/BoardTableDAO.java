package com.kh.test.domain.boardtable.dao;

import com.kh.test.domain.entity.BoardTable;

import java.util.List;

public interface BoardTableDAO {
  
  // 초기 화면에서의 목록 조회
  public List<BoardTable> findAll();

  public List<BoardTable> findAll(int reqPage, int reqRec);

  // 게시글 조회
  public BoardTable findById(Long bid);

  // 게시글 작성
  public Long AddBoardTable(BoardTable boardTable);

  // 게시글 삭제
  public Long DeleteBoardTable(Long bid);

  // 게시글 수정
  public Long UpdateBoardTable(Long bid, BoardTable boardTable);

  // 총 레코드 건수 반환
  public int getTotalRecords();
}
