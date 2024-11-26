package com.kh.test.domain.member.svc;

import com.kh.test.domain.entity.Member;
import com.kh.test.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class MemberSVCImp implements MemberSVC{

  private final MemberDAO memberDAO;

  @Override
  public Member insertMember(Member member) {
    return memberDAO.insertMember(member);
  }

  @Override
  public boolean isExist(String email) {
    return memberDAO.isExist(email);
  }

  @Override
  public Optional<Member> findByMemberId(Long memberId) {
    return memberDAO.findByMemberId(memberId);
  }

  @Override
  public Optional<Member> findByEmail(String email) {
    return memberDAO.findByEmail(email);
  }
}
