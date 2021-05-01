# CHAPTER 5 : 데이터베이스 프로그래밍

## 목차

> - 01 | [데이터베이스 프로그래밍의 개념](#01-데이터베이스-프로그래밍의-개념)
> - 02 | [저장 프로그램](#02-저장-프로그램)
> - 03 | [데이터베이스 연동 자바 프로그래밍](#03-데이터베이스-연동-자바-프로그래밍)
> - 04 | [데이터베이스 연동 웹 프로그래밍](#04-데이터베이스-연동-웹-프로그래밍)

<br/>


## 01. 데이터베이스 프로그래밍의 개념

#### 데이터베이스 프로그래밍 방법

1. SQL 전용 언어 사용하는 방법
2. 일반 프로그래밍 언어에 SQL을 삽입하여 사용하는 방법
  ex) 자바, C, C++ 등
  
3. 웹 프로그래밍 언어에 SQL을 삽입하여 사용하는 방법
  ex) JSP, ASP, PHP 등
  
4. ~~4GL(4th Generation Language)~~

<br/>

#### DBMS 플랫폼과 데이터베이스 프로그래밍의 유형

![image](https://user-images.githubusercontent.com/62230430/116764873-87165400-aa5d-11eb-9753-bec12dacc52a.png)

<br/>

## 02. 저장 프로그램

### 저장 프로그램

: 데이터베이스 응용 프로그램을 작성하는 데 사용하는 MySQL의 SQL 전용 언어

- SQL 문에 변수, 제어, 입출력 등의 프로그래밍 기능을 추가 -> SQL만으로 처리하기 어려운 문제 해결

<br/>

### 프로시저

#### 삽입 작업을 하는 프로시저

ex) Book 테이블에 한 개의 튜플을 삽입하는 프로시저

![image](https://user-images.githubusercontent.com/62230430/116764961-f68c4380-aa5d-11eb-940f-f3aede19070c.png)


<br/>

#### 제어문을 사용하는 프로시저

![image](https://user-images.githubusercontent.com/62230430/116764978-086de680-aa5e-11eb-8c56-ad66db36d28b.png)

ex) 동일한 도서가 있는지 점검한 후 삽입하는 프로시저

![image](https://user-images.githubusercontent.com/62230430/116765021-2f2c1d00-aa5e-11eb-87f8-288bd592add3.png)



<br/>


#### 결과를 반환하는 프로시저

ex) Book 테이블에 저장된 도서의 평균가격을 반환하는 프로시저

![image](https://user-images.githubusercontent.com/62230430/116765046-45d27400-aa5e-11eb-9197-64934b12ddc6.png)

<br/>

#### 커서를 사용하는 프로시저

![image](https://user-images.githubusercontent.com/62230430/116765069-5edb2500-aa5e-11eb-9c4a-786f8d03ebc0.png)

<br/>

### 트리거

: 데이터의 변경(INSERT, DELETE, UPDATE) 문이 실행될 때 자동으로 따라서 실행되는 프로시저

![image](https://user-images.githubusercontent.com/62230430/116765093-7b775d00-aa5e-11eb-88f6-f811bbcb8420.png)


### 사용자 정의 함수

#### 프로시저, 트리거, 사용자 정의 함수의 공통점과 차이점

![image](https://user-images.githubusercontent.com/62230430/116765114-a6fa4780-aa5e-11eb-8cec-4219fa129695.png)


