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
show tables;
-- drop table product;
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
    ('후드티', 15000, '분홍 후드티', 3.2, '1.webp', JSON_Array('1.webp','1.webp','1.webp')),
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
	상품상세정보 테이블 생성 : product_detailinfo
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

-- mac, windows : mysql에서 json, csv, excel... 데이터 파일을 업로드 하는 경로
show variables like 'secure_file_priv';

-- mac
SET @json = CAST(LOAD_FILE('/usr/local/mysql-files/products.json') AS CHAR CHARACTER SET utf8mb4);

-- JSON이 잘 읽혔는지 확인
SELECT LENGTH(@json) AS len, JSON_VALID(@json) AS is_valid;
-- len > 0, is_valid = 1 이면 OK


-- mac
/*
INSERT INTO product_detailinfo (title_en, title_ko, pid, `list`)
SELECT 
    jt.title_en,
    jt.title_ko,
    jt.pid,
    jt.`list`
FROM JSON_TABLE(
    @json,
    '$[*]' COLUMNS (
        pid        INT          PATH '$.pid',
        title_en   VARCHAR(100) PATH '$.detailInfo.title_en',
        title_ko   VARCHAR(100) PATH '$.detailInfo.title_ko',
        `list`     JSON         PATH '$.detailInfo.list'
    )
) AS jt;
*/

-- windows
/*
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
*/

select * from product_detailinfo;

-- pid: 1에 대한 상품정보와 상세정보 출력 
select *
from product p, product_detailinfo pd
where p.pid = pd.pid and p.pid = 1 ;

select * from product;
desc product_detailinfo;

select did, title_en as titleEn, title_ko as titleKo, pid, list
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
	qid			int				auto_increment		primary key,
    title		varchar(100) 	not null,
    content		varchar(200),
    is_complete	boolean,
    is_lock		boolean,
    id			varchar(50)		not null,
    pid			int				not null,
    cdate		datetime,
    constraint fk_product_qna_pid	foreign key(pid)  references product(pid)
		on delete cascade   on update cascade,
	constraint fk_member_id	foreign key(id)  references member(id)
		on delete cascade   on update cascade
);
desc product_qna;
select * from product_qna;

-- mysql에서 json, csv, excel... 데이터 파일을 업로드 하는 경로
show variables like 'secure_file_priv';

-- mac
-- SET @json = CAST(LOAD_FILE('/usr/local/mysql-files/productQnA.json') AS CHAR CHARACTER SET utf8mb4);

-- mac : JSON load 확인
-- SELECT LENGTH(@json) AS len, JSON_VALID(@json) AS is_valid;
-- len > 0, is_valid = 1 이면 OK

-- mac : product_qna data insert
/*
insert into product_qna(title, content, is_complete, is_lock, id, pid, cdate)
SELECT 
    jt.title,
    jt.content,
    jt.is_complete,
    jt.is_lock,
    jt.id,
    jt.pid,
    jt.cdate
FROM JSON_TABLE(
    @json,
    '$[*]' COLUMNS (
			 title   		VARCHAR(100)  	PATH '$.title',
			 content   		VARCHAR(200)  	PATH '$.content',
			 is_complete 	boolean 		PATH '$.isComplete',
             is_lock 		boolean 		PATH '$.isLock',
			 id				varchar(50)	 	PATH '$.id',
             pid			int				path '$.pid',
             cdate			datetime		path '$.cdate'
		   ) 
) AS jt;
*/

select * from product_qna;
select * from member;

-- hong 회원이 분홍색 후드티(pid:1) 상품에 쓴 QnA 조회
-- 회원아이디(id), 회원명(name), 가입날짜(mdate), 상품명(name), 상품가격(price), 
-- QnA제목(title), 내용(content), 등록날짜(cdate)
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
    
    
/*********************************************************************
	상품 Return/Delivery 테이블 생성 : product_return
**********************************************************************/
show tables;
create table product_return (
	rid			int				auto_increment	primary key,
    title		varchar(100)	not null,
    description	varchar(200),	
    list		json      
);
desc product_return;
select * from product_return;

-- mac : json 파일 형식은 [ { ~~} ], 배열로 감싼 형식
-- SET @json = CAST(LOAD_FILE('/usr/local/mysql-files/productReturn.json') AS CHAR CHARACTER SET utf8mb4);

-- mac : JSON이 잘 읽혔는지 확인
-- SELECT LENGTH(@json) AS len, JSON_VALID(@json) AS is_valid;

-- mac : json_table을 이용하여 데이터 추가
/*
insert into product_return(title, description, `list`)
select 
	jt.title,
    jt.description,
    jt.`list`
FROM JSON_TABLE(
    @json,
    '$[*]' COLUMNS (
			 title   		VARCHAR(100)  	PATH '$.title',
			 description   	VARCHAR(200)  	PATH '$.description',
			 `list` 		json	 		PATH '$.list'
		   ) 
) AS jt;
*/

desc product_return;
select rid, title, description, list from product_return;

/*********************************************************************
	장바구니 테이블 생성 : cart
**********************************************************************/
-- cid, pid, id, size, qty, cdate
create table cart(
	cid			int 	auto_increment		primary key,
    size		char(2)	not null,
    qty			int		not null,
    pid			int		not null,
    id			varchar(50)	not null,
    cdate		datetime 	not null,
    constraint fk_cart_pid	foreign key(pid) references product(pid) 
	on delete cascade		on update cascade,
	constraint fk_cart_id	foreign key(id) references member(id) 
	on delete cascade		on update cascade        
);

show tables;
desc cart;

select now() from dual;
select * from cart;

-- mysql은 수정, 삭제 시 update mode를 변경
SET SQL_SAFE_UPDATES = 0;

select * from cart;
delete from cart where cid in (1,2);
select * from cart;
delete from cart;

-- pid, size를 이용하여 상품의 존재 check 
-- checkQty = 1 인 경우 cid(⭕) 유효 데이터
-- checkQty = 0 인 경우 cid(❌) 무효 데이터
SELECT 
      ifnull(MAX(cid), 0) AS cid,
      COUNT(*) AS checkQty
    FROM cart
    WHERE pid = 1 AND size = 'xs' AND id = 'test';

select * from cart;
select * from member;

-- 장바구니 상품갯수 조회
select count(qty) from cart where id = 'test';
select ifnull(sum(qty), 0) as sumQty from cart where id = 'hong1234';  

-- 장바구니 리스트 조회 : 상품(product) + 장바구니(cart) + 회원(member) 
-- 어떤 회원이 어떤 상품을 몇개 넣었는가???
select  m.id,
		m.name,
        m.phone,
        m.email,
		p.pid,
		p.name,
        p.info,
		p.image,
        p.price,
        c.size,
        c.qty,
        c.cid,
        (select sum(c.qty * p.price) as total_price
			from cart c
			inner join product p on c.pid = p.pid
			where c.id = 'hong') as total
from member m, product p, cart c
where m.id = c.id 
	and p.pid = c.pid
	and m.id = 'hong'; 

select * from cart;   
select * from member;

-- 장바구니 총 상품 가격 : qty(cart), price(product)
select sum(c.qty * p.price) as total_price
from cart c
inner join product p on c.pid = p.pid
where c.id = 'hong';


-- 장바구니 리스트 VIEW 생성
show tables from information_schema;
select * from information_schema.views where table_schema = 'shoppy';
drop view view_cartlist;

select * from view_cartlist where id ='hong1234';

create view view_cartlist
as
select  m.id,
		m.name as mname,
		m.phone,
		m.email,
		p.pid,
		p.name,
		p.info,
		p.image,
	   p.price,
	   c.size,
	   c.qty,
	   c.cid,
       t.totalPrice
   from member m, product p, cart c,
          (select c.id, sum(c.qty * p.price) as totalPrice
			from cart c
			inner join product p on c.pid = p.pid
			group by c.id) as t
   where m.id = c.id
	and p.pid = c.pid
    and c.id = t.id
; 

select id, mname, phone, email, pid, name, info, image, price, size, qty, vc.cid, totalPrice 
from view_cartlist vc,
	(select sum(c.qty * p.price) as totalPrice
		from cart c
		inner join product p on c.pid = p.pid
		where c.id = 'hong'
		) as total
where vc.cid = total.cid   
;                   

select * from view_cartlist;

select c.id, sum(c.qty * p.price) as totalPrice
		from cart c
		inner join product p on c.pid = p.pid
		group by c.id;
        
/*********************************************************************
	고객센터 테이블생성 : support
**********************************************************************/        
create table support(
	sid			int					auto_increment		primary key,
    title			varchar(100)		not null,
    content		varchar(200),
    `type`			varchar(30)		not null,
    hits			int,
    rdate			datetime
);

show tables;

INSERT INTO support(title, `type`, hits, rdate)
SELECT 
    jt.title,
    jt.`type`,
    jt.hits,
    jt.rdate
FROM 
	JSON_TABLE(
		CAST(LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/support_list.json')
			AS CHAR CHARACTER SET utf8mb4),
			'$[*]' COLUMNS (
				title     VARCHAR(100)  PATH '$.title',
				`type`     VARCHAR(30)   PATH '$.type',
				hits      INT           PATH '$.hits',
				rdate     DATETIME      PATH '$.rdate'
			)
	) AS jt;
    
SELECT * from support;
desc support;

select sid, title, `type`, hits, rdate from support;

