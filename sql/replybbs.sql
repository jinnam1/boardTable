drop table replybbs;

drop SEQUENCE replybbs_rid_seq;
create SEQUENCE replybbs_rid_seq;

create table replybbs(
    rid    number(10),
    bid    number(10),
    content clob not null,
    username varchar2(30) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP not null,
    member_id number
);
-- 기본키

alter table replybbs add Constraint replybbs_rid_pk primary key (rid);

-- 외래키
alter table replybbs add constraint replybbs_bid_fk
    foreign key(bid) references boardtable(bid);

alter table replybbs add constraint replybbs_userid_fk
    foreign key(member_id) references member(member_id);

alter table replybbs add constraint replybbs_username_fk
    foreign key(username) references member(nickname);


-- 전체 조회
select rid, bid, content, username, created_at , updated_at, member_id
    from replybbs
    order by rid desc;

-- 단건 조회
select rid, bid, content, username, created_at , updated_at
    from replybbs
    where bid = 1
    offset ( :reqPage-1 ) * :reqRec rows fetch first :reqRec rows only;

-- 더미데이터 생성
insert into replybbs(rid, bid, content, username, created_at , updated_at , member_id)
    values(replybbs_rid_seq.nextval , 1 , '댓글 테스트내용1' , '카리나' , sysdate ,sysdate, 1);
insert into replybbs(rid, bid, content, username, created_at , updated_at, member_id)
    values(replybbs_rid_seq.nextval , 2 , '댓글 테스트내용2' , '윈터' , sysdate ,sysdate, 2);
insert into replybbs(rid, bid, content, username, created_at , updated_at, member_id)
    values(replybbs_rid_seq.nextval , 3 , '댓글 테스트내용3' , '닝닝' , sysdate ,sysdate, 3);


update replybbs
    set content = 'content' , username = 'username' , updated_at = sysdate
        where rid = 21;


delete from replybbs where rid = 42;


select bid, rid, content, username, created_at, updated_at
    from replybbs
    where rid = 21;


commit;