drop table member;


drop SEQUENCE member_member_id_seq;
create SEQUENCE member_member_id_seq;

create table member (
    member_id   number,         --내부 관리 아이디
    email       varchar2(50),   --로긴 아이디
    passwd      varchar2(12),   --로긴 비밀번호
    nickname    varchar2(30),   --별칭
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);

-- 기본키 생성
alter table member add Constraint member_member_id_pk primary key (member_id);

--  더미데이터 생성
insert into member ( member_id , email , passwd , nickname)
    values (member_member_id_seq.nextval , 'karina@kh.com' , 1234 , '카리나');
insert into member ( member_id , email , passwd , nickname)
    values (member_member_id_seq.nextval , 'winter@kh.com' , 1234 , '윈터');
insert into member ( member_id , email , passwd , nickname)
    values (member_member_id_seq.nextval , 'ning2@kh.com' , 1234 , '닝닝');
insert into member ( member_id , email , passwd , nickname)
    values (member_member_id_seq.nextval , 'zZel@kh.com' , 1234 , '지젤');
insert into member ( member_id , email , passwd , nickname)
    values (member_member_id_seq.nextval , 'test@kh.com' , 1234 , 'DummyUser');

-- 전체조회
select  member_id , email , passwd , nickname , cdate , udate
    from member;

-- 이메일 단건 조회
select member_id , email , passwd , nickname , cdate , udate
    from member
    where email = 'karina@kh.com';

-- member_id로 단건 조회
select member_id , email , passwd , nickname , cdate , udate
    from member
    where member_id = 1;

-- 단건 삭제
delete from member where member_id = memberId;


commit;
