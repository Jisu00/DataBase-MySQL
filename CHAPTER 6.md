# CHAPTER 6 : 데이터 모델링

## 목차

> - 01 | [데이터 모델링의 개념](#01-데이터-모델링의-개념)
> - 02 | [ER 모델](#02-er-모델)
> - 03 | [ER 모델을 관계 데이터 모델로 사상](#03-er-모델을-관계-데이터-모델로-사상)
> - 04 | [ERwin 실습](#04-erwin-실습)
> - 05 | [모델링 연습(마당대학 데이터베이스)](#05-모델링-연습마당대학-데이터베이스)

<br/>


## 01. 데이터 모델링의 개념

사용자의 말을 전문 지식을 통해서 모델링

<br/>

### 데이터베이스 생명 주기

![image](https://user-images.githubusercontent.com/62230430/117454346-02f22e00-af81-11eb-8b87-64ba2a85fa69.png)

1. 요구사항 수집 및 분석
2. 설계 : 스키마 도출
3. 구현
4. 운영
5. 감시 및 개선

<br/>

### 데이터 모델링 과정

![image](https://user-images.githubusercontent.com/62230430/117454431-17cec180-af81-11eb-8acc-9de848898c2d.png)

#### 개념적 모델링 
: 개체(entity)를 추출하고 각 개체들 간의 관계를 정의하여 ER 다이어그램을 만드는 과정

![image](https://user-images.githubusercontent.com/62230430/117454733-6bd9a600-af81-11eb-9728-0ace3ee3b6aa.png)

<br/>

#### 논리적 모델링
: 개념적 모델링에서 만든 ER 다이어그램을 사용하려는 DBMS에 맞게 사상하여 실제 DB로 구현하기 위한 모델 만드는 과정
  
![image](https://user-images.githubusercontent.com/62230430/117454774-785dfe80-af81-11eb-94c1-49b7844ee90a.png)

- 과정
  1. 개념적 모델링에서 추출하지 않았던 상세 속성 모두 추출
  2. 정규화 수행
  3. 데이터 표준화 수행

<br/>

#### 물리적 모델링
: 작성된 논리적 모델을 실제 컴퓨터 저장 장치에 저장하기 위한 물리적 구조를 정의하고 구현하는 과정

![image](https://user-images.githubusercontent.com/62230430/117455045-b9eea980-af81-11eb-8a2c-69b9e2e602d9.png)

- 물리적 모델링 시 트랜잭션, 저장 공간 설계 측면에서 고려해야할 사항
  - 응답 시간 최소화
  - 얼마나 많은 트랜잭션을 동시에 발생시킬 수 있는지
  - 데이터가 저장될 공간을 효율적으로 배치


<br/>


## 02. ER 모델

### ER 모델

: (Entity Relationship) 세상의 사물을 개체와 개체 간의 관계로 표현

### 개체와 개체 타입

#### 개체

: 유무형의 정보를 가지고 있는 독립적 실체

<br/>

#### 개체 타입의 ER 다이어그램 표현

![image](https://user-images.githubusercontent.com/62230430/117455698-6761bd00-af82-11eb-9708-b26bb5a2a9a6.png)

- 강한 개체 : 다른 개체의 도움 없이 **독자적으로 존재할 수 있는 개체** (기본키를 가질 수 있음)
- 약한 개체 : 독자적으로는 존재할 수 없고 **반드시 상위 개체 타입을 가짐**

<br/>

### 속성

: 개체가 가진 성질

<br/>

#### 속성의 ER 다이어그램 표현

![image](https://user-images.githubusercontent.com/62230430/117455905-9a0bb580-af82-11eb-9840-c5b10488e12d.png)


- 속성은 기본적으로 타원으로 표시
- 개체 타입을 나타내는 직사각형과 실선으로 연결됨.
- 속성이 개체를 유일하게 식별할 수 있는 키일 경우, 속성 이름에 밑줄

<br/>

#### 속성의 유형

![image](https://user-images.githubusercontent.com/62230430/117456053-bf98bf00-af82-11eb-915b-60047d3a7f74.png)


<br/>

### 관계와 타입

- 관계 (relationship) : 개체 사이의 연관성을 나타내는 개념
- 관계 타입 : 개체 타입과 개체 타입 간의 **연결 가능한 관계**를 정의한 것

![image](https://user-images.githubusercontent.com/62230430/117456215-eb1ba980-af82-11eb-88f8-c62ed386287f.png)


<br/>

#### 차수에 따른 관계 타입의 유형

![image](https://user-images.githubusercontent.com/62230430/117456670-67ae8800-af83-11eb-9062-88f55bddc492.png)



<br/>


#### 관계 대응수에 따른 관계 타입의 유형

- 관계 대응수 : 두 개체 타입의 관계에 실제로 참여하는 개별 개체 수

![image](https://user-images.githubusercontent.com/62230430/117456764-87de4700-af83-11eb-81f5-affb8627ec60.png)



<br/>

#### 관계 대응수의 최솟값과 최댓값

- 관계 대응수의 최솟값과 최댓값 표기
![image](https://user-images.githubusercontent.com/62230430/117456929-b3f9c800-af83-11eb-9bbc-6004ee585366.png)


<br/>


![image](https://user-images.githubusercontent.com/62230430/117457156-ec99a180-af83-11eb-9e8f-60d7def9a8af.png)

<br/>

#### 참여 제약 조건

![image](https://user-images.githubusercontent.com/62230430/117457084-dbe92b80-af83-11eb-97d8-0a97810587c6.png)

<br/>

#### 역할

: 개체 타입 간의 관계를 표현할 때, 각 개체들은 고유한 역할 담당

![image](https://user-images.githubusercontent.com/62230430/117457258-04712580-af84-11eb-88b7-ebdfa487e10a.png)

<br/>

#### 순환적 관계
: 하나의 개체 타입이 자기 자신과 순환적으로 관계를 가지는 형태

![image](https://user-images.githubusercontent.com/62230430/117457347-194db900-af84-11eb-8902-ef8b66209f18.png)


<br/>

### 약한 개체 타입과 식별자

![image](https://user-images.githubusercontent.com/62230430/117457455-2ff41000-af84-11eb-8d4b-2671d5098de9.png)

- 예시

![image](https://user-images.githubusercontent.com/62230430/117457492-3aaea500-af84-11eb-9997-e6620da0df47.png)


<br/>


### IE 표기법

- Erwin 등 소프트웨어에서 사용
- 개체 타입과 속성을 직사각형으로 표현
- 강한관계와 비식별자의 관계는 점선으로 표기
- 약한관계와 식별자의 관계는 실선으로 표기

![image](https://user-images.githubusercontent.com/62230430/117457902-a8f36780-af84-11eb-9150-fa3440787cb1.png)

<br/>

![image](https://user-images.githubusercontent.com/62230430/117457978-bf99be80-af84-11eb-8de5-ee77b59bbf11.png)


<br/>

- 예시

![image](https://user-images.githubusercontent.com/62230430/117458071-d6d8ac00-af84-11eb-9e50-58bd1595a7e0.png)





















