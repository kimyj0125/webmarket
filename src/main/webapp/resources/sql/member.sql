create table member ( 
    id varchar2(10) not null,
    password nvarchar2(10) not null,
    name nvarchar2(10) not null,
    gender nvarchar2(4),
    birth  nvarchar2(10),
    mail  nvarchar2(30),
    phone nvarchar2(20),
    address nvarchar2(90),
    regist_day nvarchar2(50),    
    primary key(id) 
) ;

select * from member;