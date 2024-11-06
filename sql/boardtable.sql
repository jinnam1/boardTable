// 테이블 삭제
drop table boardtable;


// 시퀀스 삭제
drop SEQUENCE boardtable_userid_seq;


// 시퀀스 생성
create SEQUENCE boardtable_userid_seq;


// 테이블 생성
create table boardtable(
    userid    number(10),
    title       varchar2(60) not null,
    content clob not null,
    username varchar2(30) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP not null,
    primary key(userid)
);


// 레코드 생성
insert into boardtable(userid, title, content, username, created_at, updated_at) 
    VALUES(boardtable_userid_seq.nextval, '오늘의 일기' ,'나는 밥을 먹었다', '윈터' , TO_DATE('241027','yymmdd'), TO_DATE('241028','yymmdd') );
    
insert into boardtable(userid, title, content, username, created_at, updated_at) 
    VALUES(boardtable_userid_seq.nextval, '지금 이순간에 나는' ,'나는 밥을 먹었다네' , '닝닝' ,TO_DATE('241028','yymmdd') ,TO_DATE('241028','yymmdd') );

insert into boardtable(userid, title, content, username, created_at, updated_at) 
    VALUES(boardtable_userid_seq.nextval, '내가 하고싶은 말' , '나는 밥을 먹었을까?' , '카리나', sysdate , sysdate );

insert into boardtable(userid, title, content, username, created_at, updated_at) 
    VALUES(boardtable_userid_seq.nextval, '졸리다' , '나는 밥을 먹었을까?' , '지젤', sysdate , sysdate );
    

// 수정
update boardtable
    set title = 'title' , content = 'content' , username = 'username' , updated_at = sysdate
    where userid = 2;


// 삭제
delete from boardtable where userid = 22;


// 단건 조회
select userid, title, content, username, created_at, updated_at
    from boardtable
    where userid = 2;


// 전체 조회
select * from boardtable;


commit;