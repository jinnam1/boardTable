package com.kh.test.domain.replyBbs.DAO;

import com.kh.test.domain.entity.ReplyBbs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor

public class ReplyBbsDAOImp implements ReplyBbsDAO {

  private final NamedParameterJdbcTemplate template;
  // 레코드 반환


  @Override
  public ReplyBbs findById(Long rid) {
    //sql 쿼리
    StringBuffer sql = new StringBuffer();
    sql.append("select rid, bid, content, username, created_at, updated_at , member_id ");
    sql.append("    from replybbs ");
    sql.append("    where rid = :rid ");

    // 현재 들어온 rid를 쿼리문의 rid와 매칭하는 param 생성
    SqlParameterSource param = new MapSqlParameterSource().addValue("rid",rid);
    
    // 매칭 후 댓글 정보 반환
    ReplyBbs findedreplyBbs = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(ReplyBbs.class));

    return findedreplyBbs;
  }

  // 댓글 작성
  @Override
  public Long addReply(ReplyBbs replybbs, Long bid) {
    StringBuffer sql = new StringBuffer();
    
    // 댓글을 저장할 sql 쿼리문 작성
    sql.append("insert into replybbs(rid, bid, content, username, created_at , updated_at  , member_id)  ");
    sql.append("    values(replybbs_rid_seq.nextval , :bid , :content , :userName , sysdate ,sysdate  , :memberId)  ");
    
    // enity replybbs와 위의 쿼리문와의 colunm 매칭 / bid 값은 외부에서 받아옴
    SqlParameterSource param = new MapSqlParameterSource().addValue("bid", bid)
            .addValue("content", replybbs.getContent())
            .addValue("userName", replybbs.getUserName())
            .addValue("memberId", replybbs.getMemberId());

    // 댓글의 고유 아이디 값을 받은 키홀더 생성
    KeyHolder keyHolder = new GeneratedKeyHolder();
    
    // sql 쿼리문 실행 및 생성시 rid값을 키홀더에 저장
    long rows = template.update(sql.toString(), param, keyHolder, new String[]{"rid"});
    
    // 생성된 rid값을 created_rid 변수에 저장
    Number rid = (Number)keyHolder.getKeys().get("rid");
    Long created_rid = rid.longValue();

    return created_rid;
  }

  // 댓글 목록
  @Override
  public List<ReplyBbs> findAll(Long bid) {
    
    // sql 쿼리문 작성
    StringBuffer sql = new StringBuffer();
    sql.append("select rid, bid, content, username, created_at , updated_at, member_id  ");
    sql.append("    from replybbs ");
    sql.append(" where bid = :bid  ");
    sql.append("    order by rid asc ");

    // 게시물의 번호를 매칭해주는 param 생성
    SqlParameterSource param = new MapSqlParameterSource().addValue("bid", bid);
    
    //sql 쿼리 실행 및 레코드 추출 후 list 컬랙션에 삽입
    List<ReplyBbs> list = template.query(sql.toString(), param ,BeanPropertyRowMapper.newInstance(ReplyBbs.class));

    return list;
  }

  @Override
  public List<ReplyBbs> findAll(Long bid, int reqPage, int reqRec) {

    // sql 쿼리문 작성
    StringBuffer sql = new StringBuffer();
    sql.append("select rid, bid, content, username, created_at , updated_at, member_id  ");
    sql.append("    from replybbs ");
    sql.append(" where bid = :bid  ");
    sql.append("    order by rid asc ");
    sql.append("    offset ( :reqPage-1 ) * :reqRec rows fetch first :reqRec rows only ");

    // 게시물의 번호를 매칭해주는 param 생성
    SqlParameterSource param = new MapSqlParameterSource().addValue("bid", bid)
            .addValue("reqPage", reqPage)
            .addValue("reqRec",reqRec );

    //sql 쿼리 실행 및 레코드 추출 후 list 컬랙션에 삽입
    List<ReplyBbs> list = template.query(sql.toString(), param ,BeanPropertyRowMapper.newInstance(ReplyBbs.class));

    return list;
  }

  // 댓글 삭제
  @Override
  public Long DeleteReply(Long rid) {

    log.info("vrid = {}" ,rid);

    //sql 쿼리문 작성
    StringBuffer sql = new StringBuffer();
    sql.append("delete from replybbs ");
    sql.append("where rid = :rid ");

    // 매칭정보 파라미터 생성
    SqlParameterSource param = new MapSqlParameterSource().addValue("rid", rid);

    long rows = template.update(sql.toString(), param);

    log.info("rows = {}", rows);

    return rows;
  }

  
  // 댓글 수정
  @Override
  public Long UpdateReply(Long rid ,ReplyBbs replyBbs) {
    //sql 쿼리문 작성
    StringBuffer sql = new StringBuffer();
    sql.append("update replybbs ");
    sql.append("set content = :content , username = :userName , updated_at = sysdate, member_id = :memberId");
    sql.append("        where rid = :rid ");

    // 파라미터 매칭
    SqlParameterSource param = new MapSqlParameterSource().addValue("content",replyBbs.getContent())
            .addValue("userName", replyBbs.getUserName())
            .addValue("rid", rid)
            .addValue("memberId",replyBbs.getMemberId());

    long rows = template.update(sql.toString(), param);


    return rows;
  }

  @Override
  public int getTotalRecords(Long bid) {

    String sql = "select count(*) from replyBbs where bid = :bid ";

    SqlParameterSource param = new MapSqlParameterSource().addValue("bid", bid);

    return template.queryForObject(sql, param, Integer.class);
  }
}
