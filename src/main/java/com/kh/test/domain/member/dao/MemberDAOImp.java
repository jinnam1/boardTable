package com.kh.test.domain.member.dao;

import com.kh.test.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor


public class MemberDAOImp implements MemberDAO{

  private final NamedParameterJdbcTemplate template;

  @Override
  public Member insertMember(Member member) {
    // 1) sql 쿼리 준비
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member ( member_id , email , passwd , nickname) ");
    sql.append(" values (member_member_id_seq.nextval , :email , :passwd , :nickname) ");

    // 2) sql 실행
    SqlParameterSource param = new BeanPropertySqlParameterSource(member);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    int rows = template.update(sql.toString(), param, keyHolder, new String[]{"member_id"});

    long memberId = ((Number)keyHolder.getKeys().get("member_id")).longValue();


    return findByMemberId(memberId).get();
  }

  @Override
  public boolean isExist(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("select count(*)" );
    sql.append("from member ");
    sql.append("where email = :email");

    Map<String,String> param = Map.of("email",email);

    // Wrapper 클래스를 기본 타입으로 받을 수 있다.
    Integer cntOfRec = template.queryForObject(sql.toString(), param, Integer.class);


    return (cntOfRec == 1) ?true :false;
  }

  // 회원 조회 by 내부 id
  @Override
  public Optional<Member> findByMemberId(Long memberId) {
    // sql 쿼리작성
    StringBuffer sql = new StringBuffer();

    sql.append(" select member_id , email , passwd , nickname , cdate , udate ");
    sql.append(" FROM member ");
    sql.append("  where member_id = :memberId ");

    Map<String,Long> param = Map.of("memberId",memberId);

//    RowMapper rowMapper = new BeanPropertyRowMapper(Member.class);
    try {
      Member member = template.queryForObject(sql.toString(),
              param,
              BeanPropertyRowMapper.newInstance(Member.class));
      return Optional.of(member);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }



  // 회원 조회 by email
  @Override
  public Optional<Member> findByEmail(String email) {
    // sql 쿼리작성
    StringBuffer sql = new StringBuffer();

    sql.append(" select member_id , email , passwd , nickname , cdate , udate ");
    sql.append(" FROM member ");
    sql.append("  where email = :email ");

    Map<String,String> param = Map.of("email",email);

//    RowMapper rowMapper = new BeanPropertyRowMapper(Member.class);
    try {
      Member member = template.queryForObject(sql.toString(),
              param,
              BeanPropertyRowMapper.newInstance(Member.class));
      return Optional.of(member);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }
}
