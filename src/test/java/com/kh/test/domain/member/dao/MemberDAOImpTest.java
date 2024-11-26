package com.kh.test.domain.member.dao;

import com.kh.test.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest

class MemberDAOImpTest {

  @Autowired
  MemberDAO memberDAO;

  @Test
  void insertMember() {

    Member member = new Member();
    member.setEmail("test3@kh.com");
    member.setPasswd("1234");
    member.setNickname("별칭3");

    Member insertedMember = memberDAO.insertMember(member);
    log.info("insertedMember = {}", insertedMember);
  }


  @Test
  @DisplayName("회원존재유무By회원이메일")
  void isExist() {

    boolean exist = memberDAO.isExist("karina@kh.com");
    Assertions.assertThat(exist).isEqualTo(true);

    exist = memberDAO.isExist("test11@google.com");
    Assertions.assertThat(exist).isEqualTo(false);
  }

  @Test
  void findByMemberId() {
    Optional<Member> optionalMember = memberDAO.findByMemberId(1L);
    if(optionalMember.isPresent()){
      Member member = optionalMember.get();
      log.info("member = {}", member);
    }
    optionalMember = memberDAO.findByMemberId(0L);
    if(!optionalMember.isPresent()){
      log.info("회원 없음!!");
    }
  }

  @Test
  void findByEmail() {
    Optional<Member> optionalMember = memberDAO.findByEmail("karina@kh.com");
    if(optionalMember.isPresent()){
      Member member = optionalMember.get();
      log.info("member = {}", member);
    }
    optionalMember = memberDAO.findByEmail("test11@google.com");
    if(!optionalMember.isPresent()){
      log.info("회원 없음!!");
    }
  }
}