drop table replybbs;

// 시퀀스 생성
drop SEQUENCE replybbs_rid_seq;
create SEQUENCE replybbs_rid_seq;

// 댓글 테이블 생성
create table replybbs(
    rid    number(10),
    bid    number(10),
    content clob not null,
    username varchar2(30) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP not null,
    primary key(rid),
    FOREIGN key(bid) REFERENCES boardtable(bid)
);

// 전체 조회
select rid, bid, content, username, created_at , updated_at
    from replybbs
    order by rid asc;

// 레코드 삽입(댓글 작성)
insert into replybbs(rid, bid, content, username, created_at , updated_at)
    values(replybbs_rid_seq.nextval , 1 , '댓글 테스트내용1' , '테스트작성자1' , sysdate ,sysdate);
insert into replybbs(rid, bid, content, username, created_at , updated_at)
    values(replybbs_rid_seq.nextval , 2 , '댓글 테스트내용2' , '테스트작성자2' , sysdate ,sysdate);
insert into replybbs(rid, bid, content, username, created_at , updated_at)
    values(replybbs_rid_seq.nextval , 3 , '댓글 테스트내용3' , '테스트작성자3' , sysdate ,sysdate);

// 수정
update replybbs
    set content = 'content' , username = 'username' , updated_at = sysdate
    where rid = 21;

// 삭제
delete from replybbs where rid = 22;


// 단건 조회
select bid, rid, content, username, created_at, updated_at
    from replybbs
    where rid = 21;


commit;