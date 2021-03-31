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

<br/>

- 질의 3-20 : 가격이 8,000원 이상인 도서를 구매한 고객에 대하여 고객별 주문 도서의 총 수량을 구하시오. 단, 두 권 이상 구매한 고객만 구한다.

```mysql
SELECT    custid, COUNT(*) AS 도서 수량
FROM      Orders
WHERE     saleprice >= 8000
GROUP BY  custid
HAVING    count(*) >= 2;
```

<br/>

#### `GROUP BY`와 `HAVING` 절의 문법과 주의사항

- `GROUP BY` <속성>
  - `GROUP BY`로 튜플을 그룹으로 묶은 후에는 `SELECT` 절에는 `GROUP BY`에서 사용한 <속성>과 집계함수만 가능 (최신 버전에선 다른 속성도 사용 가능하지만, 묶음의 첫번째 요소만 나타남)
  - 예시
    ```mysql
    SELECT    custid, SUM(saleprice) # GROUP BY의 속성과 동일하게
    FROM      Orders
    GROUP BY  custid;
    ```

- `HAVING` <검색조건>
  - `GROUP BY` 절과 함께 작성해야 함.
  - `WHERE` 절보다 뒤에 와야 함.

<br/>

### 두 개 이상 테이블에서 SQL 질의

#### 동등 조인
 
- 질의 3-22 : 고객과 고객의 주문에 관한 데이터를 고객번호 순으로 정렬하여 보이시오. (동등 조인)

```mysql

SELECT    *
FROM      Customer, Orders
WHERE     Customer.custid = Orders.custid
ORDER BY  Customer.custid;
```

<br/>

- 질의 3-24 : 고객별로 주문한 모든 도서의 총 판매액을 구하고, 고객별로 정렬하시오.

```mysql
SELECT    name, sum(saleprice)
FROM      Customer, Orders
WHERE     Customer.custid = Orders.custid
GROUP BY  Customer.name // 최신 버전에서는 Cutomer.custid 로도 같은 결과값 나옴
ORDER BY  Customer.name;
```

<br/>

- 마당서점 데이터 간의 연결

![image](https://user-images.githubusercontent.com/62230430/113091155-50fb6f80-9226-11eb-89e5-95ce9cce0fc9.png)


- 질의 3-25 : 고객의 이름과 고객이 주문한 도서의 이름을 구하시오.

```mysql
SELECT    Customer.name, Book.bookname
FROM      Customer, Orders, Book
WHERE     Customer.custid = Orders.custid AND Orders.bookid = Book.bookid; #연결
```

#### 외부 조인

- 질의 3-27 : 도서를 구매하지 않은 고객을 포함하여 고객의 이름과 고객이 주문한 도서의 판매가격을 구하시오.

```mysql
SELECT    Customer.name, saleprice
FROM      Customer LEFT OUTER JOIN Orders
          ON Customer.custid = Orders.custid;
```

<br/>

- 조인 문법

![image](https://user-images.githubusercontent.com/62230430/113092356-cbc58a00-9228-11eb-8ac1-9768911692d6.png)

  - LEFT JOIN : 왼쪽 테이블을 중심으로 오른쪽 테이블을 매치 (왼쪽 무조건 표시, 오른쪽은 없으면 NULL)
  - RIGHT JOIN : 오른쪽 테이블을 중심으로 왼쪽 테이블을 매치 (오른쪽 무조건 표시, 왼쪽은 없으면 NULL)


<br/>

#### 부속 질의
: 조인으로 해결되지 않을 때 주로 사용

- 실행 순서 : **부속 질의가 먼저 계산**됨

- 질의 3-28 : 가장 비싼 도서의 이름을 보이시오.

```mysql
SELECT    bookname
FROM      Book
WHERE     price = ( SELECT  MAX(price) # = 대신 <을 사용하면 MAX를 제외한 나머지 값
                    FROM    Book );
```

`=`, `<`와 같이 **비교 연산자**를 사용하는 경우, **비교하는 대상이 하나**여야 함.  
**한 개 이상의 경우**는 `IN` 사용함 !


- 질의 3-29 : 도서를 구매한 적이 있는 고객의 이름을 검색하시오.

```mysql
SELECT    name
FROM      Customer
WHERE     custid IN ( SELECT    custid
                      FROM      Orders );
```

<br/>

- 질의 3-30 : 대한미디어에서 출판한 도서를 구매한 고객의 이름을 보이시오.

```mysql
SELECT    name
FROM      Customer
WHERE     custid IN ( SELECT    custid
                      FROM      Orders
                      WHERE     bookid IN ( SELECT    bookid
                                            FROM      Book
                                            WHERE     publisher = "대한미디어"));
```

동등 조인과 유사하지만, 조금 더 좁은 범위를 가져올 때 보통 사용


<br/>

#### 상관 부속질의
: 상위 부속질의의 튜플을 이용하여 하위 부속 질의 계산

- 질의 3-31 : 출판사별로 출판사의 평균 도서 가격보다 비싼 도서를 구하시오.

```mysql
SELECT    b1.bookname
FROM      Book b1
WHERE     b1.price > ( SELECT   avg(b2.price)
                       FROM     Book b2
                       WHERE    b2.publisher = b1.publisher );
```


#### 집합 연산

- 주의 사항 : 양 쪽의 속성과 도메인이 같아야 함 !

- 질의 3-32 : 대한민국에서 거주하는 고객의 이름과 도서를 주문한 고객의 이름을 보이시오.

```mysql
SELECT    name
FROM      Customer
WHERE     address LIKE '대한민국%'
UNION
SELECT    name
FROM      Customer
WHERE     custid IN (SELECT custid FROM Orders);
```
