drop table boardtable;
drop SEQUENCE boardtable_bid_seq;
create SEQUENCE boardtable_bid_seq;


delete from boardtable;

-- 테이블 생성
create table boardtable(
    bid    number(10),
    title       varchar2(60) not null,
    content clob not null,
    username varchar2(30) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP not null,
    member_id number
);
-- 기본키 생성
alter table boardtable add Constraint boardtable_bid_pk primary key(bid);


-- 외래키
alter table boardtable add constraint boardtable_userid_fk
    foreign key(member_id) references member(member_id);

alter table boardtable add constraint boardtable_username_fk
    foreign key(username) references member(nickname);


-- 더미 데이터 삽입
insert into boardtable(bid, title, content, username, created_at, updated_at, member_id)
VALUES(boardtable_bid_seq.nextval, '내가 하고싶은 말' , '나는 밥을 먹었을까?' , '카리나', sysdate , sysdate, 1 );
insert into boardtable(bid, title, content, username, created_at, updated_at, member_id)
VALUES(boardtable_bid_seq.nextval, '오늘의 일기' ,'나는 밥을 먹었다', '윈터' , TO_DATE('241027','yymmdd'), TO_DATE('241028','yymmdd') ,2);
insert into boardtable(bid, title, content, username, created_at, updated_at, member_id)
VALUES(boardtable_bid_seq.nextval, '지금 이순간에 나는' ,'나는 밥을 먹었다네' , '닝닝' ,TO_DATE('241028','yymmdd') ,TO_DATE('241028','yymmdd') ,3);
insert into boardtable(bid, title, content, username, created_at, updated_at, member_id)
VALUES(boardtable_bid_seq.nextval, '졸리다' , '나는 밥을 먹었을까?' , '지젤', sysdate , sysdate, 4 );


-- bid 값으로 레코드 수정
update boardtable
    set title = 'title' , content = 'content' , username = 'username' , updated_at = sysdate
    where bid = 2;

-- bid 값으로 레코드 삭제
delete from boardtable where bid = 22;

-- 단건 조회
select bid, title, content, username, created_at, updated_at
    from boardtable
    where bid = 2;

-- 전체 조회
select bid, title, content, username, created_at, updated_at, member_id
    from boardtable
    order by bid desc
    offset ( :reqPage-1 ) * :reqRec rows fetch first :reqRec rows only;



select count(*) from boardtable;

rollback;
commit;