package com.kh.test.domain.boardtable.dao;

import com.kh.test.domain.entity.BoardTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor

public class BoardTableDAOImpl implements BoardTableDAO{

  private final NamedParameterJdbcTemplate template;


  // sql에 존재하는 데이터 리스트 추출
  // 초기 화면에서의 목록 조회
  @Override
  public List<BoardTable> findAll() {
    //sql 쿼리 작성
    StringBuffer sql = new StringBuffer();
    sql.append("select BID , TITLE , CONTENT, USERNAME, CREATED_AT, UPDATED_AT ");
    sql.append("    from boardtable ");
    sql.append("    order by bid desc ");

    //sql 쿼리 실행 및 레코드 추출 후 list 컬랙션에 삽입
    List<BoardTable> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(BoardTable.class));
    
    // list 컬랙션 반환
    return list;
  }

  @Override
  public List<BoardTable> findAll(int reqPage, int reqRec) {
  StringBuffer sql = new StringBuffer();
    sql.append("select bid, title, content, username, created_at, updated_at, member_id ");
    sql.append("from boardtable ");
    sql.append("order by bid desc ");
    sql.append("offset ( :reqPage-1 ) * :reqRec rows fetch first :reqRec rows only ");

    Map<String,Integer> param = Map.of("reqPage",reqPage,"reqRec",reqRec);
    List<BoardTable> list = template.query(sql.toString(), param , BeanPropertyRowMapper.newInstance(BoardTable.class));

    return list;
  }

  @Override
  public BoardTable findById(Long bid) {
    //sql 쿼리
    StringBuffer sql = new StringBuffer();
    sql.append("select bid, title, content, username, created_at, updated_at, member_id ");
    sql.append("    from boardtable ");
    sql.append("    where bid = :bid ");

    // 현재 변수로 들어온  bid sql에 존재하는 bid 매칭
    SqlParameterSource param = new MapSqlParameterSource().addValue("bid",bid);
    
    // 매칭된 결과의 게시글의 정보를 추출
    BoardTable findedboardTable = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(BoardTable.class));
    
    // 매칭된 결과의 게시글을 반환
    return findedboardTable;
  }

  @Override
  public Long AddBoardTable(BoardTable boardTable) {
    //sql 쿼리
    StringBuffer sql = new StringBuffer();
    sql.append("insert into boardtable(bid, title, content, username, created_at, updated_at , member_id) ");
    sql.append("VALUES( boardtable_bid_seq.nextval, :title , :content , :userName , sysdate ,sysdate ,:memberId) ");

    // sql column 값과 entity BoardTable 의 속성을 이름으로 매치
    SqlParameterSource param = new BeanPropertySqlParameterSource(boardTable);

    // 현 메소드로 생성되는 레코드의 특정 속성값을 저장하기 위한 객체 생성
    KeyHolder keyHolder = new GeneratedKeyHolder();

    // param 의 매칭 정보를 기반으로 sql 쿼리문 실행 후 row 반환
    // keyholder로 bid 저장
    // rows가 1 이상일시 정상 수행
    int rows = template.update(sql.toString(), param, keyHolder, new String[]{"bid"});

    // rows 정상 작동 확인
    log.info("rows = {}" , rows);

    // keyholder에 저장된 bid 속성을 추츨후 Object 객체로 생성되지만 Number 로 형변환
    Number uId = (Number) keyHolder.getKeys().get("bid");
    long findId = uId.longValue();

    return findId;
  }

  @Override
  public Long DeleteBoardTable(Long bid) {
    // sql 쿼리문 작성
    StringBuffer sql = new StringBuffer();
    sql.append("delete from boardtable where bid = :bid ");

    // :bid 에 bid 변수를 매칭하는 파라미터 소스 생성
    SqlParameterSource param = new MapSqlParameterSource().addValue("bid", bid);

    // sql 실행
    long rows = template.update(sql.toString(), param);

    // 실행이 제대로 되었는지 확인
    log.info("rows = {}" ,rows);


    return rows;
  }

  @Override
  public Long UpdateBoardTable(Long bid, BoardTable boardTable) {
    // sql 쿼리문 작성
    StringBuffer sql = new StringBuffer();
    sql.append("update boardtable ");
    sql.append("    set title = :title , content = :content , username = :userName , updated_at = sysdate ");
    sql.append("    where bid = :bid ");

    // 파라미터소스(매칭정보) 생성
    SqlParameterSource param = new MapSqlParameterSource().addValue("title", boardTable.getTitle())
            .addValue("content", boardTable.getContent())
            .addValue("userName", boardTable.getUserName())
            .addValue("bid", boardTable.getBid());

    // sql 쿼리 실행 및 실행 수 반환
    long rows = template.update(sql.toString(), param);


    return rows;
  }

  @Override
  public int getTotalRecords() {
    String sql = ("select count(*) from boardtable ");


    return template.queryForObject(sql, Map.of(), Integer.class);
  }
}
