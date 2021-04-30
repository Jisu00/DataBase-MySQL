# CHAPTER 4 : SQL 고급

## 목차

> - 01 | [내장 함수](#01-내장-함수)
> - 02 | [부속질의](#02-부속질의)
> - 03 | [뷰](#03-뷰)
> - 04 | [인덱스](#04-인덱스)

<br/>


## 01. 내장 함수

### SQL 내장 함수

#### MySQL에서 제공하는 주요 내장 함수

![image](https://user-images.githubusercontent.com/62230430/114272697-24c7c600-9a52-11eb-901b-4470b31f3dab.png)

<br/>

#### 숫자 함수

![image](https://user-images.githubusercontent.com/62230430/114272684-1da0b800-9a52-11eb-89a0-3dd47ae35d76.png)

<br/>

- 질의 4-1 : -78과 +78의 절댓값을 구하시오.

```mysql
SELECT  ABS(-78), ABS(+78)
FROM    Dual;
```
여기서 `Dual`은 실제 함수 내용이 아닌 가상함수임. Oracle에서는 `FROM`이 필수이기 때문에 사용해야 하지만, MySQL에서는 생략 가능

<br/>

#### 문자 함수

![image](https://user-images.githubusercontent.com/62230430/114273057-96ecda80-9a53-11eb-8c37-6c419a0ba3d1.png)


- 질의 4-4 : 도서제목에 야구가 포함된 도서를 농구로 변경한 후 도서 목록을 보이시오.

```mysql
SELECT  bookid, REPLACE(bookname, '야구', '농구') bookname, publisher, price
FROM    Book;
```

<br/>

#### 날짜, 시간 함수

![image](https://user-images.githubusercontent.com/62230430/114273269-5f326280-9a54-11eb-8ee8-fa3c0a8f0db6.png)

- format의 주요 지정자

![image](https://user-images.githubusercontent.com/62230430/114273282-69546100-9a54-11eb-8e3b-67dbb0c56dd8.png)

<br/>

### NULL 값 처리

- 집계 함수를 사용할 때 주의할 점 : 집계 함수 계산 시 NULL이 포함된 행은 집계에서 빠짐

#### IS NULL, IS NOT NULL

- `IS NULL` : NULL 값을 찾을 때 ('=' 사용 X)
- `IS NOT NULL` : NULL이 아닌 값을 찾을 때 (`< >` 사용 X)

- 예시

MyBook
|bookid|price|
|:----:|:---:|
|1|10000|
|2|20000|
|3|NULL|

```mysql
SELECT  *
FROM    Mybook
WHERE   price IS NULL; # bookid 3과 price null 출력
```

<br/>

### 행번호 출력

MySQL에서 **변수**는 이름 앞에 `@` 기호를 붙이며, 치환문에는 `SET`과 `:=` 기호를 사용

- 질의 4-11 : 고객 목록에서 고객번호, 이름, 전화번호를 앞의 두 명만 보이시오.

```mysql
SET       @seq:=0; # 이 코드 먼저 실행하고
SELECT    (@seq:=@seq+1) '순번', custid, name, phone # 이 코드 실행해야 함!
FROM      Customer
WHERE     @seq < 2;
```

<br/>

## 02. 부속질의
: 하나의 SQL 문 안에 다른 SQL 문이 중첩된 질의

- 데이터가 대량일 때, 데이터를 모두 합쳐 연산하는 조인보다 **필요한 데이터만 찾아서 공급**해주는 부속질의가 성능이 더 좋음.
- 실행 순서 : 부속질의 -> 주질의

![image](https://user-images.githubusercontent.com/62230430/114273717-34e1a480-9a56-11eb-90ab-aa953a1c6a83.png)

<br/>

#### 부속질의의 종류

![image](https://user-images.githubusercontent.com/62230430/114273751-52af0980-9a56-11eb-8df5-eb4622289334.png)

<br/>

### 스칼라 부속질의 - SELECT 부속질의

부속질의의 결과 값으로 단일 행, 단일 열의 스칼라 값으로 반환

![image](https://user-images.githubusercontent.com/62230430/114273787-77a37c80-9a56-11eb-8887-13cb96e76c1a.png)

- 질의 4-12 : 마당서점의 고객별 판매액을 보이시오(고객이름과 고객별 판매액을 출력).

```mysql
SELECT  ( SELECT    name
          FROM      Customer cs
          WHERE     cs.custid=od.custid ) ‘name’, SUM(saleprice) ‘total’
FROM 	    Orders od
GROUP BY     od.custid;
```

<br/>

- 질의 4-12 : Orders 테이블에 각 주문에 맞는 도서이름을 입력하시오.

```mysql
UPDATE 	Orders
SET 	  bookname = ( SELECT bookname
                     FROM Book
                     WHERE Book.bookid=Orders.bookid );
```

<br/>

### 인라인 뷰 - FROM 부속질의

: 테이블 이름 대신 인라인 뷰 부속질의를 사용하면 보통의 테이블과 같은 형태로 사용 가능

- 질의 4-14 : 고객번호가 2 이하인 고객의 판매액을 보이시오(고객이름과 고객별 판매액 출력).

```mysql
SELECT    cs.name, SUM(od.saleprice) ‘total’
FROM 	    (SELECT  custid, name
	         FROM   Customer
	         WHERE  custid <= 2) cs, # ( ) 안이 인라인 뷰
           Orders   od
WHERE 	  cs.custid=od.custid
GROUP BY  cs.name;
```

<br/>

### 중첩질의 - WHERE 부속질의

보통 데이터를 선택하는 조건 혹은 술어와 같이 사용됨

#### 중첩질의 연산자의 종류

![image](https://user-images.githubusercontent.com/62230430/114274036-96eed980-9a57-11eb-89be-343ed07a7adf.png)

- `ALL` : 뒤에 오는 부속 질의의 집합의 값 전체가 비교 연산자 만족되어야 함.
- `SOME` : 뒤에 오는 값 중에 하나 이상만 비교 연산자 만족되면 됨.
- `IN` : 뒤에 오는 값 또는 집합의 값들에 속하는지
- `EXISTS` : 부속질의로 가져온 결과가 공집합인지 아닌지 / FROM과 상관부속질의로 주로 사용

<br/>

#### 비교 연산자
: 부속질의가 반드시 단일 행, 단일 열을 반환해야 함

- 질의 4-16 : 각 고객의 평균 주문금액보다 큰 금액의 주문 내역에 대해서 주문번호, 고객번호, 금액을 보이시오.

```mysql
SELECT 	orderid, custid, saleprice
FROM 	Orders od
WHERE 	saleprice > (SELECT AVG(saleprice)
                     FROM   Orders so
                     WHERE  od.custid=so.custid);
```

<br/>

#### IN, NOT IN
: 부속질의에서 제공한 결과 집합에 있는지 확인하는 역할 (NOT IN은 반대)

- 질의 4-17 : 대한민국에 거주하는 고객에게 판매한 도서의 총판매액을 구하시오.

```mysql
SELECT 	  SUM(saleprice) ‘total’
FROM 	    Orders
WHERE 	  custid IN (SELECT   custid
                     FROM     Customer
                     WHERE    address LIKE '%대한민국%');
```

<br/>

#### ALL, SOME(ANY)
: 결과가 하나 이상일 때 사용

- 구문 구조
![image](https://user-images.githubusercontent.com/62230430/114274201-4e83eb80-9a58-11eb-87ca-b68039ced7d3.png)


- 질의 4-18 : 3번 고객이 주문한 도서의 최고 금액보다 더 비싼 도서를 구입한 주문의 주문번호와 금액을 보이시오.

```mysql
SELECT 	  orderid, saleprice
FROM 	    Orders
WHERE 	  saleprice > ALL (SELECT  saleprice
                           FROM   Orders
                           WHERE custid='3');
```

<br/>

#### EXISTS, NOT EXISTS
: 데이터의 존재 유무를 확인하는 연산자

- 구문 구조
![image](https://user-images.githubusercontent.com/62230430/114274250-812de400-9a58-11eb-8c87-d3693c8ce2d0.png)


- 질의 4-19 : EXISTS 연산자로 대한민국에 거주하는 고객에게 판매한 도서의 총 판매액을 구하시오.

```mysql
SELECT 	  SUM(saleprice) ‘total’
FROM 	    Orders od
WHERE 	  EXISTS (SELECT   *
                  FROM    Customer cs
                  WHERE   address LIKE '%대한민국%' AND cs.custid=od.custid);
```

<br/>

## 03. 뷰

### 뷰
: 하나 이상의 테이블을 합하여 만든 가상의 테이블 (스키마만 가짐)

#### 뷰의 장점

- **편리성 및 재사용성** : 자주 사용되는 복잡한 질의를 뷰로 미리 정의할 수 있음.
- **보안성** : 사용자별 필요한 데이터만 선별하여 보여줄 수 있음. 중요한 질의의 경우 **암호화** 가능
- **독립성** : 미리 정의된 뷰를 일반 테이블처럼 사용 가능하기 때문에 편리, 사용자가 필요한 정보만 요구에 맞게 가공 가능

<br/>

#### 뷰의 특징

- 원본 데이터 값에 따라 같이 변함
- 독립적인 인덱스 생성 어려움
- 삽입, 삭제, 갱신 연산에 많은 제약이 따름

<br/>

### 뷰의 생성

- 기본 문법

![image](https://user-images.githubusercontent.com/62230430/116661308-33f5c000-a9cf-11eb-8fe4-268f24258e59.png)

<br/>

- 질의 4-20 : 주소에 '대한민국'을 포함하는 고객들로 구성된 뷰를 만들고 조회하시오. 뷰의 이름은 vw_Customer로 설정하시오.

```mysql
CREATE VIEW    vw_Customer
AS SELECT      *
   FROM        Customer
   WHERE       address LIKE '%대한민국%';
```

<br/>

- 질의 4-21 : Orders 테이블에 고객이름과 도서이름을 바로 확인할 수 있는 뷰를 생성한 후, ‘김연아’ 고객이 구입한 도서의 주문번호, 도서이름, 주문액을 보이시오.

```mysql
CREATE VIEW	vw_Orders (orderid, custid, name, bookid, bookname, saleprice, orderdate)
AS SELECT	od.orderid, od.custid, cs.name,
	        od.bookid, bk.bookname, od.saleprice, od.orderdate
   FROM       	Orders od, Customer cs, Book bk
   WHERE     	od.custid =cs.custid AND od.bookid =bk.bookid;
```

<br/>

### 뷰의 수정

- 기본 문법

![image](https://user-images.githubusercontent.com/62230430/116661730-e2016a00-a9cf-11eb-992e-cd860bc36476.png)

<br/>

- 질의 4-22 : [질의 4-20]에서 생성한 뷰 vw_Customer는 주소가 대한민국인 고객을 보여준다. 이 뷰를 영국을 주소로 가진 고객으로 변경하시오. phone 속성은 필요 없으므로 포함시키지 마시오.

```mysql
CREATE OF REPLACE VIEW 	vs_Customer (custid, name, address)
AS SELECT		custid, name, address
   FROM			Customer
   WHERE		address LIKE '%영국%';
```

<br/>

결과 확인

```mysql
SELECT	*
FROM	vw_Customer;
```

<br/>

### 뷰의 삭제

- 기본 문법

![image](https://user-images.githubusercontent.com/62230430/116662015-33115e00-a9d0-11eb-9fa8-c604315f842a.png)

<br/>

- 질의 4-23 : 앞서 생성한 뷰 vw_Customer를 삭제하시오.

```mysql
DROP VIEW	vw_Customer;
```

<br/>

결과 확인

```mysql
SELECT	*
FROM	vw_Customer;
```

<br/>

## 04. 인덱스

#### 데이터베이스의 물리적 저장

![image](https://user-images.githubusercontent.com/62230430/116662475-c6e32a00-a9d0-11eb-96d8-ffe5f0e57851.png)


<br/>

### 인덱스와 B-tree

- 인덱스(index, 색인) : 데이터를 쉽고 빠르게 찾을 수 있도록 만든 구조 (주로 B-tree 사용)

#### B-tree의 구조

![image](https://user-images.githubusercontent.com/62230430/116662603-f1cd7e00-a9d0-11eb-9504-131f63b8575b.png)


<br/>

#### 인덱스의 특징

- 테이블에서 한 개 이상의 속성을 이용하여 생성
- **빠른 검색**과 함께 효율적인 레코드 접근이 가능
- 순서대로 정렬된 속성과 데이터의 위치만 보유 -> **작은 공간 차지**
- 저장된 값들은 테이블의 부분 집합
- 일반적으로 **B-tree** 형태의 구조 가짐
- 데이터의 수정, 삭제 등의 변경이 발생하면 인덱스 재구성이 필요

<br/>

### MySQL 인덱스

#### 클러스터 인덱스

![image](https://user-images.githubusercontent.com/62230430/116662905-58529c00-a9d1-11eb-842d-1c0d512c745d.png)


<br/>

#### MySQL 인덱스 B-tree

![image](https://user-images.githubusercontent.com/62230430/116662960-6c969900-a9d1-11eb-9584-108cc2a5c4c1.png)

<br/>

#### MySQL 인덱스의 종류

![image](https://user-images.githubusercontent.com/62230430/116663009-7c15e200-a9d1-11eb-90b2-a30d59bb6fd0.png)

<br/>

### 인덱스의 생성

#### 인덱스 생성 시 고려사항

- 인덱스는 WHERE 절에 자주 사용되는 속성이어야 함.
- 인덱스는 조인에 자주 사용되는 속성이어야 함.
- 단일 테이블에 인덱스가 많으면 속도가 느려질 수 있음.
- 속성이 가공되는 경우 사용하지 않음.
- 속성의 선택도가 낮을 때 유리함. (속성의 모든 값이 다른 경우)

<br/>

#### 인덱스의 생성 문법

![image](https://user-images.githubusercontent.com/62230430/116663244-ca2ae580-a9d1-11eb-9e38-dea8c0682c3c.png)

<br/>

- 질의 4-24 : Book 테이블의 bookname 열을 대상으로 비 클러스터 인덱스 ix_Book을 생성하라.

```mysql
CREATE INDEX ix_Book ON Book(bookname);
```

<br/>

- 질의 4-25 : Book 테이블의 publisher, price 열을 대상으로 인덱스 ix_Book2 생성하시오.

```mysql
CREATE INDEX ix_Book2 ON Book(publisher, price);
```

<br/>

### 인덱스의 재구성과 삭제

#### 인덱스 재구성

- 생성 문법

![image](https://user-images.githubusercontent.com/62230430/116663542-33aaf400-a9d2-11eb-8a65-6054812cb3da.png)


<br/>

- 질의 4-26 : Book 테이블의 인덱스를 최적화하시오.

```mysql
ALTER TABLE	Book;
```

<br/>

#### 인덱스의 삭제

- 삭제 문법

![image](https://user-images.githubusercontent.com/62230430/116663611-4fae9580-a9d2-11eb-8def-9ed0179a4223.png)

<br/>

- 질의 4-27 : 인덱스 ix_Book을 삭제하시오.

```mysql
DROP INDEX ix_Book ON Bppk;
```




















