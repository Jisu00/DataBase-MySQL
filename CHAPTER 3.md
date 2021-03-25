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
SELECT   DISTINCT publisher
```










