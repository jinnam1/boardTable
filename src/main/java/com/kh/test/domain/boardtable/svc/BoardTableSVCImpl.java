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

  @Override
  public List<BoardTable> findAll(int reqPage, int reqRec) {
    return boardTableDAO.findAll(reqPage,reqRec);
  }

  // 게시글 클릭 시 그 게시글 조회
  @Override
  public BoardTable findById(Long bid) {
    return boardTableDAO.findById(bid);
  }

  @Override
  public Long AddBoardTable(BoardTable boardTable) {
    return boardTableDAO.AddBoardTable(boardTable);
  }

  @Override
  public Long DeleteBoardTable(Long bid) {
    return boardTableDAO.DeleteBoardTable(bid);
  }

  @Override
  public Long UpdateBoardTable(Long bid, BoardTable boardTable) {
    return boardTableDAO.UpdateBoardTable(bid, boardTable);
  }

  @Override
  public int getTotalRecords() {
    return boardTableDAO.getTotalRecords();
  }
}
