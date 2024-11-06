package com.kh.test.domain.boardtable.svc;

import com.kh.test.domain.boardtable.dao.BoardTableDAO;
import com.kh.test.domain.entity.BoardTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class BoardTableSVCImpl implements BoardTableSVC{

  private final BoardTableDAO boardTableDAO;


  // 초기 화면에서의 목록 조회
  @Override
  public List<BoardTable> findAll() {
    return boardTableDAO.findAll();
  }

  // 게시글 클릭 시 그 게시글 조회
  @Override
  public BoardTable findById(Long userId) {
    return boardTableDAO.findById(userId);
  }

  @Override
  public Long AddBoardTable(BoardTable boardTable) {
    return boardTableDAO.AddBoardTable(boardTable);
  }

  @Override
  public Long DeleteBoardTable(Long userId) {
    return boardTableDAO.DeleteBoardTable(userId);
  }

  @Override
  public Long UpdateBoardTable(Long userId, BoardTable boardTable) {
    return boardTableDAO.UpdateBoardTable(userId, boardTable);
  }
}
