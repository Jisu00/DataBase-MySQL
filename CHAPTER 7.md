# CHAPTER 7 : 정규화

## 목차

> - 01 | [이상현상](#01-이상현상)
> - 02 | [함수 종속성](#02-함수-종속성)
> - 03 | [정규화](#03-정규화)
> - 04 | [정규화 연습(부동산 데이터베이스)](#04-정규화-연습부동산-데이터베이스)

<br/>


## 01. 이상현상

### 이상현상의 개념

- 삭제이상(deletion anomly) : 튜플 삭제 시, 같이 저장된 다른 정보까지 연쇄적으로 삭제되는 현상
- 삽입이상(insertion anomly) : 튜플 삽입 시, 특정 속성에 해당하는 값이 없어 NULL 값을 입력해야하는 현상
- 수정이상(update anomly) : 튜플 수정 시, 중복된 데이터의 일부만 수정되어 데이터의 불일치 문제가 일어나는 현상

![image](https://user-images.githubusercontent.com/62230430/119244142-68acff80-bba8-11eb-98de-e29d585bad81.png)


<br/>

### 이상현상의 예

- 잘못 설계된 계절학기 수강 테이블

![image](https://user-images.githubusercontent.com/62230430/119244155-8712fb00-bba8-11eb-9ef8-5bdaffecbc31.png)

중복 값이 많음 / 학생 정보와 수업 정보를 합했기 때문에

<br/>

#### 삭제 이상

- 질의 7-1 : 200번 학생의 계절학기 수강신청을 취소하시오.

```mysql
/* C 강좌 수강료 조회 */
SELECT 	price "C 수강료" # 10000
FROM 	Summer
WHERE 	class='C';

/* 200번 학생의 수강신청 취소 */
DELETE FROM  Summer
WHERE  sid=200;

/* C 강좌 수강료 다시 조회 */ => C 수강료 조회 불가능!!
SELECT 	price "C 수강료"
FROM 	Summer
WHERE 	class='C';

/* 다음 실습을 위해 200번 학생 자료 다시 입력 */
INSERT INTO Summer VALUES (200, 'C', 10000);
```

학생의 수강 내역을 지우면 과목의 수강료까지 같이 지워짐.

<br/>

#### 삽입 이상

- 질의 7-2 : 계절학기에 새로운 자바 강좌를 개설하시오.

![image](https://user-images.githubusercontent.com/62230430/119244312-4c11c700-bbaa-11eb-8027-2f742f62f229.png)

```mysql
/* 자바 강좌 삽입 */ => NULL을 삽입해야 한다. NULL 값은 문제가 있을 수 있다.
INSERT INTO Summer VALUES (NULL, 'JAVA', 25000);

/* Summer 테이블 조회 */
SELECT	 *
FROM 	Summer;



/* NULL 값이 있는 경우 주의할 질의 : 투플은 다섯 개지만 수강학생은 총 네 명임 */ # 5
SELECT 	COUNT(*) "수강인원"
FROM 	Summer;

SELECT 	COUNT(sid) "수강인원" # 4
FROM 	Summer;

SELECT 	count(*) "수강인원" # 4
FROM 	Summer
WHERE 	sid IS NOT NULL;
```

<br/>

#### 수정이상

- 질의 7-3 : FORTRAN 강좌의 수강료를 20,000원에서 15,000원으로 수정하시오.

```mysql
/* FORTRAN 강좌 수강료 수정 */
UPDATE 	Summer
SET 	price=15000
WHERE 	class='FORTRAN';

SELECT 	*
FROM 	Summer;

SELECT 	DISTINCT price "FORTRAN 수강료"
FROM 	Summer
WHERE 	class='FORTRAN';

/* 다음 실습을 위해 FORTRAN 강좌의 수강료를 다시 20,000원으로 복구 */
UPDATE 	Summer
SET 	price=20000
WHERE 	class='FORTRAN';

/* 만약 UPDATE 문을 다음과 같이 작성하면 데이터 불일치 문제가 발생함 */
UPDATE 	Summer
SET 	price=15000
WHERE 	class='FORTRAN' AND sid=100;
```

<br/>

테이블 구조를 수정시키면 이상현상이 발생하지 않음.

- 수정된 계절학기 수강 테이블

![image](https://user-images.githubusercontent.com/62230430/119244337-95621680-bbaa-11eb-9a05-9a3c0ab8264b.png)


<br/>

## 02. 함수 종속성

### 함수 종속성의 개념

####  학생 수강 성적 릴레이션
![image](https://user-images.githubusercontent.com/62230430/119244362-dbb77580-bbaa-11eb-94cf-37adb2645a22.png)

- 각 속성 사이에는 **의존성이 존재**
- A → B : '속성 B는 속성 A에 종속한다', '속성 A는 속성 B를 결정한다' (A는 B의 결정자)

<br/>

### 함수 종속성 다이어그램

![image](https://user-images.githubusercontent.com/62230430/119244396-4799de00-bbab-11eb-80e1-ba58ea19f354.png)

- 릴레이션의 속성 : 직사각형
- 속성 간의 함수 종속성 : 화살표
- 복합 속성 : 직사각형으로 묶어서 그림

<br/>

### 함수 종속성 규칙

X, Y, Z가 릴레이션 R에 포함된 속성의 집합이라고 가정하면 다음과 같은 규칙 성립

![image](https://user-images.githubusercontent.com/62230430/119244427-8def3d00-bbab-11eb-9304-7c0517436c7b.png)


<br/>

### 함수 종속성과 기본키

함수 종속성을 찾기 위해서는 우선적으로 기본키를 찾아야 함.

![image](https://user-images.githubusercontent.com/62230430/119244441-b840fa80-bbab-11eb-9931-fef5b21348f3.png)

<br/>

ex ) 이름이 같은 학생이 없다고 가정하면, ‘이름 → 학과, 이름 → 주소, 이름 → 취득학점’이므로  
‘이름 → 이름, 학과, 주소, 취득학점’이 성립한다. 즉 이름 속성이 학생 릴레이션의 전체를 결정함.

<br/>

### 이상현상과 결정자

이상현상은 **기본키가 아니면서 결정자 속성이 있을 때 발생**  
이러한 이상현상을 없애려면 **릴레이션 분해** 필요

![image](https://user-images.githubusercontent.com/62230430/119244478-1a016480-bbac-11eb-87a7-9ae72460a17c.png)

<br/>

## 03. 정규화

### 정규화

: 이상현상이 발생하는 릴레이션을 분해하여 이상현상을 없애는 과정  

 정규형이 높을수록 이상현상은 줄어듦.

### 정규화 과정

#### 제 1정규형
: 모든 속성 값이 원자값을 가지면 제 1정규형

![image](https://user-images.githubusercontent.com/62230430/119244524-8714fa00-bbac-11eb-9946-58b9f4e1fcd7.png)

고객취미들(이름, 취미들) 릴레이션을 고객취미(이름, 취미) 릴레이션으로 바꾸어 저장하면 제 1정규형을 만족

<br/>

#### 제 2정규형
: 릴레이션 R이 제 1정규형이고, 기본키가 아닌 속성이 기본키에 완전 함수 종속일 때 제 2정규형

- 완전 함수 종속 : A와 B가 릴레이션 R의 속성이고 A → B 종속성이 성립할 때, B가 A의 속성 정체에 함수 종속하고
부분 집합 속성에 함수 종속하지 않을 경우

- 수강강좌 릴레이션

![image](https://user-images.githubusercontent.com/62230430/119244594-1c17f300-bbad-11eb-926a-e0ad9b1a897d.png)

<br/>

- 제 2정규형으로 변환

![image](https://user-images.githubusercontent.com/62230430/119244583-01de1500-bbad-11eb-8718-3753470ecaac.png)

이상현상을 일으키는 (강좌이름, 강의실)을 분해

<br/>

#### 제 3정규형
: 릴레이션 R이 제 2정규형이고, 기본키가 아닌 속성이 기본키에 비이행적으로 종속할 때 제 3정규형

- 이행적 종속 : A → B, B → C가 성립할 때 A → C가 성립되는 함수 종속성

<br/>

- 계절학기 릴레이션

![image](https://user-images.githubusercontent.com/62230430/119244816-41a5fc00-bbaf-11eb-89bc-49d4c267d5bb.png)

<br/>

- 제 3정규형으로 변환

![image](https://user-images.githubusercontent.com/62230430/119244858-8af64b80-bbaf-11eb-8aef-5fe9a87b1d31.png)

이상현상을 일으키는 (강좌이름, 수강료)를 분해

<br/>

#### BCNF
: 릴레이션 R에서 함수 종속성 X → Y가 성립할 때, 모든 결정자 X가 후보키이면 BCNF 정규형

<br/>

- 특강수강 릴레이션

![image](https://user-images.githubusercontent.com/62230430/119244951-52a33d00-bbb0-11eb-999c-1efcecdcd48c.png)

<br/>

- BCNF 정규형으로 변환

![image](https://user-images.githubusercontent.com/62230430/119244959-5fc02c00-bbb0-11eb-8bd7-9f43e0b26f20.png)

이상현상을 일으키는 (교수, 특강이름)을 분해

<br/>

### 무손실 분해

![image](https://user-images.githubusercontent.com/62230430/119244972-78304680-bbb0-11eb-93fb-200b21c819ce.png)
















