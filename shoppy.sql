use shoppy;
select database();
show tables;
select * from member;
desc member;

-- pwd 사이즈 변경
alter table member modify column pwd varchar(100) not null;
desc member;

-- mysql은 수정, 삭제 시 update mode를 변경
SET SQL_SAFE_UPDATES = 0;

delete from member 
where mdate = '2025-10-17';

delete from member
where id = 'test222';

select pwd from member where id='hong';
select count(*) as pwd from member where id = 'hong';

select * from member;

/***********************************
	상품 테이블 생성 : product
***********************************/
create table product(
	pid		int		auto_increment primary key,	
	name	varchar(200)	not null,
    price	long,
    info	varchar(200),
    rate	double,
    image	varchar(100),
    imgList json
);
desc product;
select * from product;
insert into product(name, price, info, rate, image, imgList) 
	values
    ('후드티', 15000, '검정색 후드티', 2.2, '2.webp', JSON_Array('2.webp','2.webp','2.webp')),
    ('원피스', 25000, '원피스', 4, '3.webp', JSON_Array('3.webp','3.webp','3.webp')),
    ('반바지', 12000, '반바지', 3.2, '4.webp', JSON_Array('4.webp','4.webp','4.webp')),
    ('티셔츠', 20000, '티셔츠', 5, '4.webp', JSON_Array('5.webp','5.webp','5.webp')),
    ('스트레치 비스트 드레스', 55000, '스트레치 비스트 드레스', 3, '6.webp', JSON_Array('6.webp','6.webp','6.webp')),
    ('자켓', 115000, '자켓', 3.5, ' 7.webp', JSON_Array('7.webp','7.webp','7.webp'));
    

select * from product ;  
desc product;  

select pid, name, price, info, rate, trim(image) as image, imgList from product
where pid = 1;


/**********************************************
	상품 상세정보 테이블 생성 : product_detailinfo
**********************************************/
show tables;
drop table product_detailinfo;
create table product_detailinfo (
	did			int				auto_increment		primary key,
    title_en	varchar(100)	not null,
    title_ko	varchar(100)	not null,
    pid			int				not null,
    list		json,  -- nodeJS(JSON), springboot(String, List<>)
    constraint fk_product_pid	foreign key(pid)
    references product(pid)
    on delete cascade
    on update cascade
);
desc product_detailinfo;
select * from product_detailinfo;

-- mysql에서 json, csv, excel... 데이터 파일을 업로드 하는 경로
show variables like 'secure_file_priv';

-- products.json 파일의 detailinfo 정보 매핑
insert into product_detailinfo(title_en, title_ko, pid, list)
select 
	jt.title_en,
    jt.title_ko,
    jt.pid,
    jt.list
from
	json_table(
		cast(load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/products.json') 
				AS CHAR CHARACTER SET utf8mb4 ),
		'$[*]' COLUMNS (
			 title_en   	VARCHAR(100)  PATH '$.detailInfo.title_en',
			 title_ko   	VARCHAR(100)  PATH '$.detailInfo.title_ko',
			 list   	json PATH '$.detailInfo.list',
			 pid		int	 PATH '$.pid'
		   )   
    ) as jt ;

select * from product_detailinfo;

-- pid: 1에 대한 상품정보와 상세정보 출력 
select *
from product p, product_detailinfo pd
where p.pid = pd.pid and p.pid = 1 ;

select * from product;
desc product_detailinfo;

select did, title_en, title_ko, pid, list
	from product_detailinfo
    where pid = 1;

select * from member;
select now() from dual;

/**********************************************
	상품 QnA 테이블 생성 : product_qna
**********************************************/
show tables;
desc member;
create table product_qna (
	qid				int					auto_increment		primary key,
    title				varchar(100)		not null,
    content			varchar(200),
    is_complete	boolean,
    is_lock			boolean,
    id					varchar(50)		not null,
    pid				int					not null,
    cdate			datetime,
    constraint 	fk_product_qna_pid	foreign key(pid)		references product(pid)
		on delete cascade	on update cascade,
    constraint 	fk_member_id			foreign key(id)		references member(id)
		on delete cascade	on update cascade
);
desc product_qna;
select * from product_qna;

-- mysql에서 json, csv, excel... 데이터 파일을 업로드 하는 경로
show variables like 'secure_file_priv';

-- product_qna data insert
-- insert into product_qna(title, content, is_complete, is_lok, id, pid, cdate)
select
	jt.title,
    jt.content,
    jt.is_complete,
    jt.is_lock,
    jt.id,
    jt.pid,
    jt.cdate
from
	json_table(
		cast(load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/productsQnA.json') 
				AS CHAR CHARACTER SET utf8mb4 ),
		'$[*]' COLUMNS (
			 title		   		VARCHAR(100)  	PATH '$.title',
			 content   		VARCHAR(100)  	PATH '$.content',
			 is_complete  boolean 				PATH '$.isComplete',
			 is_lock  		boolean 				PATH '$.isLock',
			 id					varchar(50)			PATH '$.id',
             pid				int						PATH '$.pid',
             cdate			datetime				PATH '$.cdate'
		   )   
    ) as jt ;

select * from product_qna;
select * from member;

-- hong 회원이 분홍색 후드티(pid:1) 상품에 쓴 QnA 조회
-- 회원아이디(id), 회원명(name), 가입날짜(mdate), 상품명(name), 상품가격(price), 
-- QnA 제목(title), 내용(content), 등록 날짜(cdate)
select 
	m.id,
    m.name,
    m.mdate,
    p.pid,
    p.name,
    p.price,
    pq.title,
    pq.content,
    pq.cdate
from member m, product p, product_qna pq
where m.id = pq.id and p.pid = pq.pid
	and m.id = 'hong' and p.pid = 1;










    
    
    