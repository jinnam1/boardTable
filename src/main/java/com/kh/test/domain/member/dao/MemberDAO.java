package com.kh.test.domain.member.dao;

import com.kh.test.domain.entity.Member;

import java.util.Optional;

public interface MemberDAO {

  // 회원 가입
  Member insertMember(Member member);

  // 회원 존재 유무
  boolean isExist(String email);

  // 회원 조회
  Optional<Member> findByMemberId(Long memberId);

  Optional<Member> findByEmail(String email);
}
