--user 테이블 삭제
DROP TABLE users;

-- user 시퀀스 삭제
DROP SEQUENCE seq_user_no;

-- user 시퀀스 생성
CREATE SEQUENCE seq_user_no INCREMENT BY 1 START WITH 1 NOCACHE;

-- user 테이블 생성
CREATE TABLE users (
    no        NUMBER,
    PRIMARY KEY ( no ),
    id        VARCHAR2(20) UNIQUE NOT NULL,
    password  VARCHAR2(20) NOT NULL,
    name      VARCHAR2(20),
    gender    VARCHAR(10)
);

--select문
SELECT
    no,
    id,
    password,
    name,
    gender
FROM
    users;

--커밋
commit;

--롤백
rollback;    