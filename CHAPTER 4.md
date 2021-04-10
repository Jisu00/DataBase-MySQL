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


