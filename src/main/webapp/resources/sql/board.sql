CREATE TABLE board (
   num number not null,
   id varchar2(10) not null,
   name nvarchar2(10) not null,
   subject nvarchar2(100) not null,
   content nvarchar2(500) not null,
   regist_day nvarchar2(30),
   hit number,
   ip nvarchar2(20),
   PRIMARY KEY (num)
);

delete from board;
drop SEQUENCE board_seq;
CREATE SEQUENCE board_seq;

insert into board values
	(board_seq.nextval, 'hgd','홍길동', '제목1', '내용1', sysdate, 0, '');
insert into board values
	(board_seq.nextval, 'hgd','홍길동', '제목2', '내용1', sysdate, 0, '');
insert into board values
	(board_seq.nextval, 'hgd','홍길동', '제목3', '내용1', sysdate, 0, '');
insert into board values
	(board_seq.nextval, 'hgd','홍길동', '제목4', '내용1', sysdate, 0, '');
insert into board values
	(board_seq.nextval, 'hgd','홍길동', '제목5', '내용1', sysdate, 0, '');
insert into board values
	(board_seq.nextval, 'hgd','홍길동', '제목6', '내용1', sysdate, 0, '');	
select * from board;


SELECT * FROM COL WHERE tname='BOARD';
delete from board;
drop table board;
drop SEQUENCE board_seq;

select count(*) from board;

select * from board where subject like '%제목5%' order by num desc;
select * from board where name like '%홍길동%' order by num desc;