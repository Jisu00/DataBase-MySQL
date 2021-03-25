# CHAPTER 3 : SQL 기초

## 목차

> - 01 | [SQL 학습을 위한 준비](#01-sql-학습을-위한-준비)
> - 02 | [SQL 개요](#02-sql-개요)
> - 03 | [데이터 조작어 - 검색](#03-데이터-조작어--검색)
> - 04 | [데이터 정의어](#04-데이터-정의어)
> - 05 | [데이터 조작어 - 삽입, 수정, 삭제](#05-데이터-조작어--삽입-수정-삭제)


## 01. SQL 학습을 위한 준비

`MySQL` 설치에 관한 내용이므로 넘어감. 또한 이 책에서의 모든 예시는 책에서 제공하는 '마당 서점 DB'를 사용

<br/>

## 02. SQL 개요

### SQL 개요

SQL의 용도 : 데이터베이스에서 데이터를 추출하여 문제 해결

#### SQL 기능에 따른 분류

- 데이터 정의어 (DDL) : 테이블이나 관계의 구조를 생성하는 데 사용
  - 예시 : `CREATE`, `ALTER`, `DROP` 등
- **데이터 조작어 (DML)** : 테이블에 데이터를 **검색**, **삽입**, **수정**, **삭제**하는 데 사용
  - 예시 : `SELECT`, `INSERT`, `DELETE`, `UPDATE` 등
- 데이터 제어어 (DCL) : 데이터의 사용 권한을 관리
  - 예시 : `GRANT`, `REVOKE` 등

<br/>

## 03. 데이터 조작어

### SELECT 문

#### SELECT 문의 기본 문법

```mysql
// []: 대괄호 안의 예약어들은 선택적으로 사용

SELECT [ALL or DISTINT] 속성 이름(들)
FROM       테이블 이름(들)
[WHERE     검색 조건(들)]
[GROUP BY  속성 이름] // 속성 값이 같은 튜플들끼리 묶어줌.
[HAVING    검색 조건(들)]
[ORDER BY  속성 이름 [ASC or DESC]]
```

#### SELECT / FROM

- 질의 3-2 : 모든 도서의 도서번호, 도서이름, 출판사, 가격을 검색하시오.

```mysql
SELECT  bookid, bookname, publisher, price // * 로 바꿔 쓸 수 있음
FROM    Book;
```

<br/>

- 중복을 제거하고 싶으면 `DISTINCT`라는 키워드 사용

```mysql
SELECT  DISTINCT publisher
FROM    Book;
```

#### WHERE 조건

- WHERE 절에 조건으로 사용할 수 있는 술어

![image](https://user-images.githubusercontent.com/62230430/112476689-84b54000-8db5-11eb-80b2-77e606dc634c.png)


- 질의 3-5 : 가격이 10,000원 이상 20,000 이하인 도서를 검색하시오.

```mysql
SELECT  *
FROM    Book
WHERE   price BETWEEN 10000 AND 20000;
```
`BETWEEN` 에는 '='이 내포되어 있음.

<br/>

- 질의 3-6 : 출판사가 '굿스포츠' 혹은 '대한미디어'인 도서를 검색하시오.

```mysql
SELECT  *
FROM    Book
WHERE   pusblisher IN ('굿스포츠', '대한미디어'); // 아닌 것은 NOT IN 사용
```

<br/>

- 질의 3-8 : 도서 이름에 '축구'가 포함된 출판사를 검색하시오.

```mysql
SELECT  bookname, publisher
FROM    Book
WHERE   bookname LIKE '%축구%' // 축구~ 에 해당하는 것밖에 없으니 '축구%'라고 해도 무방
```

<br/>

- 질의 3-9 : 도서이름의 왼쪽 두 번째 위치에 ‘구’라는 문자열을 갖는 도서를 검색하시오.

```mysql
SELECT  *
FROM    Book
WHERE     bookname LIKE '_구%';
```

<br/>

- 와일드 문자 종류

![image](https://user-images.githubusercontent.com/62230430/112477369-453b2380-8db6-11eb-9d86-c8853d0e085a.png)

<br/>

- 질의 3-10 : 축구에 관한 도서 중 가격이 20,000원 이상인 도서를 검색하시오.

```mysql
SELECT  *
FROM    Book
WHERE   bookname LIKE '%축구%' AND price >= 20000;
```

#### ORDER BY

- 질의 3-12 : 도서를 가격순으로 검색하고, 가격이 같으면 이름순으로 검색하시오.

```mysql
SELECT    *
FROM      Book
ORDER BY  price, bookname;
```
변수의 순서가 앞에 올 수록 우선순위가 높은 것임.


#### 집계 함수

- 질의 3-15 : 고객이 주문한 도서의 총 판매액을 구하시오.

```mysql
SELECT  SUM(saleprice)
FROM    Orders;
```

<br/>

- 의미 있는 열 이름을 출력하고 싶을 때, 속성 이름의 별칭을 지칭하는 `AS` 키워드 사용

```mysql
SELECT  SUM(salesprice) AS 총매출
FROM    Orders;
```

<br/>

- 질의 3-17 : 고객이 주문한 도서의 총 판매액, 평균값, 최저가, 최고가를 구하시오.

```mysql
SELECT  SUM(salesprice) AS Total,
        AVG(salesprice) AS Average,
        MIN(salesprice) AS Minimum,
        MAX(salesprice) AS Maximum
FROM    Orders;
```

<br/>

- 질의 3-18 : 마당서점의 도서 판매 건수를 구하시오.

```mysql
SELECT  COUNT(*)
FROM    Orders;
```

<br/>

- 집계 함수의 종류

![image](https://user-images.githubusercontent.com/62230430/112481493-67cf3b80-8dba-11eb-91d3-bcc048f4d7db.png)


#### GROUP BY

- 질의 3-19 : 고객별로 주문한 도서의 총 수량과 총 판매액을 구하시오.

```mysql
SELECT    custid, COUNT(*) AS  도서수량, SUM(salesprice) AS 총액
FROM      Orders
GROUP BY  custid;
```
