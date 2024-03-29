# CHAPTER 2 : 관계 데이터 모델

## 목차

> - 01 | [관계 데이터 모델의 개념](#01-관계-데이터-모델의-개념)
> - 02 | [무결성 제약조건](#02-무결성-제약조건)
> - 03 | [관계대수](#03-관계대수)
> - [MySQL 실습](#mysql-실습)


## 01. 관계 데이터 모델의 개념

### 릴레이션

#### 릴레이션(relation)
: 행과 열로 구성된 **테이블**

relation == table / **relationship** : 관계 (다른 것임 !)

#### 릴레이션 구조와 관련 용어

<도서 릴레이션(테이블)>

![image](https://user-images.githubusercontent.com/62230430/110878804-3583ff80-831f-11eb-8246-2df46e786e30.png)

<br/>

### 릴레이션 스키마

#### 스키마의 요소

- **속성** (attribute) : 릴레이션 스키마의 **열**
- **도메인** (domain) : **속성이 가질 수 있는** 값의 집합, 즉 **dataType의 집합**
- **차수** (degree) : 속성의 개수

#### 스키마의 표현

릴레이션 이름(속성1 : 도메인1, 속성2 : 도메인2, ...)

ex. 도서(도서 번호, 도서 이름, 출판사, 가격)

<br/>

### 릴레이션 인스턴스

#### 인스턴스 요소

- **튜플** (tuple) : 릴레이션의 **행**
- **카디날리티** (cardinality) : 튜플의 수

<br/>

### 릴레이션의 특징

**Table == Set(집합)**

1. 속성은 단일 값을 가짐.
2. 속성은 서로 다른 이름을 가짐.
3. 한 속성의 값은 모두 같은 도메인을 가짐.   
  (ex. integer로 도메인을 정의하면, integer만 지정할 수 있음.)  
4. 속성의 순서는 상관 없음. (순서가 달라도 릴레이션은 같은 것)
5. 릴레이션 내의 중복된 튜플은 허용하지 않음.
6. 튜플의 순서는 상관 없음.

<br/>

## 02. 무결성 제약조건

### 키
: 특정 튜플을 **유일하게 식별**할 때 사용하는 **속성** 혹은 **속성의 집합** (다중 선택 가능)

#### 키의 조건

- null 값이 없어야 함.
- 중복 값이 없어야 함.

<br/>

##### <마당 서점 데이터베이스>
![image](https://user-images.githubusercontent.com/62230430/111401860-760fbe80-870d-11eb-95ac-52b702a2419f.png)

#### 슈퍼키
: 튜플을 **유일하게 식별할 수 있는** 하나의 속성 혹은 속성의 집합

즉, 튜플을 유일하게 식별할 수 있는 값이면 모두 슈퍼키가 됨.

ex) [마당 서점 DB](#마당-서점-데이터베이스)의 **고객 릴레이션**에서 **고객번호와 주민번호를 포함한 모든 속성의 집합**이 슈퍼키

#### 후보키
: 튜플을 유일하게 식별할 수 있는 속성의 **최소 집합**

ex) [마당 서점 DB](#마당-서점-데이터베이스)의 **주문 릴레이션**에서 후보키는 두 속성을 합한 (고객번호, 도서번호)가 됨.

#### 기본키
: **여러 후보키 중 하나를 선정**하여 **대표**로 삼는 키 (만약 여러 개라면 특성을 반영하여 하나 선택)

- 기본키 선정 시 고려 사항
  - 중복되지 않아야 함.
  - NULL 값 허용하지 않음.
  - 최대한 적은 수의 속성을 가진 것.

- 릴레이션 스키마를 표현할 때, 기본키는 **밑줄을 그어 표시** : 릴레이션 이름(__속성1__, 속성2, ..., 속성N)
  ex) 고객(__고객번호__, 이름, 주민번호, 주소, 핸드폰)
      도서(__도서번호__, 도서이름, 출판사, 가격)
      
#### 대리키
: 일련번호 같은 가상의 속성을 만들어 기본키로 삼음

- 주문

![image](https://user-images.githubusercontent.com/62230430/111402666-f4209500-870e-11eb-99da-ea7062fcc724.png)

위에서 **주문번호**가 그 예 중 하나임.

#### 대체키
: 기본키로 선정되지 않은 후보키

#### 외래키
: 2개 이상의 릴레이션에 관계를 맺게 하는 키, **다른 릴레이션의 기본키를 참조**하는 속성

- 외래키 예시
![image](https://user-images.githubusercontent.com/62230430/111402796-321db900-870f-11eb-93ec-10f527d47875.png)

주문 릴레이션에서는 고객번호와 도서번호가 중복되지만, 고객 릴레이션이나 도서 릴레이션에서는 각각이 key의 역할을 함. -> **고객 릴레이션이나 도서 릴레이션에서 참조**함.

#### 키의 포함 관계

![image](https://user-images.githubusercontent.com/62230430/112288067-53af0f80-8cd0-11eb-9387-41dac67630f9.png)

<br/>

### 무결성 제약조건

#### 도메인 무결성 제약조건
: **도메인 제약**(domain constraint)

- 도메인에 해당하는 값만 삽입과 수정 가능

#### 개체 무결성 제약조건
: **기본키 제약**(primary key constraint)

- 삽입 : 기본키 값이 같으면 삽입 금지
- 수정 : 기본키 값이 같거나 NULL로 수정 금지
- 삭제 : 특별한 확인 없이 즉시 수행

#### 참조 무결성 제약조건
: **외래키 제약**(foreign key constraint)

- 삽입
  - 부모 릴레이션 : 튜플 삽입 후 수행하면 정상 진행
  - 자식 릴레이션 : 참조받는 테이블에 외래키 값이 없으므로 삽입 금지

- 삭제
  - 부모 릴레이션 : 다른 추가 작업 필요
    1. 즉시 작업 중지
    2. 자식 릴레이션의 관련 튜플 삭제
    3. 초기에 설정된 다른 어떤 값으로 변경
    4. NULL 값으로 
  - 자식 릴레이션 : 바로 삭제 가능


- 수정

<br/>

## 03. 관계대수

### 관계대수

#### 관계대수
: relational algebra, 연산을 이용하여 질의하는 방법을 기술하는 언어

#### 관계 대수 연산자

![image](https://user-images.githubusercontent.com/62230430/112289107-5827f800-8cd1-11eb-8705-ef18136459e6.png)

주로 셀렉션, 프로젝션, 카디전 프로덕트를 사용

#### 관계대수 식

![image](https://user-images.githubusercontent.com/62230430/112289289-8574a600-8cd1-11eb-8019-8c57ad091cc9.png)

- 예시

![image](https://user-images.githubusercontent.com/62230430/112289370-9ae9d000-8cd1-11eb-95d2-6e8c15750afd.png)


<br/>

### 셀렉션과 프로젝션

#### 셀렉션 (selection)
: 튜플을 추출하기 위한 연산, 튜플의 조건을 명시하고 그 조건에 만족하는 튜플 반환

- 형식 : σ<조건>(R)

#### 셀렉션의 확장

- 형식 : σ<복합조건>(R)
- 여러 개의 조건을 ∧(and), ∨ (or), ┑(not) 기호를 이용하여 복합조건을 표시 (ex. σ(가격<=8000 ∧ 도서번호 >=3) (도서))

#### 프로젝션 (projection)
: 릴레이션 속성을 추출하기 위한 연산, 단항 연산자

- 형식 : π<속성리스트>(R)

### 집합 연산

합집합, 교집합, 차집합은 일반 수학 연산과 같기 때문에 skip

#### 카티전 프로덕트 (cartesian product)
: 두 릴레이션을 연결시켜 하나로 합칠 때 사용

- 형식 : R x S
- 결과 릴레이션의 차수 : 두 릴레이션 차수의 합
- 결과 릴레이션의 카디날리티 : 두 릴레이션의 카디날리티의 곱

- 질의 2-6 : 고객 릴레이션과 주문 릴레이션의 카티전 프로덕트를 구하시오.

![image](https://user-images.githubusercontent.com/62230430/112290339-79d5af00-8cd2-11eb-8eb2-ba794240e118.png)

- MySQL

```mysql
SELECT * from customer, orders;
```

<br/>

### 조인

- 형식 : R ⋈ cS = σc (R×S) (c는 조인 조건)
- 조인 연산의 구분
  - 기본 연산 : 세타조인, 동등조인, 자연조인
  - 확장된 조인 연산 : 세미조인, 외부조인

#### 세타조인 (theta join)
: 두 릴레이션의 속성 값을 비교하여 조건을 만족하는 튜플만 반환

- 형식 : R ⋈(r 조건 s) S
- 조건 : {=, ≠, ≤, ≥, ＜, ＞} 중 하나

#### 동등조인 (equi join)
: 세타조인에서 **= 연산자**를 사용한 조인 - 주로 사용

- 형식 : R ⋈(r = s) S
- 질의 2-7 : 고객과 고객 주문 사항을 모두 보이시오.

고객 ⋈ (고객.고객주문번호 = 주문.고객번호) 주문

![image](https://user-images.githubusercontent.com/62230430/112300914-bb6b5780-8cdc-11eb-8209-31a046576ad9.png)

- MySQL

```mysql
SELECT * 
from customer, orders 
where customer.custid = orders.custid; 
```

#### 자연조인 (natural join)
: 동등조인에서 조인에 참여한 속성의 **중복 속성 제거한 결과** 반환

- 형식 : R ⋈ N(r,s) S 
- 질의 2-8 : 고객과 고객의 주문 사항을 모두 보여주되, 같은 속성은 한 번만 표시하시오.

![image](https://user-images.githubusercontent.com/62230430/112303559-c07dd600-8cdf-11eb-8c5f-5a49e1e275b3.png)

- MySQL로 구현하려면 `*` 대신 속성을 모두 입력해줘야 함.

<br/>

### 관계대수 예제

- 질의 2-11 : 마당서점의 도서 중 가격이 8,000원 이하인 도서이름과 출판사를 보이시오.
```mysql
SELECT bookname, publisher 
from book 
where price<=8000;
```

- 질의 2-12 : 마당서점의 박지성 고객의 거래 내역 중 주문번호, 이름, 가격을 보이시오.
```mysql
SELECT ordersid, name, saleprice 
from orders, customer 
where orders.custid = customers.custid and customer.name = '박지성';
```

<br/>

## MySQL 실습

- Table 생성

```mysql
CREATE TABLE Book (
  bookid      INTEGER PRIMARY KEY, # 변수명 타입 (키종류) 순
  bookname    VARCHAR(40),
  publisher   VARCHAR(40),
  price       INTEGER
);
```

- Table 삭제

```mysql
DROP TABLE Book; # Book table 삭제
```

- 삽입

```mysql
INSERT INTO Book VALUES(11, 'Olympic Champions 2', 'Person', 150000);

# 10은 이미 존재하기 때문에 오류
INSERT INTO Book VALUES(10, 'Olympic Champions 2', 'Person', 150000); 
```

- 프로젝션

```mysql
select name from customer; # name 속성을 프로젝션
```




