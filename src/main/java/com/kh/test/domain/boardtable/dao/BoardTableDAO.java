package com.kh.test.domain.boardtable.dao;

import com.kh.test.domain.entity.BoardTable;

import java.util.List;

public interface BoardTableDAO {
  
  // 초기 화면에서의 목록 조회
  public List<BoardTable> findAll();

  // 게시글 조회
  public BoardTable findById(Long userId);

  // 게시글 작성
  public Long AddBoardTable(BoardTable boardTable);

  // 게시글 삭제
  public Long DeleteBoardTable(Long userId);

  // 게시글 수정
  public Long UpdateBoardTable(Long userId, BoardTable boardTable);
}
